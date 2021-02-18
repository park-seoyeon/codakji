<template>
  <v-container fill-height fluid>
    <v-row no-gutters justify="center" align="center">
      <v-col cols="12" sm="4">
        <div align="center"><v-img width="70px" src="@/assets/img/codackji_logo.png" /></div>
        <h1 class="text--primary pt-3">비밀번호찾기</h1>
        <v-form persistent ref="form">
          <v-text-field
            class="pl-3 pr-3"
            :rules="emailRules"
            v-model="email"
            label="가입하신 이메일을 작성해주세요"
            type="email"
            prepend-icon="mdi-email"
          ></v-text-field>
        </v-form>
        <div>
          {{ msg }}
        </div>
        <v-chip class="ma-2" color="primary" outlined pill @click="findRequest">
          비밀번호찾기
          <v-icon right>
            mdi-account-search
          </v-icon>
        </v-chip>
        <v-chip class="ma-2" close color="red darken-3" outlined @click="reset">
          취소
        </v-chip>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: 'FindPassword',
  data: () => ({
    msg: '',
    email: '',
    emailRules: [
      (v) => !!v || '이메일을 작성해 주세요.',
      (v) => /.+@.+\..+/.test(v) || '이메일 형식에 맞춰 작성해 주세요.',
    ],
  }),
  computed: {
    validatePasswordRules() {
      return () => this.validatePassword != null || '비밀번호 확인을 작성해주세요.';
    },
    passwordConfirmationRule() {
      return () => this.password === this.validatePassword || '비밀번호가 일치하지 않습니다.';
    },
  },
  methods: {
    reset() {
      this.$refs.form.reset();
    },
    findRequest() {
      if (this.$refs.form.validate()) {
        axios
          .post(`${SERVER_URL}/user/changepassword`, {
            email: this.email,
          })
          .then(({ data }) => {
            if (data === 'success') {
              alert('이메일로 비밀번호가 전송되었습니다.');
              this.$router.push({ name: 'Home' });
            } else {
              this.msg = '!가입된 이메일이 없습니다.';
            }
          });
        this.$refs.form.reset();
      }
    },
  },
};
</script>

<style></style>
