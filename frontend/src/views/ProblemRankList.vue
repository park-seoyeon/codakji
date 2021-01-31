<template>
  <div>
    <h1>{{ $route.params.problemrank }} 단계 문제들</h1>

    <v-container fluid>
      <v-row v-if="rankProblems">
        <ProblemCard v-for="(problem, idx) in rankProblems" :key="idx" :problem="problem" />
      </v-row>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';
import ProblemCard from '@/components/ProblemCard';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: 'ProblemRankList',
  data: () => {
    return {
      rankProblems: '',
    };
  },
  components: {
    ProblemCard,
  },
  methods: {
    getListRankProblem() {
      // console.log(window.location.href);
      // console.log(this.$route.params.problemrank);  // 현재 route 된 problemrank 값
      axios
        .get(`${SERVER_URL}/problem/rank/${this.$route.params.problemrank}`)
        .then((response) => {
          this.rankProblems = response.data;
          // console.log(response.data)
        })
        .catch((error) => {
          console.log(error);
        });
    },
  },
  created() {
    this.getListRankProblem();
  },
};
</script>

<style></style>
