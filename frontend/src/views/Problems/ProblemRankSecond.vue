<template>
  <div>
    <v-sheet height="300" align="center">
      <v-row class="fill-height" align="center" justify="center">
        <div class="display-3">
          <v-img src="@/assets/img/banner/problem_banner.png" height="300">
            <v-container fill-height fluid>
              <v-row class="fill-height" align="center" justify="center">
                <v-col>
                  <div
                    class="banner text-center"
                    style="font-size:40px;"
                  >
                    초등 5-6 학년
                  </div>
                  <div class="guide text-center" style="font-size:15px">
                    학년별 문제를 차근히 풀어서 코딩 실력을 높여 보아요!
                  </div>
                </v-col>
              </v-row>
            </v-container>
          </v-img>
        </div>
      </v-row>
    </v-sheet>
    <v-container>
      <v-row v-if="rankProblems" class="mt-4 mb-4">
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
  name: 'ProblemRankSecond',
  components: {
    ProblemCard,
  },
  data: () => {
    return {
      rankProblems: '',
    };
  },
  methods: {
    getListRankProblem() {
      axios
        .get(`${SERVER_URL}/problem/rank/${2}`)
        .then((response) => {
          this.rankProblems = response.data;
        })
        .catch(() => {
          alert('서버와 통신할 수 없습니다.');
        });
    },
  },
  created() {
    this.getListRankProblem();
  },
};
</script>

<style></style>
