<template>
  <v-col cols="12" sm="6" md="4">
    <v-card @click="moveProblemDetail(problem.problem_number)">
      <v-sheet color="grey lighten-1" dark>
        <v-card-text align="right" class="pa-1 guide">{{ problem.problem_group }}</v-card-text>
      </v-sheet>
      <v-sheet>
        <div class="img_container">
          <v-img :src="problem.problem_img" id="problem" />
          <div class="btn-plus">
            <v-btn class="go" plain outlined x-large dark>바로가기</v-btn>
          </div>
        </div>
      </v-sheet>
      <v-card-title> {{ problem.problem_title }}</v-card-title>
      <v-card-text align="left">{{ problem.problem_content.slice(0, 30) }}...</v-card-text>
    </v-card>
  </v-col>
</template>

<script>
export default {
  data: () => {
    return {
      isLogin: false,
    };
  },
  props: {
    problem: Object,
  },
  methods: {
    moveProblemDetail(problem) {
      if (this.isLogin) {
        this.$router
          .push({ name: 'ProblemDetail', params: { problemnumber: problem } })
          .catch((error) => {
            if (error.name === 'NavigationDuplicated') {
              location.reload();
            }
          });
      } else {
        alert('로그인이 필요합니다');
      }
    },
    likeButton() {
      this.like = !this.like;
    },
  },
  created() {
    const token = localStorage.getItem('jwt');
    if (token) {
      this.isLogin = true;
    }
  },
};
</script>

<style></style>
