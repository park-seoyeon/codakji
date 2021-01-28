<template>
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
        <v-chip
          class="ma-2"
          @click="logInRequest"
          color="primary"
          outlined pill
        >로그인
          <v-icon right>mdi-login</v-icon>
        </v-chip>
        <div class="px-5 py-3 d-flex flex-column text-subtitle-2">
          <div class="d-flex justify-space-between">
            <span>아직 회원이 아니신가요?</span>
            <a @click="goSignUp" outlined pill color="warning">회원가입</a>
          </div>
          <div class="pt-1 d-flex justify-end">
            <span @click="moveFindPassword">비밀번호 찾기</span>
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
      passwordRules: [
        (v) => !!v || '비밀번호를 입력해주세요.',
      ],
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
            localStorage.setItem('jwt', response.data.token);
            localStorage.setItem('name', response.data.name);
            this.$router.push({ name: 'Home' });
          })
          .catch(() => {
            alert('이메일 혹은 비밀번호가 맞지 않습니다');
          });
      }
    },
    goSignUp() {
      this.$emit("closeModal")
      if (window.location.href != "http://localhost:8080/accounts/signup")  // 이 부분 후에 수정해야함
        this.$router.push({ name: 'SignUp' });                              // 회원가입 창에서 회원가입으로 못 넘어가도록
    },
    moveFindPassword() {
      this.$emit("closeModal")
      if (window.location.href != "http://localhost:8080/accounts/findpassword")
        this.$router.push({ name: 'FindPassword' });
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
