<template>
  <v-col cols="6" md="3">
    <v-card>
      <div>
        <v-sheet color="grey darken-2" dark>
          <v-card-text align="left" class="pa-1 guide">{{ rank.title }}</v-card-text>
        </v-sheet>
        <v-sheet>
          <div class="img_container">
            <v-img :src="rank.item" id="problem" />
            <div class="btn-plus">
              <v-btn @click="moveRankList(rank.id)" class="go" plain outlined x-large dark
                >바로가기</v-btn
              >
            </div>
          </div>
        </v-sheet>
        <v-card-title> {{ rank.content }}</v-card-title>
      </div>
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
      if (rank == 1) {
        this.$router
        .push({ name: 'ProblemRankFirst' })
        .catch((error) => {
          if (error.name === 'NavigationDuplicated') {
            location.reload();
          }
        });
      } else if (rank == 2) {
        this.$router
        .push({ name: 'ProblemRankSecond' })
        .catch((error) => {
          if (error.name === 'NavigationDuplicated') {
            location.reload();
          }
        });
      } else {
        this.$router
        .push({ name: 'ProblemRankThird' })
        .catch((error) => {
          if (error.name === 'NavigationDuplicated') {
            location.reload();
          }
        });
      }
      // this.$router
      //   .push({ name: 'ProblemRankList', params: { problemrank: rank } })
      //   .catch((error) => {
      //     if (error.name === 'NavigationDuplicated') {
      //       location.reload();
      //     }
      //   });
    },
  //   moveRankList(rank) {
  //     if (rank == 0) {
  //       this.$router.push({ name: 'AllProblemRank' }).catch((error) => {
  //         if (error.name === 'NavigationDuplicated') {
  //           location.reload();
  //         }
  //       });
  //     } else {
  //       this.$router
  //         .push({ name: 'ProblemRankList', params: { problemrank: rank } })
  //         .catch((error) => {
  //           if (error.name === 'NavigationDuplicated') {
  //             location.reload();
  //           }
  //         });
  //     }
  //   },
   },
};

</script>

<style></style>
