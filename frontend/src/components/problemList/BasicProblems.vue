<template>
  <v-flex>
    <v-sheet class="mx-auto mt-5" align="left" width="1024">
      <div style=" display: inline-block;">
        <img src="@/assets/img/wheel.png" width="50" height="50" />
      </div>
      <div
        style="font-size: 30px; display: inline-block; position:absolute;"
        class="ml-5 pt-2"
        max-height="50"
      >
        처음 시작하는 학생들을 위한 기초 문제들
      </div>
    </v-sheet>
    <v-sheet class="mx-auto mt-5" width="1024">
      <v-row>
        <ProblemCard v-for="(problem, idx) in getBasic()" :key="idx" :problem="problem" />
      </v-row>
    </v-sheet>
  </v-flex>
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
        })
        .catch((error) => {
          console.log(error);
        });
    },

    getBasic() {
      return this.problems.slice(0, 3);
    },
  },
  created() {
    this.getAllProblem();
  },
};
</script>

<style></style>
