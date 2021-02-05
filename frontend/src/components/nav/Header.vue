<template>
  <div class="mb-3">
    <v-toolbar v-bind="attrs" v-on="on">
      <v-toolbar-title @click="moveHome" class="mx-10" style="cursor: pointer;"
        ><v-img width="90px" src="@/assets/img/codackji_logo.png"
      /></v-toolbar-title>

      <v-spacer></v-spacer>

      <v-subheader>
        <v-menu
          open-on-hover
          offset-y
        >
          <template v-slot:activator="{ on, attrs }">
            <div @click="toggleMenu" v-bind="attrs" v-on="on" class="d-flex mx-10">
              <v-icon color="yellow darken-2" size="25">mdi-folder-search</v-icon>
              <div style="font-size: 17px">문제풀기</div>
              <v-icon size="25">mdi-menu-down</v-icon>
            </div>
          </template>

          <v-list>
            <v-list-item @click="moveAllRank" class="px-5">
              <v-list-item-title> 모든 단계</v-list-item-title>
            </v-list-item>
            <v-list-item
              v-for="(item, index) in items"
              :key="index"
              @click="moveRankList(item.rank)"
            >
              <v-list-item-title>{{ item.title }}</v-list-item-title>
            </v-list-item>
          </v-list>
        </v-menu>

        <v-spacer></v-spacer>

        <div class="d-flex mx-10" style="cursor: pointer;">
          <v-icon color="yellow darken-2" size="25">mdi-video-account</v-icon>
          <div style="font-size: 17px">화상으로 함께 하기</div>
        </div>
        <!-- <div class="mx-10" style="cursor: pointer;">
          <v-icon color="yellow darken-2" size="25">mdi-microphone</v-icon>
          <v-title style="font-size: 17px">음성으로 코딩하기</v-title>
        </div> -->
        <v-spacer></v-spacer>
        <div @click="moveNotice" class="d-flex mx-10" style="cursor: pointer;">
          <v-icon color="yellow darken-2" size="25">mdi-bullhorn</v-icon>
          <div style="font-size: 17px">공지사항</div>
        </div>
        <v-spacer></v-spacer>
        <div link @click="moveCoFAQ" class="d-flex mx-10" style="cursor: pointer;">
          <v-icon color="yellow darken-2" size="25">mdi-chat-question</v-icon>
          <div style="font-size: 17px">FAQ</div>
        </div>
      </v-subheader>
    

      <v-spacer></v-spacer>

      <v-subheader>
        <div app v-if="isLogin">
          <v-avatar rounded color="red darken-1" size="40" @click="moveMyProfile" class="mx-10" style="cursor: pointer;">
            <span class="white--text headline">{{ userName }}</span>
          </v-avatar>

          <v-chip outlined small @click="logOut" color="grey darken-1">
            <span>로그아웃</span>
            <v-icon>mdi-logout</v-icon>
          </v-chip>
        </div>
        <div v-else>
          <v-chip outlined small @click="moveSignup" color="grey darken-1" class="mx-5">
            <span>회원가입</span>
            <v-icon>mdi-account</v-icon>
          </v-chip>
          <v-chip outlined small @click="moveLogin" color="grey darken-1">
            <span>로그인</span>
            <v-icon>mdi-login</v-icon>
          </v-chip>
        </div>
      </v-subheader>
    </v-toolbar>
  </div>



  <!-- <div class="mb-3">
    <v-menu
      open-on-hover
      offset-y
    >
      <template v-slot:activator="{ on, attrs }">
        <v-toolbar v-bind="attrs" v-on="on">
          <v-toolbar-title @click="moveHome" class="mx-10" style="cursor: pointer;"
            ><v-img width="90px" src="@/assets/img/codackji_logo.png"
          /></v-toolbar-title>
          <v-spacer></v-spacer>

          <div>
            문제 풀기
          </div>
          <v-spacer></v-spacer>
          <div>
            화상으로 함께 하기
          </div>
          <v-spacer></v-spacer>
          <div>
            공지사항
          </div>


          <v-spacer></v-spacer>

          <div app v-if="isLogin">
            <v-chip outlined small @click="logOut" color="grey darken-1">
              <span>로그아웃</span>
              <v-icon>mdi-logout</v-icon>
            </v-chip>
          </div>
          <div v-else>
            <v-chip outlined small @click="moveSignup" color="grey darken-1" class="mx-5">
              <span>회원가입</span>
              <v-icon>mdi-account</v-icon>
            </v-chip>
            <v-chip outlined small @click="moveLogin" color="grey darken-1">
              <span>로그인</span>
              <v-icon>mdi-login</v-icon>
            </v-chip>
          </div>
        </v-toolbar>
      </template>

      <v-list>
        <v-list-item
          v-for="(item, index) in items"
          :key="index"
        >
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-menu>
  </div> -->
