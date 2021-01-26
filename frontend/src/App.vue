<template>
  <v-app>
    <v-navigation-drawer app v-model="drawer">
      <v-list-item style="padding: 15px">
        <v-badge color="red darken-1" content="6" overlap bottom bordered>
          <v-avatar color="yellow darken-2" size="50">
            <v-icon dark size="30">
              mdi-account-circle
            </v-icon>
          </v-avatar>
        </v-badge>

        <v-list-item-title style="font-size: 25px">코기</v-list-item-title>
      </v-list-item>

      <v-divider></v-divider>

      <v-list nav align="left">
        <v-list-item link>
          <v-list-item-icon>
            <v-icon color="yellow darken-2" size="30">mdi-folder-search</v-icon>
          </v-list-item-icon>
          <v-list-item-title style="font-size: 20px">문제 풀기</v-list-item-title>
        </v-list-item>
        <v-list-item link>
          <v-list-item-icon>
            <v-icon color="yellow darken-2" size="30">mdi-video-account</v-icon>
          </v-list-item-icon>
          <v-list-item-title style="font-size: 20px">화상으로 함께 하기</v-list-item-title>
        </v-list-item>
        <v-list-item link>
          <v-list-item-icon>
            <v-icon color="yellow darken-2" size="30">mdi-microphone</v-icon>
          </v-list-item-icon>
          <v-list-item-title style="font-size: 20px">음성으로 코딩하기</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>
    <v-app-bar app dark color="yellow darken-2" elevate-on-scroll>
      <v-app-bar-nav-icon color="grey lighten-5" @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title style="font-size: 35px">코딱지</v-toolbar-title>

      <v-spacer></v-spacer>

      <v-btn text @click="moveHome" style="font-size: 20px">
        <span>홈으로</span>
        <v-icon>mdi-home</v-icon>
      </v-btn>

      <v-btn text @click="moveSignup" style="font-size: 20px">
        <span>회원가입</span>
        <v-icon>mdi-account</v-icon>
      </v-btn>

      <v-btn text @click="moveLogin" style="font-size: 20px">
        <span>로그인</span>
        <v-icon>mdi-login</v-icon>
      </v-btn>
    </v-app-bar>

    <v-main>
      <v-container fill-height fluid>
        <router-view />
      </v-container>
    </v-main>
    <v-footer app> </v-footer>
  </v-app>
</template>
<script>
export default {
  data() {
    return {
      drawer: false,
    };
  },
  methods: {
    moveHome() {
      this.$router.push('/').catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
    moveSignup() {
      this.$router.push('/accounts/signup').catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
    moveLogin() {
      this.$router.push('/accounts/login').catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
  },
};
</script>
<style lang="scss">
@import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap');
#app {
  font-family: 'Do Hyeon', sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;

  a {
    font-weight: bold;
    color: #2c3e50;

    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
</style>
