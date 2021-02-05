<template>
  <v-col cols="12" sm="6" md="4" lg="3" xl="2">
    <v-card>
      <div
        @click="moveProblemDetail(problem.problem_number)"
        style="cursor: pointer;"
        class="to-right-underline"
      >
        <v-card-title> {{ problem.problem_number }}. {{ problem.problem_title }}</v-card-title>
        <v-card-text></v-card-text>
      </div>
      <v-card-actions style="background-color:#F5F5F5;">
        <v-spacer></v-spacer>

        <v-btn icon @click="likeButton()" v-if="like">
          <v-icon color="red lighten-1">mdi-heart</v-icon>
        </v-btn>
        <v-btn icon @click="likeButton()" v-else>
          <v-icon>mdi-heart</v-icon>
        </v-btn>
      </v-card-actions>
    </v-card>
  </v-col>
</template>

<script>
export default {
  data: () => {
    return {
      like: false,
    };
  },
  props: {
    problem: Object,
  },
  methods: {
    moveProblemDetail(problem) {
      this.$router
        .push({ name: 'ProblemDetail', params: { problemnumber: problem } })
        .catch((error) => {
          if (error.name === 'NavigationDuplicated') {
            location.reload();
          }
        });
    },

    likeButton() {
      this.like = !this.like;
    },
  },
};
</script>

<style></style>
