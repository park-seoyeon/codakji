<template>
  <v-container fill-height fluid class="py-0">
    <v-row no-gutters justify="center" align="center">
      <v-col cols="12">
        <h1 class="text--primary">로그인</h1>
        <v-form persistent ref="form">
          <v-text-field
            :rules="emailRules"
            label="이메일"
            type="email"
            v-model="email"
            prepend-icon="mdi-email"
          ></v-text-field>
          <v-text-field
            :rules="passwordRules"
            label="비밀번호"
            type="password"
            v-model="password"
            prepend-icon="mdi-lock"
          ></v-text-field>
          <v-chip class="ma-2" @click="logInRequest" color="primary" outlined pill
            >로그인
            <v-icon right>mdi-login</v-icon>
          </v-chip>
          <div align="center" class="mt-2">
            <img src="../../assets/kakao_login.png" width="150px" @click="logInKakao" />
          </div>
          <div class="px-5 pt-3 d-flex flex-column guide">
            <div class="d-flex justify-space-between">
              <span>아직 회원이 아니신가요?</span>
              <v-btn @click="goSignUp" plain pill color="indigo accent-2"
                ><strong>회원가입</strong></v-btn
              >
            </div>
            <div class="pt-1 d-flex justify-end">
              <v-btn plain @click="moveFindPassword" color="indigo accent-2">
                <strong>비밀번호 찾기</strong>
              </v-btn>
            </div>
          </div>
          <!-- <v-chip class="ma-2" @click="goSignUp" color="warning" outlined pill
          >회원가입
          <v-icon right>
            mdi-account-circle-outline
          </v-icon>
        </v-chip>

        <div>
          <v-btn color="red darken-3" plain @click="moveFindPassword">
            비밀번호 찾기
          </v-btn>
        </div> -->
        </v-form>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;
const SECRET_KEY = process.env.VUE_APP_SECRET_KEY;

export default {
  name: 'LogIn',
  data: () => {
    return {
      email: '',
      password: '',
      emailRules: [
        (v) => !!v || '이메일을 입력해주세요.',
        (v) => /.+@.+\..+/.test(v) || '올바른 형식의 이메일을 입력하세요.',
      ],
      passwordRules: [(v) => !!v || '비밀번호를 입력해주세요.'],
    };
  },
  // emailRules: [
  //   v => !!v || "이메일을 작성해주세요"
  // ],
  methods: {
    logInRequest() {
      // if (!this.email) return alert('이메일을 입력해주세요');

      // if (
      //   !this.email.includes('@', 1) ||
      //   !this.email.includes('.', this.email.indexOf('@') + 2) ||
      //   this.email.charAt(this.email.length - 1) === '.'
      // )
      //   return alert('올바른 형식의 이메일을 입력하세요');

      // if (!this.password) return alert('비밀번호를 입력해주세요');

      if (this.$refs.form.validate()) {
        const crypto = require('crypto');

        const form = {
          email: this.email,
          password: crypto
            .createHmac('sha256', SECRET_KEY)
            .update(this.password)
            .digest('base64'),
        };

        console.log(form.password);

        this.password = '';

        axios
          .post(`${SERVER_URL}/user/confirm/login`, form)
          .then((response) => {
            localStorage.setItem('jwt', response.data['access-token']);
            localStorage.setItem('name', response.data['userInfo'].name);
            this.$emit('closeModal');
            this.$router.push({ name: 'Home' }).catch((error) => {
              if (error.name === 'NavigationDuplicated') {
                location.reload();
              }
            });
          })
          .catch(() => {
            alert('이메일 혹은 비밀번호가 맞지 않습니다');
          });
      }
    },
    logInKakao() {
      //현아
      window.Kakao.Auth.login({
        // scope : 'account_email',
        success: this.GetMe,
      });
    },
    GetMe() {
      // console.log(authObj);
      const crypto = require('crypto');
      window.Kakao.API.request({
        url: '/v2/user/me',
        success: (res) => {
          const kakao_account = res.kakao_account;
          const userInfo = {
            name: kakao_account.profile.nickname,
            email: kakao_account.email,
            password: crypto
              .createHmac('sha256', SECRET_KEY)
              .update('kakao1234')
              .digest('base64'),
            //account_type : 2,
            //profileImage : res.properties.profile_image,
          };
          //alert(userInfo.profileImage);
          axios
            .post(`${SERVER_URL}/user/confirm/kakaoLogin`, userInfo)
            .then((response) => {
              if (response.data['oauth-result'] === 'success') {
                localStorage.setItem('jwt', response.data['access-token']);
                localStorage.setItem('name', response.data['userInfo'].name);
                alert(userInfo.email + '님 로그인!');
                this.$emit('closeModal');
                this.$router.push({ name: 'Home' }).catch((error) => {
                  if (error.name === 'NavigationDuplicated') {
                    location.reload();
                  }
                });
              } else {
                var result = confirm(
                  '이미 존재하는 이메일입니다. 카카오 계정으로 통합하시겠습니까?'
                );
                if (result) {
                  userInfo.password = crypto
                    .createHmac('sha256', SECRET_KEY)
                    .update('kakao1234')
                    .digest('base64');
                  axios
                    .post(`${SERVER_URL}/user/confirm/continueKakaoLogin`, userInfo)
                    .then((response) => {
                      if (response.data['oauth-result'] === 'success') {
                        localStorage.setItem('jwt', response.data['access-token']);
                        localStorage.setItem('name', response.data['userInfo'].name);
                        alert(userInfo.name + '님! 통합 후 로그인 성공');
                        this.$emit('closeModal');
                        this.$router.push({ name: 'Home' }).catch((error) => {
                          if (error.name === 'NavigationDuplicated') {
                            location.reload();
                          }
                        });
                      } else {
                        alert('카카오 로그인에 실패하셨습니다');
                      }
                    });
                }
              }
            })
            .catch(() => {
              alert('로그인중 오류가 발생했습니다');
            });
        },
      });
    },
    goSignUp() {
      this.$emit('closeModal');
      this.$router.push({ name: 'SignUp' }).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
    moveFindPassword() {
      this.$emit('closeModal');
      this.$router.push({ name: 'FindPassword' }).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
  },
  computed: {
    validatePasswordRules() {
      return () => null || '비밀번호 확인을 작성해주세요.';
    },
  },
};
</script>

<style></style>
