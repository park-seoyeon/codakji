<template>
  <v-col cols="4">
    <v-card>
      <div>
        <v-sheet color="grey lighten-1" dark>
          <v-card-text align="right" class="pa-1 guide">{{ problem.problem_group }}</v-card-text>
        </v-sheet>
        <v-sheet>
          <div class="img_container">
            <v-img :src="'/img/problem/' + problem.problem_title + '.png'" id="problem" />
            <div class="btn-plus">
              <v-btn
                @click="moveProblemDetail(problem.problem_number)"
                class="go"
                plain
                outlined
                x-large
                dark
                >바로가기</v-btn
              >
            </div>
          </div>
        </v-sheet>
        <v-card-title> {{ problem.problem_title }}</v-card-title>
        <v-card-text align="left">{{ problem.problem_content.slice(0, 30) }}...</v-card-text>
      </div>
      <!-- <v-card-actions style="background-color:#F5F5F5;">
        <v-spacer></v-spacer>

        <v-btn icon @click="likeButton()" v-if="like">
          <v-icon color="red lighten-1">mdi-heart</v-icon>
        </v-btn>
        <v-btn icon @click="likeButton()" v-else>
          <v-icon>mdi-heart</v-icon>
        </v-btn>
      </v-card-actions> -->
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
