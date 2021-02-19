<template>
  <div id="WebMeeting">
    <div id="session" class="underRoom">
      <div id="session-header">

        <v-img src="@/assets/img/banner/rank_banner.png" height="200" width="100%">
          <v-container fill-height fluid>
            <v-row class="fill-height" align="center" justify="center">
              <v-col>
                <div
                  class="banner text-center"
                  style="font-size:40px;"
                >
                  {{ room_name }}
                </div>
                  <div class="guide text-center" style="font-size:15px">
                    {{room_description}}
                  </div>
              </v-col>
            </v-row>
          </v-container>
        </v-img>
      </div>

      <div id="toolbar">
        <div>
          <ul id="nav-1"> 
            <li class="slide1"></li>         
            <li class="slide2"></li>
            <li v-if="checkscreenshare" @click="stopScreenShare"><a>화면공유중지</a></li>
            <li v-else @click="screenShare"><a>화면공유</a></li>
            <li v-if="checkmute" @click="micControl"><a>음소거</a></li>
            <li v-else @click="micControl"><a>음소거해제</a></li>
            <li v-if="checkcam" @click="videoControl"><a>카메라끄기</a></li>
            <li v-else @click="videoControl"><a>카메라켜기</a></li>
            <li @click="leaveSession"><a>나가기</a></li>
          </ul>
        </div>
      </div>

      <div id="roomContents">
        <div id="video-container">
          <div class="video_test" >
            <user-video
              :stream-manager="publisher"
              @click.native="updateMainVideoStreamManager(publisher)"
            />

            <user-video
              v-for="sub in subscribers"
              :key="sub.stream.connection.connectionId"
              :stream-manager="sub"
              @click.native="updateMainVideoStreamManager(sub)"
            />
          </div>

        </div>
        <div id="chatContainer">
          <div class="message-wrap">
            <p
              class="chatlist"
              v-for="(msg, index) in messageList"
              :key="index"
              v-text="msg.message"
            ></p>
          </div>

          <div id="messageInput">
            <input
              placeholder="대화를 시작해보세요..!"
              id="chatInput"
              v-model="message"
              @keypress.enter="sendMessage"
            />
            <button id="sendButton" @click="sendMessage">SEND</button>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>


<script>
import axios from "axios";
import { OpenVidu } from "openvidu-browser";

import UserVideo from "@/components/meeting/UserVideo.vue";

axios.defaults.headers.post["Content-Type"] = "application/json";

const SERVER_URL = process.env.VUE_APP_SERVER_URL;
const OPENVIDU_SERVER_URL = process.env.OPENVIDU_SERVER_URL;
const OPENVIDU_SERVER_SECRET = process.env.OPENVIDU_SERVER_SECRET;

