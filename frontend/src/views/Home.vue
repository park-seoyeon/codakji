<template>
  <v-row no-gutters justify="center" align="center">
    <v-col cols="12" sm="4">
      <div class="home">
        <HelloWorld msg="Welcome to Your Vue.js App" />
      </div>
    </v-col>
  </v-row>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue';
import axios from 'axios';
const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: 'Home',
  data: () => ({
    code :"",
   }),
  components: {
    HelloWorld,
  },
  created(){
    this.code = this.$route.query.code;
    if(this.code!=null || this.code!=undefined){
      axios
      .post(`${SERVER_URL}/kakao/login/request`, this.code)
      .then((response) => {
        let userInfo = null;
        if (response.data['oauth-result'] === 'success') {
          userInfo = response.data['userInfo'];
          localStorage.setItem('jwt', response.data['access-token']);
          localStorage.setItem('name', response.data['userInfo'].name);
          alert(userInfo.email + '님 로그인!');
          this.$emit('closeModal');
          this.$router.push({ name: 'Home' }).catch((error) => {
            if (error.name === 'NavigationDuplicated') {
              location.reload();
            }
          });
        }else {
          var result = confirm(
            '이미 존재하는 이메일입니다. 카카오 계정으로 통합하시겠습니까?'
          );
          if (result) {
            userInfo = response.data['userInfo'];
            axios
              .post(`${SERVER_URL}/kakao/login/merge`, userInfo)
              .then((response) => {
                if (response.data['oauth-result'] === 'success') {
                  userInfo = response.data['userInfo'];
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
                  this.$router.push({ name: 'Home' }).catch((error) => {
                    if (error.name === 'NavigationDuplicated') {
                      location.reload();
                    }
                  });
                }
              });
          }
        }
      })
      .catch(() => {
        alert('로그인중 오류가 발생했습니다. 다시 로그인 해주세요.');
        this.$router.push({ name: 'Home' }).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
      });
    }
  }
};
</script>
