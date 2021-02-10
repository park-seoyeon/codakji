<template>
  <v-col style="margin-top: 50px;">
    <v-sheet class="mx-auto mt-5" align="left" width="1024">
      <div style=" display: inline-block;">
        <img src="@/assets/img/wheel.png" width="50" height="50" />
      </div>
      <div
        style="font-size: 30px; display: inline-block; position:absolute;"
        class="ml-5 pt-2"
        max-height="50"
      >
        코딱지에서 추천하는 문제들
      </div>
    </v-sheet>
    <v-sheet class="mx-15 mt-5">
      <v-row>
        <ProblemCard v-for="(problem, idx) in getRecog()" :key="idx" :problem="problem" />
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
        })
        .catch((error) => {
          console.log(error);
        });
    },
    getRecog() {
      return this.problems.slice(this.problems.length - 3, this.problems.length);
    },
  },
  created() {
    this.getAllProblem();
  },
};
</script>

<style></style>
