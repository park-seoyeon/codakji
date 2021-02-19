<template>
  <v-sheet class="mx-auto mt-5">
    <v-row>
      <ProblemCard v-for="(problem, idx) in getRank()" :key="idx" :problem="problem" />
    </v-row>
  </v-sheet>
</template>

<script>
import axios from 'axios';

import ProblemCard from '@/components/problem/ProblemCard';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  data: () => {
    return {
      problems: [],
      rank: [],
    };
  },
  components: {
    ProblemCard,
  },
  methods: {
    getAllProblem() {
      axios
        .get(`${SERVER_URL}/problem/all`)
        .then((response) => {
          this.problems = response.data;
          this.addRank(this.problems);
        })
        .catch(() => {
          alert('서버와 통신할 수 없습니다.');
        });
    },
    addRank(problems) {
      for (let index in problems) {
        if (problems[index]['problem_rank'] == 2) {
          this.rank.push(problems[index]);
        }
      }
    },
    getRank() {
      return this.rank.slice(0, 3);
    },
  },
  created() {
    this.getAllProblem();
  },
};
</script>

<style></style>
