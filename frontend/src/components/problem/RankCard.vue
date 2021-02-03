<template>
  <v-col cols="4">
    <v-card class="mx-auto" max-width="500" @click="moveRankList(rank.id)">
      <v-card-title>{{ rank.title }} 풀어보기</v-card-title>
      <v-card-text class="text--primary">{{ rank.content }}</v-card-text>
    </v-card>
  </v-col>
</template>

<script>
export default {
  props: {
    rank: Object,
  },
  methods: {
    moveRankList(rank) {
      this.$router
        .push({ name: 'ProblemRankList', params: { problemrank: rank } })
        .catch((error) => {
          if (error.name === 'NavigationDuplicated') {
            location.reload();
          }
        });
    },
  },
};
</script>

<style></style>
