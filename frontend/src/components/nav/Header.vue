<template>
  <nav>
    <v-navigation-drawer app v-model="drawer">
      <span v-if="userName" @click="moveMyProfile" style="cursor: pointer;">
        <v-list-item style="padding: 15px">
          <v-badge v-if="notice.length" color="red darken-1" content="6" overlap bottom bordered>
            <v-avatar color="yellow darken-2" size="40">
              <v-icon dark size="20">
                mdi-account-circle
              </v-icon>
            </v-avatar>
          </v-badge>
          <v-avatar v-else color="yellow darken-2" size="40">
            <v-icon dark size="20">
              mdi-account-circle
            </v-icon>
          </v-avatar>
          <v-list-item-title style="font-size: 20px">{{ userName }}</v-list-item-title>
        </v-list-item>
      </span>

      <span v-else>
        <v-list-item style="padding: 15px">
          <v-badge v-if="notice.length" color="red darken-1" content="6" overlap bottom bordered>
            <v-avatar color="yellow darken-2" size="40">
              <v-icon dark size="20">
                mdi-account-circle
              </v-icon>
            </v-avatar>
          </v-badge>
          <v-avatar v-else color="yellow darken-2" size="40">
            <v-icon dark size="20">
              mdi-account-circle
            </v-icon>
          </v-avatar>

          <v-list-item-title style="font-size: 20px">
            로그인 해주세요
          </v-list-item-title>
        </v-list-item>
      </span>

      <v-divider></v-divider>

      <v-list flat align="left">
        <v-list-item>
          <v-menu offset-x max-width="100px">
            <template v-slot:activator="{ on, attrs }">
              <v-list-item @click="toggleMenu" v-bind="attrs" v-on="on" style="padding-left: 0px">
                <v-list-item-icon>
                  <v-icon color="yellow darken-2" size="25">mdi-folder-search</v-icon>
                </v-list-item-icon>
                <v-list-item-title style="font-size: 17px">문제풀기</v-list-item-title>
                <v-list-item-icon>
                  <v-icon v-if="isMenu" size="25">mdi-menu-right</v-icon>
                  <v-icon v-else size="25">mdi-menu-down</v-icon>
                </v-list-item-icon>
              </v-list-item>
            </template>
            <v-list>
              <v-list-item @click="moveAllRank" class="px-5">
                <!-- 여기 수정해야 함 -->
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
        </v-list-item>

        <v-list-item link>
          <v-list-item-icon>
            <v-icon color="yellow darken-2" size="25">mdi-video-account</v-icon>
          </v-list-item-icon>
          <v-list-item-title style="font-size: 17px">화상으로 함께 하기</v-list-item-title>
        </v-list-item>
        <v-list-item link>
          <v-list-item-icon>
            <v-icon color="yellow darken-2" size="25">mdi-microphone</v-icon>
          </v-list-item-icon>
          <v-list-item-title style="font-size: 17px">음성으로 코딩하기</v-list-item-title>
        </v-list-item>
        <v-list-item link @click="moveNotice">
          <v-list-item-icon>
            <v-icon color="yellow darken-2" size="25">mdi-bullhorn</v-icon>
          </v-list-item-icon>
          <v-list-item-title style="font-size: 17px">공지사항</v-list-item-title>
        </v-list-item>
        <v-list-item link @click="moveCoFAQ">
          <v-list-item-icon>
            <v-icon color="yellow darken-2" size="25">mdi-chat-question</v-icon>
          </v-list-item-icon>
          <v-list-item-title style="font-size: 17px">FAQ</v-list-item-title>
        </v-list-item>
      </v-list>
    </v-navigation-drawer>

    <v-app-bar app color="white" elevate-on-scroll>
      <v-app-bar-nav-icon color="grey darken-5" @click="drawer = !drawer"></v-app-bar-nav-icon>
      <v-toolbar-title @click="moveHome" style="cursor: pointer; margin-left:30px;"
        ><v-img width="90px" src="@/assets/codackji_logo.png"
      /></v-toolbar-title>

      <v-spacer></v-spacer>

      <div v-if="isLogin">
        <v-chip outlined small @click="logOut" color="grey darken-1">
          <span>로그아웃</span>
        </v-chip>
      </div>
      <v-chip outlined small @click="moveSignup" color="grey darken-1" class="mx-5">
        <span>회원가입</span>
        <v-icon>mdi-account</v-icon>
      </v-chip>
      <v-chip outlined small @click="moveLogin" color="grey darken-1">
        <span>로그인</span>
        <v-icon>mdi-login</v-icon>
      </v-chip>
    </v-app-bar>
  </nav>
</template>

<script>
export default {
  data() {
    return {
      drawer: false,
      isMenu: false,
      notice: '',
      userName: '',
      items: [
        { rank: 1, title: 'level 1' },
        { rank: 2, title: 'level 2' },
        { rank: 3, title: 'level 3' },
        { rank: 4, title: 'level 4' },
        { rank: 5, title: 'level 5' },
        { rank: 6, title: 'level 6' },
        { rank: 7, title: 'level 7' },
        { rank: 8, title: 'level 8' },
        { rank: 9, title: 'level 9' },
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
      this.$router.push({ name: 'SignUp' }).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
    moveLogin() {
      this.$emit('openModal', true);
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
      if (this.isLogin) localStorage.removeItem('jwt');
      localStorage.removeItem('name');
      this.isLogin = false;
      this.userName = '';
    },
  },
  created() {
    const token = localStorage.getItem('jwt');
    if (token) {
      this.isLogin = true;
      this.userName = localStorage.getItem('name');
    }
  },
};
</script>

<style></style>
