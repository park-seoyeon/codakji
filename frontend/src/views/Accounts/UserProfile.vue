<template>
  <div>
    <!--상단 배너 -->
    <v-sheet color="#FFFFFF" height="300" dark align="center">
      <v-row class="fill-height" align="center" justify="center">
        <div class="display-3">
          <v-img src="@/assets/img/mypage_banner.png" max-width="1000px">
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
    <v-tabs fixed-tabs background-color="indigo" dark>
      <v-tab @click="selectInfo">내 정보 보기</v-tab>
      <v-tab @click="selectSolved"> 내가 푼 문제 목록 </v-tab>
      <v-tab v-if="userInfo.stat == '학생'" @click="selectQuestion">
        나의 질문 목록
      </v-tab>
      <v-tab v-else @click="selectQuestion"> 전체 질문 목록 </v-tab>
    </v-tabs>

    <!-- 내 정보 보기 -->
    <div v-if="isSelectedInfo" style="text_align: center">
      <br /><br />
      <template>
        <v-card class="mx-auto" max-width="60%" min-height="400px">
          <img
            src="@/assets/img/watting_cogi.png"
            width="10%"
            style="margin-top: 50px"
          />
          <v-card-text style="font-size:1.5em">
            <div class="text--primary">
              {{ userInfo.name }} 님 ({{ userInfo.stat }})<br /><br /><br />

              이메일 : {{ userInfo.email }}<br /><br />
              SNS 연동 여부 : {{ userInfo.sns }}<br /><br />
              가입일 : {{ userInfo.joindate }}
            </div>
          </v-card-text>
          <br />

          <!--비밀번호 재입력 모달창 -->
          <template>
            <v-row justify="center">
              <v-dialog v-model="dialog" persistent max-width="500">
                <template v-slot:activator="{ on, attrs }">
                  <v-btn color="primary" dark v-bind="attrs" v-on="on">
                    <v-icon>mdi-pencil</v-icon>
                  </v-btn>
                </template>
                <v-card>
                  <br />

                  <v-text-field
                    label="비밀번호"
                    type="password"
                    v-model="password"
                    prepend-icon="mdi-lock"
                  ></v-text-field>
                  <v-card-actions>
                    <v-btn color="green darken-1" text @click="getNewPassword">
                      비밀번호 찾기
                    </v-btn>
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
                    src="@/assets/img/watting_cogi.png"
                    width="170px"
                    style="border: 3px solid gold; border-radius: 10px"
                  />
                  <div style="cursor:pointer" @click="editProfilePic">
                    프로필 사진 수정하기<v-icon>mdi-pencil</v-icon>
                  </div>
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

              <v-btn
                class="ma-2"
                color="orange darken-2"
                dark
                @click="editCancel"
              >
                <v-icon dark left> mdi-arrow-left </v-icon>돌아가기
              </v-btn>

              <v-btn class="ma-2" color="purple" dark @click="updateUser">
                <v-icon dark> mdi-wrench </v-icon>수정하기
              </v-btn>

              <v-card-text style="cursor: pointer" @click="deleteUser"
                >| 회원탈퇴 |</v-card-text
              >
            </v-card>
          </v-expand-transition>
        </v-card>
      </template>
      <br /><br />
    </div>

    <!-- 내가 푼 문제 목록 -->
    <div v-if="isSelectedSolved" style="text_align: center">
      <br /><br />

      <v-simple-table>
        <template v-slot:default>
          <thead>
            <tr>
              <th class="text-center" style="width: 20%">제목</th>
              <th class="text-center" style="width: 40%">내용</th>
              <th class="text-center" style="width: 20%">유형</th>
              <th class="text-center" style="width: 20%">제출 코드</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="(item, index) in solvedProblems" :key="index">
              <td>{{ item.problem_title }}</td>
              <td>{{ item.problem_content.substring(0, 50) }}...</td>
              <td>{{ item.problem_group }}</td>
              <td @click="showSource(index)" style="cursor: pointer">
                {{ item.language }}
              </td>
            </tr>
          </tbody>
        </template>
      </v-simple-table>
    </div>

    <!-- 소스코드 모달창 -->
    <v-dialog v-model="isSourceCode" width="50%">
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
    </v-dialog>

    <!-- 나의 질문 목록 -->
    <div v-if="isSelectedQuestion" style="text_align: center">
      <br />
      <v-container fluid>
        <v-row justify="center">
          <v-subheader v-if="userInfo.stat == '학생'" style="font-size:1.5em"
            >무엇이든 선생님에게 물어보세요</v-subheader
          >
          <v-subheader v-else>최근 등록된 질문 목록</v-subheader>

          <br /><br /><br />

          <v-expansion-panels popout>
            <v-expansion-panel
              v-for="(question, i) in questionList"
              :key="i"
              hide-actions
            >
              <v-expansion-panel-header>
                <v-row align="center" class="spacer" no-gutters>
                  <!--선생님 프로필사진 -->
                  <v-col>
                    선생님 프로필사진
                  </v-col>

                  <!--선생님 이름 -->
                  <v-col>
                    선생님 이름
                  </v-col>

                  <!--질문 제목 -->
                  <v-col>
                    {{ question.question_title.substring(0, 50) }}...
                  </v-col>

                  <!--질문 등록 시간 -->
                  <v-col>
                    {{ question.created_at }}
                  </v-col>

                  <!--답변 유무 -->
                  <v-col>
                    <span v-if="question.question_answered">답변완료</span>
                    <span v-else>답변 대기중</span>
                  </v-col>
                </v-row>
              </v-expansion-panel-header>

              <v-expansion-panel-content>
                <v-divider></v-divider>
                <!--답변 내용 -->
                <v-card-text>{{ question.question_reply }}</v-card-text>
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-expansion-panels>
        </v-row>
      </v-container>
      <br /><br /><br />
    </div>
  </div>
