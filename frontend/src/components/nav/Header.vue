<template>
  <div id="navibar" class="pb-3" @mouseover="showNaviMenu" @mouseout="hideNaviMenu">
    <div class="d-flex my-3">
      <v-toolbar-title @click="moveHome" class="mx-10" style="cursor: pointer;"
        ><v-img width="90px" src="@/assets/img/codackji_logo.png"
      /></v-toolbar-title>

      <div class="d-flex mx-10 align-self-center">
        <v-icon color="yellow darken-2" size="25">mdi-folder-search</v-icon>
        <div class="mx-1" style="font-size: 17px;">문제풀기</div>
        <v-icon size="25">mdi-menu-down</v-icon>
      </div>

      <div class="d-flex mx-10 align-self-center" style="cursor: pointer;">
        <v-icon class="mx-1" color="yellow darken-2" size="25">mdi-video-account</v-icon>
        <div style="font-size: 17px">화상으로 함께 하기</div>
      </div>

      <div class="d-flex mx-10 align-self-center">
        <v-icon class="mx-1" color="yellow darken-2" size="25">mdi-bullhorn</v-icon>
        <div style="font-size: 17px">공지사항</div>
      </div>

      <v-spacer></v-spacer>

      <v-subheader>
        <div app v-if="isLogin">
          <v-chip outlined small @click="moveMyProfile" color="grey darken-1" class="mx-5">
            <span>{{ userName }}</span>
            <v-icon>mdi-account</v-icon>
          </v-chip>

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

    </div>

    <hr>

    <div id="navimenu">
      <v-toolbar-title class="mx-10" style="visibility: hidden;"
        ><v-img width="90px" src="@/assets/img/codackji_logo.png"
      /></v-toolbar-title>

      <v-list class="mx-11" @click="hideNaviMenu">
        <v-list-item @click="moveAllRank" class="px-6">
          <v-list-item-title>학습 하기</v-list-item-title>
        </v-list-item>
        <v-list-item
          v-for="(item, index) in items"
          :key="index"
          @click="moveRankList(item.rank)"
        >
          <v-list-item-title>{{ item.title }}</v-list-item-title>
        </v-list-item>
      </v-list>

      <div class="mx-11">
        <div class="d-flex" style="visibility: hidden;">
          <v-icon color="yellow darken-2" size="25">mdi-video-account</v-icon>
          <div style="font-size: 17px">화상으로 함께 하기</div>
        </div>
      </div>

      <div class="mx-11" @click="hideNaviMenu">
        <div @click="moveNotice" class="d-flex my-5" style="cursor: pointer;">
          <v-icon color="yellow darken-2" size="25">mdi-bullhorn</v-icon>
          <div class="mx-1" style="font-size: 17px">공지사항</div>
        </div>
        <div link @click="moveCoFAQ" class="d-flex my-5" style="cursor: pointer;">
          <v-icon color="yellow darken-2" size="25">mdi-chat-question</v-icon>
          <div class="mx-1" style="font-size: 17px">FAQ</div>
        </div>
      </div>

      <v-spacer></v-spacer>

      <v-subheader>
        <div app v-if="isLogin" @click="hideNaviMenu">
          <v-chip outlined small color="grey darken-1" class="mx-5" style="visibility: hidden;">
            <span>{{ userName }}</span>
            <v-icon>mdi-account</v-icon>
          </v-chip>

          <v-chip outlined small color="grey darken-1" style="visibility: hidden;">
            <span>로그아웃</span>
            <v-icon>mdi-logout</v-icon>
          </v-chip>
        </div>
        <div v-else @click="hideNaviMenu">
          <v-chip outlined small color="grey darken-1" class="mx-5" style="visibility: hidden;">
            <span>회원가입</span>
            <v-icon>mdi-account</v-icon>
          </v-chip>
          <v-chip outlined small color="grey darken-1" style="visibility: hidden;">
            <span>로그인</span>
            <v-icon>mdi-login</v-icon>
          </v-chip>
        </div>
      </v-subheader>
    </div>
  </div>
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
      headerChecked: false,
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
        alert("로그아웃 되었습니다.");
      }
      this.$router.push({ name: 'Home' }).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
    showNaviMenu() {
      const naviMenu = document.querySelector('#navimenu');
      naviMenu.style = 'display: flex;';
    },
    hideNaviMenu() {
      const naviMenu = document.querySelector('#navimenu');
      naviMenu.style = 'display: none;';
    }
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

<style scoped>
#navibar {
  height: 100%;
  position: relative;
}

#navimenu {
  width: 100%;
  height: 200px;
  background: #fff;
  position: absolute;
  z-index: 100;
  display: none;
}


/* div[id="menuicon"] {
  display: inline-block;
  width: 100%;
  height: 100px;
  position: relative;
  cursor: pointer;
}
div[id="menuicon"] {z-index: 2;}
div[id="menuicon"] + span {background: #fff;}
div[class="topbar"] {
  width: 100%;
  height: 200px;
  background: #fff;
  position: fixed;
  top: -300px;
  z-index: 1;
  transition: all .35s;
} */

/* div[id="menuicon"] {display: none;} */
/* div[id="menuicon"] + span {
  display: block;
  position: absolute;
  width: 100%;
  height: 5px;
  border-radius: 30px;
  background: #000;
  transition: all .35s;
} */
/* input[id="menuicon"] + label span:nth-child(1) {top: 0;} */
/* input[id="menuicon"] + label span:nth-child(2) {
  top: 50%;
  transform: translateY(-50%);
} */
/* input[id="menuicon"] + label span:nth-child(3) {bottom: 0;} */
/* input[id="menuicon"]:checked + label span:nth-child(1) {
  top:50%;
  transform: translateY(-50%) rotate(45deg);
} */
/* input[id="menuicon"]:checked + label span:nth-child(2) {opacity: 0;} */
/* input[id="menuicon"]:checked + label span:nth-child(3) {
  bottom: 50%;
  transform: translateY(50%) rotate(-45deg);
} */


/* div[id=menuicon] + div {top: 100px;} */
</style>