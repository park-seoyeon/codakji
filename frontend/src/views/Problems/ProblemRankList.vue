<template>
  <div>
    <v-sheet :color="colors[$route.params.problemrank - 1]" height="200" align="center">
      <v-row class="fill-height" align="center" justify="center">
        <div class="display-3">
          <v-container fill-height fluid>
            <div>
              <div
                class="banner text-center"
                style="font-size:40px;"
                v-if="$route.params.problemrank == 1"
              >
                3 ~ 4 학년 문제들
              </div>
              <div class="banner text-center white--text" style="font-size:40px" v-else-if="$route.params.problemrank == 2">
                5 ~ 6 학년 문제들
              </div>
              <div class="banner text-center white--text" style="font-size:40px" v-else>
                중등 이상 문제들
              </div>
              <div
                class="guide text-center"
                style="font-size:15px"
                v-if="$route.params.problemrank == 1"
              >
                단계별 문제를 차근히 풀어서 코딩 실력을 높여 보아요!
              </div>
              <div class="guide text-center white--text" style="font-size:15px" v-else>
                단계별 문제를 차근히 풀어서 코딩 실력을 높여 보아요!
              </div>
            </div>
          </v-container>
        </div>
      </v-row>
    </v-sheet>
    <v-container>
      <v-row v-if="rankProblems">
        <ProblemCard v-for="(problem, idx) in rankProblems" :key="idx" :problem="problem" />
      </v-row>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';
import ProblemCard from '@/components/problem/ProblemCard';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: 'ProblemRankList',
  data: () => {
    return {
      rankProblems: '',
      colors: [
        'grey lighten-5',
        'red lighten-1',
        'orange lighten-2',
        'amber lighten-2',
        'light-green lighten-1',
        'blue accent-1',
        'indigo lighten-2',
        'deep-purple lighten-2',
        'grey darken-3',
      ],
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
          // console.log(response.data);
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
