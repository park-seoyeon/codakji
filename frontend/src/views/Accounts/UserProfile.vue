<template>
  <div>
    <!--상단 배너 -->
    <v-sheet color="#FFFFFF" height="300" dark align="center">
      <v-row class="fill-height" align="center" justify="center">
        <div class="display-3">
          <v-img src="@/assets/img/banner/mypage_banner.png" max-width="1000px">
            <v-container fill-height fluid>
              <div style="margin-left: 43%">
                <div class="banner text-center" style="font-size: 40px">
                  마이 페이지
                </div>
              </div>
            </v-container>
          </v-img>
        </div>
      </v-row>
    </v-sheet>

    <!--상단 탭 메뉴-->
    <v-tabs fixed-tabs v-model="tabs" background-color="indigo" dark>
      <v-tab>내 정보 보기</v-tab>
      <!-- <v-tab @click="selectInfo">내 정보 보기</v-tab> -->
      <v-tab> 내가 푼 문제 목록 </v-tab>
      <!-- <v-tab @click="selectSolved"> 내가 푼 문제 목록 </v-tab> -->
      <v-tab v-if="userInfo.stat == '학생'">
        <!-- <v-tab v-if="userInfo.stat == '학생'" @click="selectQuestion"> -->
        나의 질문 목록
      </v-tab>
      <v-tab v-else> 전체 질문 목록 </v-tab>
      <!-- <v-tab v-else @click="selectQuestion"> 전체 질문 목록 </v-tab> -->
    </v-tabs>

    <!-- <br /><br /> -->
    <v-tabs-items v-model="tabs" class="my-15">
      <!-- 내 정보 보기 -->
      <v-tab-item>
        <template style="text_align: center">
          <v-card class="mx-auto my-10" max-width="80%" min-height="400px">
            <img :src="userInfo.profileImg" width="10%" style="margin-top: 50px" />
            <v-card-text style="font-size:1.5em">
              <div class="text--primary">
                <div class="mb-10">{{ userInfo.name }} 님 ({{ userInfo.stat }})</div>

                <v-simple-table style="width:50%; margin-left:30%; text-align:left">
                  <tr>
                    <td><v-icon color="#FFB911">mdi-paw</v-icon>이메일</td>
                    <td><v-icon color="#FFB911">mdi-paw</v-icon>{{ userInfo.email }}</td>
                  </tr>

                  <tr>
                    <td><v-icon color="#6F85FF">mdi-paw</v-icon>SNS</td>
                    <td><v-icon color="#6F85FF">mdi-paw</v-icon>{{ userInfo.sns }}</td>
                  </tr>

                  <tr>
                    <td><v-icon color="#FF4D55">mdi-paw</v-icon>가입일</td>
                    <td>
                      <v-icon color="#FF4D55">mdi-paw</v-icon
                      >{{ userInfo.joindate.substring(0, 10) }}
                    </td>
                  </tr>
                </v-simple-table>
              </div>
            </v-card-text>
            <br />

            <!--비밀번호 재입력 모달창 -->
            <template>
              <v-row justify="center">
                <v-dialog v-model="dialog" max-width="500">
                  <template v-slot:activator="{ on, attrs }">
                    <v-btn color="primary" dark v-bind="attrs" v-on="on">
                      <v-icon>mdi-pencil</v-icon>
                    </v-btn>
                  </template>
                  <v-card class="px-5 py-1">
                    <v-text-field
                      label="비밀번호 재입력"
                      type="password"
                      v-model="password"
                      prepend-icon="mdi-lock"
                    ></v-text-field>
                    <v-card-actions>
                      <!-- <v-btn
                        color="green darken-1"
                        text
                        @click="getNewPassword"
                      >
                        비밀번호 찾기
                      </v-btn> -->
                      <v-spacer></v-spacer>
                      <v-btn color="green darken-1" text @click="checkPassword">
                        확인
                      </v-btn>
                      <v-btn color="green darken-1" text @click="dialog = false">
                        취소
                      </v-btn>
                    </v-card-actions>
                  </v-card>
                </v-dialog>
              </v-row>
            </template>
            <br /><br />

            <!-- 정보 수정 페이지 -->
            <v-expand-transition>
              <v-card
                v-if="reveal"
                class="transition-fast-in-fast-out v-card--reveal"
                style="height: 100%"
              >
                <br /><br />

                <v-row>
                  <v-col cols="2"></v-col>
                  <v-col cols="4">
                    <img
                      :src="userInfo.profileImg"
                      width="170px"
                      style="border: 3px solid gold; border-radius: 10px"
                    />
                    <v-file-input
                      accept="image/*"
                      label="프로필 사진 수정하기"
                      filled
                      prepend-icon="mdi-pencil"
                      id="photo"
                      enctype="multipart/form-data"
                      @change="Preview_image"
                      v-model="image"
                    ></v-file-input>
                    <br />
                  </v-col>
                  <v-col cols="4">
                    <v-textarea
                      label="이름"
                      auto-grow
                      outlined
                      rows="1"
                      row-height="15"
                      v-model="newName"
                    ></v-textarea>
                    <v-text-field
                      type="password"
                      :rules="passwordRules"
                      label="비밀번호"
                      auto-grow
                      outlined
                      rows="1"
                      row-height="15"
                      v-model="newPassword"
                    ></v-text-field>
                    <v-text-field
                      type="password"
                      label="비밀번호 확인"
                      auto-grow
                      outlined
                      rows="1"
                      row-height="15"
                      v-model="newPassword2"
                    ></v-text-field>
                  </v-col>
                </v-row>
                <br />

                <v-btn class="ma-2" color="orange darken-2" dark @click="editCancel">
                  <v-icon dark left> mdi-arrow-left </v-icon>돌아가기
                </v-btn>

                <v-btn class="ma-2" color="purple" dark @click="updateUser">
                  <v-icon dark> mdi-wrench </v-icon>수정하기
                </v-btn>

                <v-card-text class="mt-10" style="cursor: pointer" @click="deleteUser"
                  >| 회원탈퇴 |</v-card-text
                >
              </v-card>
            </v-expand-transition>
          </v-card>
          <br />
        </template>
      </v-tab-item>

      <!-- 내가 푼 문제 목록 -->
      <v-tab-item style="text_align: center">
        <MySolvedProblem :userInfo="userInfo" />
      </v-tab-item>

      <!-- 나의 질문 목록 -->
      <v-tab-item>
        <MyQuestion :userInfo="userInfo" />
      </v-tab-item>
    </v-tabs-items>

    <!-- 소스코드 모달창 -->
    <!-- <v-dialog v-model="isSourceCode" width="50%">
      <v-card height="500px" style="position: relative">
        <v-card-title class="headline yellow lighten-2">
          <br />
          {{ this.sourceTitle }}
        </v-card-title>

        <v-card-text style="font-size:1.2em; height:70%">
          <br />
          {{ this.sourceCode }}
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-btn
            color="primary"
            text
            @click="isSourceCode = false"
            style="font-size:1.4em"
          >
            닫기
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog> -->
  </div>
