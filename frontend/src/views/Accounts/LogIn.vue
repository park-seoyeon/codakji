<template>
  <div style="margin: 50px;">
    <v-text-field :rules="emailRules" label="이메일" type="email"
          v-model="email" prepend-icon="mdi-email"></v-text-field>
    <v-text-field :rules="passwordRules" label="비밀번호" type="password"
          v-model="password" prepend-icon="mdi-lock"></v-text-field>
    <v-btn @click="logInRequest">Log In</v-btn>
  </div>
</template>

<script>
import axios from 'axios'
const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "LogIn",
  data: () => {
    return {
      email: "",
      password: "",
    }
  },
  methods: {
    logInRequest() {
      if (!this.email)
        return alert("이메일을 입력해주세요")
      
      if (!this.email.includes('@', 1) || !this.email.includes('.', this.email.indexOf('@') + 2) || this.email.charAt(this.email.length - 1) === '.')
        return alert("올바른 형식의 이메일을 입력하세요")

      if (!this.password)
        return alert("비밀번호를 입력해주세요")

      const form = {
        email: this.email,
        password: this.password 
      }

      axios.post(`${SERVER_URL}/accounts/login`, form)
      .then(response => {
        localStorage.setItem("jwt", response.data.token)
        localStorage.setItem("name", response.data.name)
        this.$router.push({ name: "Home" })
      })
      .catch(() => {
        alert("이메일 혹은 비밀번호가 맞지 않습니다")
      })
    }
  }
}
</script>

<style>

</style>