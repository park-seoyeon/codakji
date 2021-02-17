<template>
  <v-col style="margin-top: 50px;">
    <v-sheet class="mx-auto mt-5" align="left">
      <div style=" display: inline-block;">
        <img src="@/assets/img/wheel.png" width="50" height="50" />
      </div>
      <div
        style="font-size: 30px; display: inline-block; position:absolute;"
        class="ml-5 pt-2"
        max-height="50"
      >
        코딱지 선정 최고의 인기 문제!
      </div>
    </v-sheet>
    <v-sheet class="mx-auto mt-5">
      <v-row>
        <ProblemCard v-for="(problem, idx) in getPopular()" :key="idx" :problem="problem" />
      </v-row>
    </v-sheet>
  </v-col>
</template>

<script>
import axios from 'axios';
import ProblemCard from '@/components/problem/ProblemCard';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;
export default {
  data: () => {
    return {
      problems: [],
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
          // console.log(this.problems[0]);
          // console.log(response.data);
          this.problems.sort(function(a, b) {
            return b.problem_submit - a.problem_submit;
          });
        })
        .catch((error) => {
          console.log(error);
        });
    },

    getPopular() {
      return this.problems.slice(0, 6);
    },
  },
  created() {
    this.getAllProblem();
  },
};
</script>

<style></style>