export default {
  name: "WebMeeting",
  components: {
    UserVideo,
  },
  data() {
    return {
      OV: undefined,
      session: undefined,
      mainStreamManager: undefined,
      publisher: undefined,
      subscribers: [],
      token: " ",
      mySessionId: "Room",
      myUserName: "player" + Math.floor(Math.random() * 100),
      room_number : '',
      room_name : '',
      room_description:'',
      name : '',

      stoken : "",
      sOV: undefined,
      ssession: undefined,
      smainStreamManager: undefined,
      spublisher: undefined,
      ssubscribers: [],

      checkscreenshare: false,
      checkmute: true,
      checkcam: true,

      messageList: [],
      message: "",
      to: "",
    };
  },
  methods: {
    stopScreenShare() {
      if (this.ssession) this.ssession.disconnect();

      this.ssession = undefined;
      this.smainStreamManager = undefined;
      this.spublisher = undefined;
      this.ssubscribers = [];
      this.sOV = undefined;
      this.checkscreenshare = false;

      axios
        .post(`${SERVER_URL}/sessions/removeUser`, {
          screenShare: true,
          user_token: localStorage.getItem('jwt'),
          room_number: this.room_number,
          token: this.stoken,
        })
        .then(() => {
          window.removeEventListener("beforeunload", this.stopScreenShare);
        });
    },
    screenShare(){
      this.sOV = new OpenVidu();
      this.ssession = this.sOV.initSession();

      axios
        .post(`${SERVER_URL}/sessions/maketoken/`,{
          user_token : localStorage.getItem('jwt'),
          room_number : parseInt(this.room_number),
          screenShare : true,
        })
        .then((response) => {
          this.checkscreenshare = true;
          this.stoken = response.data.token;

          this.ssession
            .connect(this.stoken, { clientData: this.name })
            .then(() => {
              const videoSource =
                navigator.userAgent.indexOf("Firefox") !== -1
                  ? "window"
                  : "screen";
              let publisher = this.OV.initPublisher(
                undefined,
                {
                  audioSource: undefined,
                  videoSource: videoSource,
                  publishAudio: false,
                  publishVideo: true,
                  resolution: "640x480",
                  frameRate: 30,
                  insertMode: "APPEND",
                  mirror: false,
                },
                (error) => {
                  if (
                    error &&
                    error.name === "SCREEN_EXTENSION_NOT_INSTALLED"
                  ) {
                    alert("Your browser should INSTALL SCREEN_EXTENSION");
                  } else if (
                    error &&
                    error.name === "SCREEN_SHARING_NOT_SUPPORTED"
                  ) {
                    alert("Your browser does not support screen sharing");
                  } else if (
                    error &&
                    error.name === "SCREEN_EXTENSION_DISABLED"
                  ) {
                    alert("You need to enable screen sharing extension");
                  } else if (error && error.name === "SCREEN_CAPTURE_DENIED") {
                    alert(
                      "You need to choose a window or application to share",
                    );
                  }
                },
              );
              this.smainStreamManager = publisher;
              this.spublisher = publisher;

              this.ssession.publish(this.spublisher);
            })
            .catch((error) => {
              console.log(
                "There was an error connecting to the session:",
                error.code
              );
            });
        });
        
      window.addEventListener("beforeunload", this.stopScreenShare);
    },
    micControl(){
        if (this.checkmute) {
        this.checkmute = false;
      } else {
        this.checkmute = true;
      }
      this.mainStreamManager.publishAudio(this.checkmute);
    },
    videoControl(){
       if (this.checkcam) {
        this.checkcam = false;
      } else {
        this.checkcam = true;
      }
      this.mainStreamManager.publishVideo(this.checkcam);
    },
    sendMessage() {
      if (this.message) {
        let message = this.message.replace(/ +(?= )/g, "");
        if (message !== "" && message !== " ") {
          const data = {
            message: this.name + " : " + message,
            name: this.name,
            streamId: this.publisher.stream.streamId,
          };
          this.publisher.session.signal({
            data: JSON.stringify(data),
            to: [this.to],
            type: "chat",
          });
        }
      }
      this.message = "";
    },
    joinSession() {
      this.OV = new OpenVidu();

      this.session = this.OV.initSession();

      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      axios
        .post(`${SERVER_URL}/sessions/maketoken`,{
          user_token : localStorage.getItem('jwt'),
          room_number : parseInt(this.room_number),
          screenShare : false,
        })
        .then((response) => {
          if(response.data.message === "fail"){
            alert("새로고침으로 인해 존재하지 않는 방입니다");
            this.$router.push({ name: 'MeetingRoomList' }).catch((error) => {
              if (error.name === 'NavigationDuplicated') {
                location.reload();
              }
            });
          } else {
            this.token = response.data.token;
            this.name = response.data.name;
            this.session
              .connect(this.token, { clientData: this.name })
              .then(() => {
                let publisher = this.OV.initPublisher(undefined, {
                  audioSource: undefined,
                  videoSource: undefined,
                  publishAudio: true,
                  publishVideo: true,
                  resolution: "640x480",
                  frameRate: 30,
                  insertMode: "APPEND",
                  mirror: false,
                });

                this.mainStreamManager = publisher;
                this.publisher = publisher;

                this.messageList = [];
                this.session.publish(this.publisher);
                this.publisher.session.on("signal:chat", (event) => {
                  const data = JSON.parse(event.data);
                  this.messageList.push({
                    name: data.name,
                    message: data.message,
                  });
                });
              })
              .catch((error) => {
                console.log(
                  "There was an error connecting to the session:",
                  error.code
                );
              });

            window.addEventListener("beforeunload", this.leaveSession);
          }
      });  
    },
    leaveSession() {
      if (this.checkscreenshare) {
        this.stopScreenShare();
      }

      if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      axios
      .post(`${SERVER_URL}/sessions/removeUser`, {
        user_token : localStorage.getItem('jwt'),
        room_number : this.room_number,
        token : this.token,
      })
      .then(()=>{
        window.removeEventListener("beforeunload", this.leaveSession);
        this.$router
          .push({ name: 'MeetingRoomList'})
          .catch((error) => {
          if (error.name === 'NavigationDuplicated') {
            location.reload();
          }
        });
        location.reload();
      })
    },
    updateMainVideoStreamManager(stream) {
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(`${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
            JSON.stringify({
              customSessionId: sessionId,
            }),
            {
              auth: {
                username: "OPENVIDUAPP",
                password: OPENVIDU_SERVER_SECRET,
              },
            },
          )
          .then((data) => resolve(data.id))
          .catch((error) => {
            if (error.response.status === 409) {
              resolve(sessionId);
            } else {
              console.warn(
                `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}`,
              );
              if (
                window.confirm(
                  `No connection to OpenVidu Server. This may be a certificate error at ${OPENVIDU_SERVER_URL}\n\nClick OK to navigate and accept it. If no certificate warning is shown, then check that your OpenVidu Server is up and running at "${OPENVIDU_SERVER_URL}"`,
                )
              ) {
                location.assign(`${OPENVIDU_SERVER_URL}/accept-certificate`);
              }
              reject(error.response);
            }
          });
      });
    },
  },
  created() {
    this.room_number = this.$route.params.room_number;
    axios
    .get(`${SERVER_URL}/sessions/roominfo/${this.room_number}`)
     .then((response) => {
       this.room_name = response.data.room_name;
       this.room_description = response.data.room_description;
       this.joinSession();
     });
  },
};
</script>

<style>
</style>