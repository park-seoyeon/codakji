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
        코딱지에서 많은 학생들이 맞춘 문제!
      </div>
    </v-sheet>
    <v-sheet class="mx-auto mt-5">
      <v-container>
        <v-row>
          <ProblemCard v-for="(problem, idx) in hitRate" :key="idx" :problem="problem" />
        </v-row>
      </v-container>
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
      hitRate: [],
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
          this.hitRate = this.top3_hit_rate_problems(this.problems);
        })
        .catch((error) => {
          console.log(error);
        });
    },

    getHitRate() {
      return this.problems.slice(0, 3);
    },

    top3_hit_rate_problems(problems) {
      var result = [
        { problem_submit: 1, problem_correct: 0 },
        { problem_submit: 1, problem_correct: 0 },
        { problem_submit: 1, problem_correct: 0 },
      ];
      // console.log(result);
      for (let index in problems) {
        if (problems[index]['problem_submit'] > 0) {
          result = this.insert(result, problems[index]);
        }
      }
      return result;
    },

    insert(result, problem) {
      // console.log(problem);
      if (result.length < 3) {
        result.push(problem);
        return result;
      }
      let correct_rate = problem['problem_correct'] / problem['problem_submit'];
      let correct_rate1 = result[0]['problem_correct'] / result[0]['problem_submit'];
      let correct_rate2 = result[1]['problem_correct'] / result[1]['problem_submit'];
      let correct_rate3 = result[2]['problem_correct'] / result[2]['problem_submit'];
      if (correct_rate > correct_rate1) {
        result.pop();
        result.splice(0, 0, problem);
      } else if (correct_rate > correct_rate2) {
        result.pop();
        result.splice(1, 0, problem);
      } else if (correct_rate > correct_rate3) {
        result.pop();
        result.push(problem);
      }
      return result;
    },
  },
  created() {
    this.getAllProblem();
  },
};
</script>

<style></style>
