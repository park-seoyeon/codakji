<template>
  <v-app>
    <Header @openModal="toggleModal" />
    <v-main>
      <router-view />
    </v-main>
    <ModalCareer v-if="isModal" @closeModal="toggleModal" />
    <Footer />
  </v-app>
</template>
<script>
import Header from '@/components/nav/Header.vue';
import Footer from '@/components/nav/Footer.vue';
import ModalCareer from '@/components/modal/ModalCareer';

export default {
  data: () => {
    return {
      isModal: false,
    };
  },
  components: {
    Header,
    Footer,
    ModalCareer,
  },
  methods: {
    toggleModal(data) {
      this.isModal = data;
    },
  },
};
</script>
<style lang="scss">
@import url('https://fonts.googleapis.com/css2?family=Do+Hyeon&display=swap');
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@300&display=swap');
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
footer {
  font-family: 'Noto Sans KR', sans-serif;
}
.guide {
  font-family: 'Noto Sans KR', sans-serif;
}
</style>
