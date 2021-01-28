<template>
  <v-row no-gutters justify="center" align="center">
    <v-col cols="12" sm="4">
      <h1 class="text--primary">회원가입</h1>
      <v-form persistent ref="form">
        <v-text-field
          class="pl-3 pr-3"
          :rules="nameRules"
          :counter="10"
          v-model="name"
          label="이름"
          type="text"
          prepend-icon="mdi-account"
        ></v-text-field>
        <v-text-field
          class="pl-3 pr-3"
          :rules="emailRules"
          v-model="email"
          label="이메일"
          type="email"
          prepend-icon="mdi-email"
        ></v-text-field>
        <v-text-field
          class="pl-3 pr-3"
          :rules="passwordRules"
          v-model="password"
          label="비밀번호"
          :counter="16"
          type="password"
          prepend-icon="mdi-lock"
        ></v-text-field>
        <v-text-field
          class="pl-3 pr-3"
          :rules="[validatePasswordRules, passwordConfirmationRule]"
          v-model="validatePassword"
          label="비밀번호 확인"
          type="password"
          prepend-icon="mdi-lock"
        ></v-text-field>
        <v-chip class="ma-2" color="primary" outlined pill @click="joinRequest">
          회원가입
          <v-icon right>
            mdi-account-outline
          </v-icon>
        </v-chip>
        <v-chip class="ma-2" close color="red darken-3" outlined @click="reset">
          취소
        </v-chip>
      </v-form>
    </v-col>
  </v-row>
</template>

<script>
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;
const SECRET_KEY = process.env.VUE_APP_SECRET_KEY;

export default {
  name: 'FindPassword',
  computed: {
    validatePasswordRules() {
      return () => this.validatePassword != null || '비밀번호 확인을 작성해주세요.';
    },
    passwordConfirmationRule() {
      return () => this.password === this.validatePassword || '비밀번호가 일치하지 않습니다.';
    },
  },
  data: () => ({
    name: '',
    email: '',
    password: '',
    validatePassword: '',
    nameRules: [
      (v) => !!v || '이름을 작성해주세요.',
      (v) => (v && v.length <= 10) || '이름은 10글자를 넘으면 안됩니다',
    ],
    emailRules: [
      (v) => !!v || '이메일을 작성해 주세요.',
      (v) => /.+@.+\..+/.test(v) || '이메일 형식에 맞춰 작성해 주세요.',
    ],
    passwordRules: [
      (v) => !!v || '비밀번호를 작성해주세요.',
      (v) =>
        /^(?=.*[a-z])(?=.*[0-9]).{8,16}$/.test(v) || '소문자, 숫자를 포함한 8-16자로 입력해 주세요',
    ],
  }),
  methods: {
    reset() {
      this.$refs.form.reset();
    },
    joinRequest() {
      const crypto = require('crypto');
      if (this.$refs.form.validate()) {
        const hash = crypto
          .createHmac('sha256', SECRET_KEY)
          .update(this.password)
          .digest('base64');
        axios
          .post(`${SERVER_URL}/user/confirm/add`, {
            name: this.name,
            email: this.email,
            password: hash,
          })
          .then(({ data }) => {
            let msg = '이메일이 중복되었습니다.';
            if (data === 'success') {
              msg = '등록이 완료되었습니다.';
              this.$router.push({ name: 'Home' });
            }
            alert(msg);
          });
        this.$refs.form.reset();
      }
    },
  },
};
</script>

<style></style>
