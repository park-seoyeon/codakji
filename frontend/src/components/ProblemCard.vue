<template>
  <v-col cols="12" sm="6" md="4" lg="3" xl="2"> <!-- 화면분할 더 알차게 -->
    <v-card @click="goProblemDetail">
      <v-card-title>{{ problem.problem_number }}. {{ problem.problem_title }}</v-card-title>
      <v-card-text>{{ problem.content }}</v-card-text>
    </v-card>
  </v-col>
</template>

<script>
export default {
  props: {
    problem: Object,
  },
  methods: {
    goProblemDetail() {
      this.$router.push({ name: 'ProblemDetail' }).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
  },
};
</script>

<style></style>