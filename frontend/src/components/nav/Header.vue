<template>
  <div @mouseover="showNaviMenu" @mouseout="hideNaviMenu">
    <div id="navibar" class="pb-3">
      <div class="my-3" style="display: flex;">
        <div @click="moveHome" class="mx-10" style="cursor: pointer;">
          <v-img width="90px" src="@/assets/img/codackji_logo.png" />
        </div>

        <div id="baritem">
          <span class="d-flex">
            <div class="d-flex mx-10 align-self-center">
              <!-- <v-icon color="yellow darken-2" size="25">mdi-folder-search</v-icon> -->
              <div class="mx-1" style="font-size: 17px; cursor: pointer;" @click="moveAllRank">
                학습 하기
              </div>
            </div>

            <div class="d-flex align-self-center" style="border-left: 2px solid #e6e6e6;">
              <!-- <v-icon color="yellow darken-2" size="25">mdi-video-account</v-icon> -->
              <div class="mx-11" style="font-size: 17px; cursor: pointer; ">화상으로 함께 하기</div>
            </div>

            <div class="d-flex align-self-center" style="border-left: 2px solid #e6e6e6;">
              <!-- <v-icon color="yellow darken-2" size="25">mdi-bullhorn</v-icon> -->
              <div class="mx-11" style="font-size: 17px; cursor: pointer;" @click="moveNotice">
                공지사항
              </div>
            </div>
          </span>

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
      </div>

      <hr />

      <div id="navimenu">
        <v-toolbar-title class="mx-10" style="visibility: hidden;"
          ><v-img width="90px" src="@/assets/img/codackji_logo.png"
        /></v-toolbar-title>

        <v-list @click="hideNaviMenu">
          <v-list-item @click="moveAllRank" class="mx-6 px-6">
            <v-list-item-title>학습 하기</v-list-item-title>
          </v-list-item>
          <v-list-item v-for="(item, index) in items" :key="index" @click="moveRankList(item.rank)">
            <v-list-item-title>{{ item.title }}</v-list-item-title>
          </v-list-item>
        </v-list>

        <div class="mx-13">
          <div class="d-flex" style="visibility: hidden;">
            <!-- <v-icon color="yellow darken-2" size="25">mdi-video-account</v-icon> -->
            <div style="font-size: 17px">화상으로 함께 하기</div>
          </div>
        </div>

        <div class="mx-5" @click="hideNaviMenu" align="center">
          <div @click="moveIntroduce" class="d-flex my-5" style="cursor: pointer;" align="center">
            <!-- <v-icon color="yellow darken-2" size="25">mdi-bullhorn</v-icon> -->
            <div class="mx-1" style="font-size: 17px" align="center">코딱지는?</div>
          </div>
          <div link @click="moveNotice" class="d-flex my-5" style="cursor: pointer;" align="center">
            <!-- <v-icon color="yellow darken-2" size="25">mdi-chat-question</v-icon> -->
            <div class="mx-1" style="font-size: 17px" align="center">공지사항</div>
          </div>
          <div link @click="moveCoFAQ" class="d-flex my-5" style="cursor: pointer;" align="center">
            <!-- <v-icon color="yellow darken-2" size="25">mdi-chat-question</v-icon> -->
            <div class="mx-1" style="font-size: 17px" align="center">FAQ</div>
          </div>
        </div>

        <v-spacer></v-spacer>

        <v-subheader>
          <div app v-if="isLogin" @click="hideNaviMenu">
            <v-chip outlined small color="grey darken-1" class="mx-5" style="visibility: hidden;">
              <span>{{ userName }}</span>
              <v-icon>mdi-account</v-icon>
            </v-chip>

            <v-chip
              outlined
              small
              color="grey darken-1"
              style="visibility: hidden;"
              @click="hideNaviMenu"
            >
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

    <v-btn id="navbtn" @click="isSide = !isSide" :class="{ sidebtn: isSide }">
      <v-icon>mdi-menu</v-icon>
    </v-btn>

    <div id="smallnav">
      <div id="sidebar" :class="{ sidenav: isSide }">
        <div @click="isSide = !isSide">
          <div
            class="d-flex mx-5 mt-10 align-self-center"
            @click="moveAllRank"
            style="cursor:pointer;"
          >
            <v-icon color="yellow darken-2" size="20">mdi-folder-search</v-icon>
            <div class="mx-1" style="font-size: 20px;">학습 하기</div>
            <!-- <v-icon v-if="isMenu" size="15">mdi-menu-up</v-icon>
            <v-icon v-else size="15">mdi-menu-down</v-icon> -->
          </div>

          <v-list>
            <v-list-item @click="moveAllRank">
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

          <div class="d-flex mx-5 my-10 align-self-center" style="cursor: pointer;">
            <v-icon color="yellow darken-2" size="20">mdi-video-account</v-icon>
            <div class="mx-1" style="font-size: 20px">화상으로 함께하기</div>
          </div>

          <div @click="moveNotice" class="d-flex mx-5 my-10" style="cursor: pointer;">
            <v-icon color="yellow darken-2" size="20">mdi-bullhorn</v-icon>
            <div class="mx-1" style="font-size: 20px">공지사항</div>
          </div>

          <div link @click="moveCoFAQ" class="d-flex mx-5 my-10" style="cursor: pointer;">
            <v-icon color="yellow darken-2" size="20">mdi-chat-question</v-icon>
            <div class="mx-1" style="font-size: 20px">FAQ</div>
          </div>
        </div>
      </div>
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
      // isMenu: true,
      isSide: false,
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
    moveIntroduce() {
      this.$router.push({ name: 'Introduce' }).catch((error) => {
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
        localStorage.removeItem('user_number');
        this.isLogin = false;
        this.userName = '';
        alert('로그아웃 되었습니다.');
      }
      this.$router.push({ name: 'Home' }).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
    showNaviMenu() {
      if (window.innerWidth >= 768) {
        const naviMenu = document.querySelector('#navimenu');
        naviMenu.style = 'display: flex;';
      }
    },
    hideNaviMenu() {
      if (window.innerWidth >= 768) {
        const naviMenu = document.querySelector('#navimenu');
        naviMenu.style = 'display: none;';
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
          history.pushState(null, '', `/`);
          let userInfo = null;
          if (response.data['oauth-result'] === 'success') {
            userInfo = response.data['userInfo'];
            localStorage.setItem('jwt', response.data['access-token']);
            localStorage.setItem('name', response.data['userInfo'].name);
            localStorage.setItem('user_number', response.data['userInfo'].user_number);
            alert(userInfo.email + '님 로그인!');
            location.reload();
          } else {
            const result = confirm('이미 존재하는 이메일입니다. 카카오 계정으로 통합하시겠습니까?');
            if (result) {
              userInfo = response.data['userInfo'];
              axios.post(`${SERVER_URL}/kakao/login/merge`, userInfo).then((response) => {
                history.pushState(null, '', `/`);
                if (response.data['oauth-result'] === 'success') {
                  userInfo = response.data['userInfo'];
                  localStorage.setItem('jwt', response.data['access-token']);
                  localStorage.setItem('name', response.data['userInfo'].name);
                  localStorage.setItem('user_number', response.data['userInfo'].user_number);
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
#baritem {
  display: flex;
  width: 100%;
  justify-content: space-between;
}

#navimenu {
  width: 100%;
  height: 200px;
  background: #fff;
  position: absolute;
  z-index: 50;
  display: none;
}

#navbtn {
  display: none;
  position: absolute;
  right: 10px;
  top: 15px;
}

#smallnav {
  display: none;
}

div[id='sidebar'] {
  width: 250px;
  height: 100%;
  background: #fff;
  position: fixed;
  top: 0;
  right: -300px;
  z-index: 100;
  transition: all 0.35s;
}

.sidebtn {
  right: 260px !important;
}

.sidenav {
  right: 0px !important;
}

@media screen and (max-width: 768px) {
  #baritem {
    display: none;
    flex-direction: column;
  }

  #navbtn {
    display: flex;
    transition: all 0.35s;
  }

  #smallnav {
    display: flex;
  }

  #sidebar {
    padding-top: 0;
  }
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
