<template>
  <v-col cols="4" class="mt-3">
    <v-card elevation="3" class="mx-auto" width="300px" @click="moveRankList(rank.id)" id="rank">
      <div align="center" class="pt-2">
        <v-sheet :color="rank.color" height="120" max-width="95%" rounded elevation="2"></v-sheet>
      </div>
      <v-card-title>{{ rank.title }} 풀어보기</v-card-title>
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
