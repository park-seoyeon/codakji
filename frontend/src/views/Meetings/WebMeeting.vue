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

      <!-- <div id="main-video" class="col-md-6">
         <user-video :stream-manager="mainStreamManager" />
      </div> -->
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
<!--          <span
            style="
              height: 40px;
              float: left;
              width: 100%;
              text-align: center;
              font-size: 30px;
            "
            >CHAT</span
          >
-->
          <div class="message-wrap">
            <p
              class="chatlist"
              v-for="(msg, index) in messageList"
              :key="index"
              v-text="msg.message"
            ></p>
          </div>

          <div id="messageInput">
            <!-- <select v-model="to" class="chatselect">
            <option
              v-for="sub in subscribers"
              :key="sub.stream.connection.connectionId"
              v-text="sub.stream.connection.connectionId"
              v-bind="sub.stream"
            ></option>
          </select> -->
            <input
              placeholder="대화를 시작해보세요..!"
              id="chatInput"
              v-model="message"
            />
            <button id="sendButton" @click="sendMessage">SEND</button>
          </div>
        </div>

        <!-- //////////////// chat ///////////////////// -->
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


      // chat
      messageList: [],
      message: "",
      to: "",
    };
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
 /* mounted(){
      console.log("mounted WebMeeting 테스트");
      console.log(this.$refs.user_video);
      let width = this.$refs.user_video.clientWidth;
      let height = this.$refs.user_video.clientHeight;
      console.log("가로:"+width+"/세로"+height);
      if(this.subscribers.length>0){
        console.log("sub 가로:"+this.$refs.sub_video.clientWidth);
      }
      
  },
  */

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
        .then((response) => {
          console.log(response);
          //this.smainStreamManager.publishVideo(false);
          window.removeEventListener("beforeunload", this.stopScreenShare);

        });
    },
    screenShare(){
            // --- Get an OpenVidu object ---
      this.sOV = new OpenVidu();

      // --- Init a session ---
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
                  resolution: "640x480", // The resolution of your video
                  frameRate: 30, // The frame rate of your video
                  insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
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

              // --- Publish your stream ---
              this.ssession.publish(this.spublisher);
            })
            .catch((error) => {
              console.log(
                "There was an error connecting to the session:",
                error.code,
                error.message,
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
      console.log(this.checkmute);
      this.mainStreamManager.publishAudio(this.checkmute);
    },
    videoControl(){
       if (this.checkcam) {
        this.checkcam = false;
      } else {
        this.checkcam = true;
      }
      console.log(this.checkcam);
      this.mainStreamManager.publishVideo(this.checkcam);
    },
    sendMessage() {
      console.log(this.message);
      console.log(this.to);
      // if (this.props.user && this.message) {
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
      // --- Get an OpenVidu object ---
      this.OV = new OpenVidu();

      // --- Init a session ---
      this.session = this.OV.initSession();

      // --- Specify the actions when events take place in the session ---

      // On every new Stream received...
      this.session.on("streamCreated", ({ stream }) => {
        const subscriber = this.session.subscribe(stream);
        this.subscribers.push(subscriber);
      });

      // On every Stream destroyed...
      this.session.on("streamDestroyed", ({ stream }) => {
        const index = this.subscribers.indexOf(stream.streamManager, 0);
        if (index >= 0) {
          this.subscribers.splice(index, 1);
        }
      });

      axios
        .post(`${SERVER_URL}/sessions/maketoken`,{
          //session : this.session,
          user_token : localStorage.getItem('jwt'),
          room_number : parseInt(this.room_number),
          screenShare : false,
        })
        .then((response) => {
          if(response.data.message==="fail"){
            alert("새로고침으로 인해 존재하지 않는 방입니다");
            this.$router.push({ name: 'MeetingRoomList' }).catch((error) => {
              if (error.name === 'NavigationDuplicated') {
                location.reload();
              }
            });
          }else{
            console.log(response);
          this.token = response.data.token; //유저토큰 아님. session 토큰
          this.name = response.data.name;
          this.session
            .connect(this.token, { clientData: this.name })
            .then(() => {
              // --- Get your own camera stream with the desired properties ---

              let publisher = this.OV.initPublisher(undefined, {
                audioSource: undefined, // The source of audio. If undefined default microphone
                videoSource: undefined, // The source of video. If undefined default webcam
                publishAudio: true, // Whether you want to start publishing with your audio unmuted or not
                publishVideo: true, // Whether you want to start publishing with your video enabled or not
                resolution: "640x480", // The resolution of your video
                frameRate: 30, // The frame rate of your video
                insertMode: "APPEND", // How the video is inserted in the target element 'video-container'
                mirror: false, // Whether to mirror your local video or not
              });

              this.mainStreamManager = publisher;
              this.publisher = publisher;

              // --- Publish your stream ---
              this.messageList = [];
              this.session.publish(this.publisher);
              console.log("참여자 목록");
              console.log(this.subscribers);
              //chat
              this.publisher.session.on("signal:chat", (event) => {
                const data = JSON.parse(event.data);
                this.messageList.push({
                  // connectionId: event.from.connectionId,
                  name: data.name,
                  message: data.message,
                });
              });
              //chat
            })
            .catch((error) => {
              console.log(
                "There was an error connecting to the session:",
                error.code,
                error.message,
              );
            });

          window.addEventListener("beforeunload", this.leaveSession);
          }
      });
          
    },

    leaveSession() {
      // --- Leave the session by calling 'disconnect' method over the Session object ---
     if (this.checkscreenshare) {
        this.stopScreenShare();
      }

     if (this.session) this.session.disconnect();

      this.session = undefined;
      this.mainStreamManager = undefined;
      this.publisher = undefined;
      this.subscribers = [];
      this.OV = undefined;

      //this.user.session_id = -1;

      axios
      .post(`${SERVER_URL}/sessions/removeUser`, {
        user_token : localStorage.getItem('jwt'),
        room_number : this.room_number,
        token : this.token,
      }).then((response)=>{
        console.log(response);
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
      alert("메인스트림 바꾸자");
      if (this.mainStreamManager === stream) return;
      this.mainStreamManager = stream;
    },


    // See https://docs.openvidu.io/en/stable/reference-docs/REST-API/#post-openviduapisessions
    createSession(sessionId) {
      return new Promise((resolve, reject) => {
        axios
          .post(
            `${OPENVIDU_SERVER_URL}/openvidu/api/sessions`,
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
          .then((response) => response.data)
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
};
</script>

<style>
</style>