</template>

<script>
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
      },
      solvedProblems: [],
      isSelectedInfo: false,
      isSelectedSolved: false,
      isSelectedQuestion: false,
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
      questionList: [],
    };
  },
  methods: {
    setToken() {
      const token = localStorage.getItem('jwt');
      if (token) {
        return token;
      }
    },

    selectInfo() {
      this.isSelectedInfo = true;
      this.isSelectedSolved = false;
      this.isSelectedQuestion = false;
    },

    selectSolved() {
      this.isSelectedInfo = false;
      this.isSelectedSolved = true;
      this.isSelectedQuestion = false;
    },

    selectQuestion() {
      this.isSelectedInfo = false;
      this.isSelectedSolved = false;
      this.isSelectedQuestion = true;
    },

    setUserInfo() {
      axios
        .post(`${SERVER_URL}/user/info`, { token: localStorage.getItem('jwt') })
        .then((response) => {
          this.userInfo.name = response.data.name;
          this.userInfo.email = response.data.email;
          this.userInfo.joindate = response.data.created_at;
          this.userInfo.number = response.data.user_number;
          if (response.data.oauth == 'kakao')
            this.userInfo.sns = '카카오 계정과 연동되어 있습니다';
          else this.userInfo.sns = '연동된 SNS 계정이 없습니다';

          if (response.data.admin == true) {
            this.userInfo.stat = '관리자';
            console.log(response.data.admin);
          } else if (response.data.teach == true) {
            this.userInfo.stat = '선생님';
            console.log(response.data.teach);
          } else {
            this.userInfo.stat = '학생';
          }

          this.setQuestionList();
        })
        .catch((error) => {
          if (error.response.status === 401) {
            alert('세션이 만료되었습니다.');
            this.$emit('expireLogin');
          } else {
            console.log(error);
          }
        });
    },

    setSolvedProblem() {
      axios
        .post(`${SERVER_URL}/problem/user/solved`, {
          token: localStorage.getItem('jwt'),
        })
        .then((response) => {
          this.solvedProblems = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },

    setQuestionList() {
      if (this.userInfo.stat == '학생') {
        //나의 질문 목록
        axios
          .post(`${SERVER_URL}/question`, {
            token: localStorage.getItem('jwt'),
          })
          .then((response) => {
            console.log('나의질문리스트');
            console.log(response.data);
            this.questionList = response.data;
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        //전체 질문 목록
      }
    },

    editProfilePic() {
      alert('프로필 사진을 수정해주세요');
    },

    editCancel() {
      this.newName = '';
      this.newPassword = '';
      this.newPassword2 = '';
      this.reveal = false;
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

      axios
        .put(`${SERVER_URL}/user`, {
          name: this.newName,
          password: form.password,
          token: localStorage.getItem('jwt'),
          email: this.userInfo.email,
        })
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
    },

    deleteUser() {
      axios
        .delete(`${SERVER_URL}/user`, {data: {token: localStorage.getItem('jwt')}} )
        .then((response) => {
          console.log(response);
          alert('정말 탈퇴하시겠습니까?');
          alert(
            '\n정상적으로 회원 탈퇴 되셨습니다.\n\n\n -코기는 항상 기다리고 있을게-'
          );

          //자동 로그아웃해서 홈페이지로 가기
        })
        .catch((error) => {
          alert('\n회원 탈퇴에 실패하셨습니다. \n\n\n 탈퇴 못하지롱\n가지마...');
          console.log(error);
        });
    },

    

    showSource(idx) {
      //alert(this.solvedProblems[idx].solved_problem_content);
      this.sourceTitle = this.solvedProblems[idx].problem_title;
      this.sourceCode = this.solvedProblems[idx].solved_problem_content;
      this.isSourceCode = true;
    },

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

    getNewPassword() {
      axios
        .post(`${SERVER_URL}/user/changepassword`, {
          token: localStorage.getItem('jwt'),
        })
        .then((response) => {
          console.log(response);
          alert('이메일로 임시 비밀번호를 전송했습니다.');
        })
        .catch((error) => {
          console.log(error);
        });
    },
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

    this.selectInfo();

    this.setUserInfo();

    this.setSolvedProblem();
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
