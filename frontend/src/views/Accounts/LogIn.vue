<template>
  <div style="margin-right: 100px; margin-left: 100px;" max-width="500px">
    <!-- :rules="emailRules" --><v-text-field label="이메일" type="email" 
          v-model="email" prepend-icon="mdi-email"></v-text-field>
    <v-text-field label="비밀번호" type="password"
          v-model="password" prepend-icon="mdi-lock"></v-text-field>
    <v-chip style="margin-right: 20px;" @click="logInRequest" elevation="3" rounded>로그인</v-chip>
    <v-chip @click="goSignUp" elevation="3" rounded>회원가입</v-chip>
  </div>
</template>

<script>
import axios from 'axios'
const SERVER_URL = process.env.VUE_APP_SERVER_URL
const SECRET_KEY = process.env.VUE_APP_SECRET_KEY


export default {
  name: "LogIn",
  data: () => {
    return {
      email: "",
      password: "",
    }
  },
  // emailRules: [
  //   v => !!v || "이메일을 작성해주세요"
  // ],
  methods: {
    logInRequest() {
      if (!this.email)
        return alert("이메일을 입력해주세요")

      if (!this.email.includes('@', 1) || !this.email.includes('.', this.email.indexOf('@') + 2) || this.email.charAt(this.email.length - 1) === '.')
        return alert("올바른 형식의 이메일을 입력하세요")

      if (!this.password)
        return alert("비밀번호를 입력해주세요")

      const crypto = require("crypto")

      const form = {
        email: this.email,
        password: crypto.createHmac("sha256", SECRET_KEY).update(this.password).digest("hex")
      }

      console.log(form.password);

      this.password = ""

      axios.post(`${SERVER_URL}/user/confirm/login`, form)
      .then(response => {
        localStorage.setItem("jwt", response.data.token)
        localStorage.setItem("name", response.data.name)
        this.$router.push({ name: "Home" })
      })
      .catch(() => {
        alert("이메일 혹은 비밀번호가 맞지 않습니다")
      })
    },
    goSignUp() {
      this.$router.push({ name: "SignUp"})
    }
  },
  computed: {
    validatePasswordRules() {
      return () => null || '비밀번호 확인을 작성해주세요.';
    }
  }
}
</script>

<style>

</style>