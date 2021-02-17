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
      <v-tab> 내가 푼 문제 목록 </v-tab>
      <v-tab v-if="userInfo.stat == '학생'">
        나의 질문 목록
      </v-tab>
      <v-tab v-else> 전체 질문 목록 </v-tab>
    </v-tabs>

    <v-tabs-items v-model="tabs" class="my-15">
      <!-- 내 정보 보기 -->
      <v-tab-item>
        <template style="text_align: center">
          <v-card class="mx-auto pt-3 pb-15" max-width="80%" min-height="400px">
            <div class="mt-1" align="right">
              <v-chip
                class="mr-4"
                color="red lighten-2"
                outlined
                @click="
                  isUpdate = false;
                  editNotice = true;
                "
                v-if="isAdmin"
              >
                공지 추가하기
                <v-icon>mdi-bell-plus-outline</v-icon>
              </v-chip>
            </div>
            <img :src="userInfo.profileImg" width="10%" style="margin-top: 50px" />
            <v-card-text style="font-size:1.5em">
              <div class="text--primary">
                <div class="mb-10">{{ userInfo.name }} 님 ({{ userInfo.stat }})</div>

                <v-simple-table style="width:50%; margin-left:40%; text-align:left;">
                  <tr>
                    <td><v-icon color="#FFB911">mdi-at</v-icon>이메일</td>
                    <td>{{ userInfo.email }}</td>
                  </tr>

                  <tr>
                    <td><v-icon color="#6F85FF">mdi-web</v-icon>SNS</td>
                    <td>{{ userInfo.sns }}</td>
                  </tr>

                  <tr>
                    <td><v-icon color="#FF4D55">mdi-calendar-range</v-icon>가입일</td>
                    <td>
                      {{ userInfo.joindate.substring(0, 10) }}
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
                    <v-chip color="primary" outlined dark v-bind="attrs" v-on="on">
                      정보 수정하기<v-icon>mdi-pencil</v-icon>
                    </v-chip>
                  </template>
                  <v-card class="px-5 py-1">
                    <v-text-field
                      label="비밀번호 재입력"
                      type="password"
                      v-model="password"
                      prepend-icon="mdi-lock"
                      @keypress.enter="checkPassword"
                    ></v-text-field>
                    <v-card-actions>
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
              <!-- <v-btn v-if="isAdmin" color="primary">
                <v-icon color="white">mdi-bell-plus-outline</v-icon>
                공지 추가하기
              </v-btn> -->
            </template>

            <!-- 정보 수정 페이지 -->
            <v-expand-transition>
              <v-card
                v-if="reveal"
                class="transition-fast-in-fast-out v-card--reveal"
                style="height: 100%"
              >
                <v-row>
                  <v-col cols="2"></v-col>
                  <v-col cols="4">
                    <div class="my-10">
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
                    </div>
                  </v-col>
                  <v-col cols="4">
                    <div class="my-10">
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
                    </div>
                  </v-col>
                </v-row>

                <v-chip class="ma-2" color="red darken-1" outlined dark @click="editCancel">
                  <v-icon dark left> mdi-arrow-left </v-icon>돌아가기
                </v-chip>

                <v-chip class="ma-2" color="primary" outlined dark @click="updateUser">
                  <v-icon dark> mdi-wrench </v-icon>수정하기
                </v-chip>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <span class="mt-10" style="cursor: pointer; color: darkgrey;" @click="deleteUser">
                    | 회원탈퇴 |
                  </span>
                  <v-spacer></v-spacer>
                </v-card-actions>
              </v-card>
            </v-expand-transition>

            <!-- 공지 추가/수정 하기 모달창 -->
            <v-dialog v-model="editNotice" width="50%">
              <v-card min-height="500px" style="position: relative">
                <v-card-title class="headline #FAFAFA">
                  <img src="@/assets/img/codackji_logo.png" height="30px" />
                </v-card-title>

                <v-select
                  :items="numbers"
                  label="새로 작성"
                  dense
                  solo
                  max-width="10px"
                  v-model="selected_number"
                ></v-select>

                <v-card-text style="font-size:1.2em;">
                  <br />
                  <v-textarea
                    label="제목"
                    auto-grow
                    outlined
                    rows="1"
                    row-height="10"
                    v-model="editNoticeTitle"
                  ></v-textarea>
                  <v-textarea
                    label="내용"
                    auto-grow
                    outlined
                    rows="10"
                    row-height="40"
                    v-model="editNoticeContent"
                  ></v-textarea>
                </v-card-text>

                <v-divider></v-divider>

                <v-card-actions>
                  <v-spacer></v-spacer>
                  <v-chip
                    color="amber darken-1"
                    class="mr-2"
                    close
                    outlined
                    @click="cancelNotice"
                    style="font-size:1.4em"
                  >
                    취소하기
                  </v-chip>
                  <v-chip
                    color="red darken-1"
                    class="mr-2"
                    v-show="isUpdate"
                    outlined
                    @click="deleteNotice"
                    style="font-size:1.4em"
                  >
                    삭제하기
                    <v-icon size="20">mdi-delete-outline</v-icon>
                  </v-chip>
                  <v-chip
                    color="primary"
                    v-show="!isUpdate"
                    outlined
                    @click="uploadNotice"
                    style="font-size:1.4em"
                  >
                    등록하기
                    <v-icon size="20">mdi-lead-pencil</v-icon>
                  </v-chip>
                  <v-chip
                    color="light-green darken-1"
                    v-show="isUpdate"
                    outlined
                    @click="updateNotice"
                    style="font-size:1.4em"
                  >
                    수정하기
                    <v-icon size="20">mdi-lead-pencil</v-icon>
                  </v-chip>
                </v-card-actions>
              </v-card>
            </v-dialog>
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
        stat: '직위',
        number: '',
        profileImg: '',
      },
      image: '',
      tabs: null,
      isAdmin: false,
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
      notices: '',
      numbers: ['새로 작성'],
      selected_number: '새로 작성',
      editNotice: false,
      isUpdate: false, //true면 수정하기 false면 추가하기
      editNoticeTitle: '',
      editNoticeContent: '',
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
        .post(`${SERVER_URL}/user/info`, { token: this.setToken() })
        .then((response) => {
          this.userInfo.name = response.data.name;
          this.userInfo.email = response.data.email;
          this.userInfo.joindate = response.data.created_at;
          this.userInfo.number = response.data.user_number;
          this.userInfo.profileImg = response.data.profile_content;
          this.isAdmin = response.data.admin;
          if (response.data.oauth == 'kakao') this.userInfo.sns = '카카오와 연동되어 있습니다';
          else this.userInfo.sns = '연동된 정보가 없습니다';

          if (this.isAdmin) {
            this.userInfo.stat = '관리자'; // 관리자인 경우 notice를 수정하기 위해 notice 정보를 get으로 가져옴
            axios
              .get(`${SERVER_URL}/notice`)
              .then((response) => {
                this.notices = response.data;
                for (let i = response.data.length - 1; i > -1; i--) {
                  this.numbers.push(this.notices[i].notice_number);
                }
              })
              .catch((error) => {
                console.log(error);
              });
          }

          if (response.data.teach) {
            if (response.data.admin) this.userInfo.stat += ', 교사';
            else this.userInfo.stat = '교사';
          }

          if (!response.data.admin && !response.data.teach) {
            this.userInfo.stat = '학생';
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

        // console.log(this.fileName);
        // console.log(fd.get('photo'));

        if (confirm('정말 수정하시겠습니까?')) {
          axios
            .put(`${SERVER_URL}/user`, fd)
            .then(() => {
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
            .then(() => {
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
          .then(() => {
            alert('정상적으로 회원 탈퇴 되셨습니다.\n\n\n -코기는 항상 기다리고 있을게-');

            //자동 로그아웃해서 홈페이지로 가기
            localStorage.removeItem('jwt');
            localStorage.removeItem('user_number');
            localStorage.removeItem('name');

            this.$router.push({ name: 'Home' });
          })
          .catch((error) => {
            alert('회원 탈퇴에 실패하셨습니다. \n\n\n 탈퇴 못하지롱\n가지마...');
            console.log(error);
          });
      }
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
    updateNotice() {
      if (confirm('정말로 수정하시겠습니까?')) {
        axios
          .put(`${SERVER_URL}/notice`, {
            notice_number: this.selected_number,
            notice_title: this.editNoticeTitle,
            notice_content: this.editNoticeContent,
          })
          .then(() => {
            alert('성공적으로 글이 수정되었습니다.');
            this.noticeList = true;
            this.editNotice = false;
            this.editNoticeTitle = '';
            this.editNoticeContent = '';
          })
          .catch((error) => {
            alert('error!');
            console.log(error);
          });
      }
    },
    uploadNotice() {
      if (this.editNoticeTitle && this.editNoticeContent) {
        axios
          .post(`${SERVER_URL}/notice`, {
            admin_user_number: this.userNumber,
            notice_title: this.editNoticeTitle,
            notice_content: this.editNoticeContent,
          })
          .then(() => {
            alert('새로운 공지 사항을 추가하시겠습니까?');
            alert('공지 사항이 추가되었습니다.');
            this.editNotice = false;
            this.editNoticeTitle = '';
            this.editNoticeContent = '';
            this.listNotice();
          })
          .catch((error) => {
            console.log(error);
            alert('error! :(');
          });
      } else {
        alert('빈 값을 공지사항에 입력할 수 없습니다.');
      }
    },
    cancelNotice() {
      if (confirm('작성중인 게시글이 삭제됩니다.\n계속 하시겠습니까?')) {
        this.editNotice = false;
        this.editNoticeTitle = '';
        this.editNoticeContent = '';
        this.selected_number = '새로 작성';
      }
    },
    refreshNotice() {
      if (!this.editNotice) {
        this.editNoticeTitle = '';
        this.editNoticeContent = '';
        this.selected_number = '새로 작성';
      }
    },
    changeNotice() {
      if (this.selected_number == '새로 작성' && this.editNotice) {
        if (confirm('글을 새로 작성하시겠습니까?\n모든 변경이 취소됩니다.')) {
          this.isUpdate = false;
          this.editNoticeTitle = '';
          this.editNoticeContent = '';
        }
      } else {
        for (let i = this.notices.length - 1; i > -1; i--) {
          if (this.selected_number === this.notices[i].notice_number) {
            if (confirm('수정 중인 내용이 제거됩니다.\n계속 하시겠습니까?')) {
              this.isUpdate = true;
              this.editNoticeTitle = this.notices[i].notice_title;
              this.editNoticeContent = this.notices[i].notice_content;
            }
            break;
          }
        }
      }
    },
    deleteNotice() {
      if (confirm('\n정말로 삭제하시겠습니까?')) {
        axios
          .delete(`${SERVER_URL}/notice/` + this.selected.notice_number)
          .then(() => {
            alert('\n공지 글이 삭제되었습니다.');
            this.noticeList = true;
            this.listNotice();
          })
          .catch((error) => {
            alert('error!');
            console.log(error);
          });
      }
    },
  },
  created() {
    this.setUserInfo();
  },
  watch: {
    selected_number: 'changeNotice',
    editNotice: 'refreshNotice',
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