</template>

<script>
import MySolvedProblem from '../../components/user/MySolvedProblem.vue';
import MyQuestion from '../../components/user/MyQuestion.vue';
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;
const SECRET_KEY = process.env.VUE_APP_SECRET_KEY;

export default {
  name: 'UserProfile',
  data: () => {
    return {
      userInfo: {
        name: '이름 위치',
        email: '이메일 위치',
        joindate: '가입일 위치',
        sns: '없음',
        stat: '신분',
        number: '',
        profileImg: '',
      },
      image: '',
      tabs: null,
      reveal: false,
      dialog: false,
      isSourceCode: false,
      sourceTitle: '',
      sourceCode: '',
      password: '',
      newName: '',
      newPassword: '',
      newPassword2: '',
      passwordRules: [
        (v) => !!v || '비밀번호를 작성해주세요.',
        (v) =>
          /^(?=.*[a-z])(?=.*[0-9]).{8,16}$/.test(v) ||
          '소문자, 숫자를 포함한 8-16자로 입력해 주세요',
      ],
    };
  },
  components: {
    MySolvedProblem,
    MyQuestion,
  },
  methods: {
    setToken() {
      const token = localStorage.getItem('jwt');
      if (token) {
        return token;
      }
    },
    setUserInfo() {
      axios
        .post(`${SERVER_URL}/user/info`, { token: localStorage.getItem('jwt') })
        .then((response) => {
          this.userInfo.name = response.data.name;
          this.userInfo.email = response.data.email;
          this.userInfo.joindate = response.data.created_at;
          this.userInfo.number = response.data.user_number;
          this.userInfo.profileImg = response.data.profile_content;
          if (response.data.oauth == 'kakao') this.userInfo.sns = '카카오와 연동되어 있습니다';
          else this.userInfo.sns = '연동된 정보가 없습니다';

          if (response.data.admin == true) {
            this.userInfo.stat = '관리자';
            // console.log(response.data.admin);
          }

          if (response.data.teach == true) {
            if (response.data.admin) this.userInfo.stat += ', 교사';
            else this.userInfo.stat = '교사';
            // console.log(response.data.teach);
          }

          if (!response.data.admin && !response.data.teach) {
            this.userInfo.stat = '학생';
          }
        })
        .catch((error) => {
          // if (error.response.status === 401) {
          //   alert('세션이 만료되었습니다.');
          //   this.$emit('expireLogin');
          // } else {
          //   console.log(error);
          // }
          console.log(error);
        });
    },

    editCancel() {
      this.newName = '';
      this.newPassword = '';
      this.newPassword2 = '';
      this.reveal = false;
    },

    Preview_image() {
      this.userInfo.profileImg = URL.createObjectURL(this.image);
    },

    updateUser() {
      if (this.newPassword != this.newPassword2) {
        alert('비밀번호가 일치하지 않습니다.');
        return;
      }

      const crypto = require('crypto');
      const form = {
        password: crypto
          .createHmac('sha256', SECRET_KEY)
          .update(this.newPassword)
          .digest('base64'),
      };

      const fd = new FormData();
      fd.append('name', this.newName);
      fd.append('password', form.password);
      fd.append('token', localStorage.getItem('jwt'));
      fd.append('email', this.userInfo.email);
      if (document.getElementById('photo').files[0]) {
        fd.append('photo', document.getElementById('photo').files[0]);

        console.log(this.fileName);
        console.log(fd.get('photo'));

        if (confirm('정말 수정하시겠습니까?')) {
          axios
            .put(`${SERVER_URL}/user`, fd)
            .then((response) => {
              console.log(response);
              this.setUserInfo();

              alert('정보 수정이 완료되었습니다!');

              this.newName = '';
              this.newPassword = '';
              this.newPassword2 = '';
              this.reveal = false;
            })
            .catch((error) => {
              alert('정보 수정에 실패했습니다');
              console.log(error);
            });
        }
      } else {
        if (confirm('정말 수정하시겠습니까?')) {
          axios
            .put(`${SERVER_URL}/user/noimage`, fd)
            .then((response) => {
              console.log(response);
              this.setUserInfo();

              alert('정보 수정이 완료되었습니다!');

              this.newName = '';
              this.newPassword = '';
              this.newPassword2 = '';
              this.reveal = false;
            })
            .catch((error) => {
              alert('정보 수정에 실패했습니다');
              console.log(error);
            });
        }
      }
    },

    deleteUser() {
      if (confirm('정말 탈퇴하시겠습니까?')) {
        axios
          .delete(`${SERVER_URL}/user`, {
            data: { token: localStorage.getItem('jwt') },
          })
          .then((response) => {
            console.log(response);

            alert('\n정상적으로 회원 탈퇴 되셨습니다.\n\n\n -코기는 항상 기다리고 있을게-');

            //자동 로그아웃해서 홈페이지로 가기
          })
          .catch((error) => {
            alert('\n회원 탈퇴에 실패하셨습니다. \n\n\n 탈퇴 못하지롱\n가지마...');
            console.log(error);
          });
      }
    },

    // showSource(idx) {
    //   //alert(this.solvedProblems[idx].solved_problem_content);
    //   this.sourceTitle = this.solvedProblems[idx].problem_title;
    //   this.sourceCode = this.solvedProblems[idx].solved_problem_content;
    //   this.isSourceCode = true;
    // },

    checkPassword() {
      const crypto = require('crypto');
      const form = {
        password: crypto
          .createHmac('sha256', SECRET_KEY)
          .update(this.password)
          .digest('base64'),
      };

      axios
        .post(`${SERVER_URL}/user/info`, {
          token: localStorage.getItem('jwt'),
        })
        .then((response) => {
          if (form.password == response.data.password) {
            this.dialog = false;
            this.reveal = true;
          } else {
            alert('비밀번호가 일치하지 않습니다');
          }
        })
        .catch((error) => {
          console.log(error);
        });

      this.password = '';
    },

    // getNewPassword() {
    //   axios
    //     .post(`${SERVER_URL}/user/changepassword`, {
    //       token: localStorage.getItem('jwt'),
    //     })
    //     .then((response) => {
    //       console.log(response);
    //       alert('이메일로 임시 비밀번호를 전송했습니다.');
    //     })
    //     .catch((error) => {
    //       console.log(error);
    //     });
    // },
  },

  created() {
    const userKey = {
      token: this.setToken(),
    };

    axios
      .post(`${SERVER_URL}/user/info`, userKey)
      .then((response) => {
        this.userInfo.name = response.data.name;
        this.userInfo.email = response.data.email;
        if (response.data.oauth) {
          this.userInfo.sns = response.data.oauth;
        }
      })
      .catch((error) => {
        if (error.response.status === 401) {
          alert('세션이 만료되었습니다.');
          this.$emit('expireLogin');
        } else {
          console.log(error);
        }
      });

    // this.selectInfo();

    this.setUserInfo();

    // this.setSolvedProblem();
  },
};
</script>

<style scoped>
.v-card--reveal {
  bottom: 0;
  opacity: 1 !important;
  position: absolute;
  width: 100%;
}

.v-data-table > .v-data-table__wrapper > table > tbody > tr > th,
.v-data-table > .v-data-table__wrapper > table > thead > tr > th,
.v-data-table > .v-data-table__wrapper > table > tfoot > tr > th {
  font-size: 20px;
}
</style>