</template>

<script>
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;


export default {
  name: 'Header',
  data() {
    return {
      attrs: null,
      on: null,
      drawer: false,
      isMenu: false,
      isLogin: false,
      notice: '',
      userName: '',
      items: [
        { rank: 1, title: '3 ~ 4 학년' },
        { rank: 2, title: '5 ~ 6 학년' },
        { rank: 3, title: '중등 이상' },
      ],
    };
  },
  methods: {
    moveHome() {
      this.drawer = false;
      this.$router.push({ name: 'Home' }).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
    moveSignup() {
      if (!this.isLogin) {
        this.$router.push({ name: 'SignUp' }).catch((error) => {
          if (error.name === 'NavigationDuplicated') {
            location.reload();
          }
        });
      }
    },
    moveLogin() {
      if (!this.isLogin) this.$emit('openModal', true);
    },
    moveAllRank() {
      this.drawer = false;
      this.$router.push({ name: 'AllProblemRank' }).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
    moveRankList(rank) {
      this.drawer = false;
      this.$router
        .push({ name: 'ProblemRankList', params: { problemrank: rank } })
        .then(() => location.reload()) // 문제목록 갱신을 위한 어쩔 수 없는 선택
        .catch((error) => {
          if (error.name === 'NavigationDuplicated') {
            location.reload();
          }
        });
    },
    moveMyProfile() {
      this.$router.push({ name: 'UserProfile' }).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
    moveNotice() {
      this.$router.push({ name: 'Notice' }).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
    moveCoFAQ() {
      this.$router.push({ name: 'CoFAQ' }).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
    toggleMenu() {
      this.isMenu = !this.isMenu;
    },
    logOut() {
      if (this.isLogin) {
        localStorage.removeItem('jwt');
        localStorage.removeItem('name');
        this.isLogin = false;
        this.userName = '';
      }
    },
  },
  created() {
    const token = localStorage.getItem('jwt');
    if (token) {
      this.isLogin = true;
      this.userName = localStorage.getItem('name');
    }

    this.code = this.$route.query.code;
    if (this.code != null || this.code != undefined) {
      axios
        .post(`${SERVER_URL}/kakao/login/request`, this.code)
        .then((response) => {
          history.pushState(null, "", `/`);
          let userInfo = null;
          if (response.data['oauth-result'] === 'success') {
            userInfo = response.data['userInfo'];
            localStorage.setItem('jwt', response.data['access-token']);
            localStorage.setItem('name', response.data['userInfo'].name);
            alert(userInfo.email + '님 로그인!');
            location.reload();
          } else {
            const result = confirm('이미 존재하는 이메일입니다. 카카오 계정으로 통합하시겠습니까?');
            if (result) {
              userInfo = response.data['userInfo'];
              axios.post(`${SERVER_URL}/kakao/login/merge`, userInfo).then((response) => {
                history.pushState(null, "", `/`);
                if (response.data['oauth-result'] === 'success') {
                  userInfo = response.data['userInfo'];
                  localStorage.setItem('jwt', response.data['access-token']);
                  localStorage.setItem('name', response.data['userInfo'].name);
                  alert(userInfo.name + '님! 통합 후 로그인 성공');
                  location.reload();
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
  },
};
</script>

<style></style>
