<template>
  <div>
    <v-sheet color="#909FFF" height="200" dark align="center">
      <v-row class="fill-height" align="center" justify="center">
        <div class="display-3">
          <v-img src="@/assets/img/banner/rank_banner.png">
            <v-container fill-height fluid>
              <v-row class="fill-height" align="center" justify="center">
                <v-col>
                  <div class="banner text-center" style="font-size:40px">학습하기</div>
                  <div class="guide text-center" style="font-size:15px">
                    코딱지에서 제공하는 문제들을 학년별로 풀어보아요
                  </div>
                </v-col>
              </v-row>
            </v-container>
          </v-img>
        </div>
      </v-row>
    </v-sheet>
    <v-container>
      <BasicProblems />
      <RecommendProblems />
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
            학년별 문제 풀러 가기
          </div>
        </v-sheet>
        <v-sheet style="margin-top: 30px;" align="center">
          <div class="recommendTitle" :class="{ on: first }" @click="recommendFirst()">
            초등 3-4 학년
          </div>
          <div class="recommendTitle" :class="{ on: second }" @click="recommendSecond()">
            초등 5-6 학년
          </div>
          <div class="recommendTitle" :class="{ on: third }" @click="recommendThird()">
            중등 이상
          </div>
        </v-sheet>
        <v-sheet class="mb-5">
          <div v-show="first" align="center">
            <v-sheet align="right" width="1024" class="mt-4">
              <v-chip
                class="ma-2"
                color="grey darken-1"
                outlined
                style="cursor: pointer;"
                @click="moveRank1()"
              >
                더 많은 푼제 풀러가기
                <v-icon right>
                  mdi-arrow-right-bold
                </v-icon>
              </v-chip>
            </v-sheet>
            <Rank1Problems />
          </div>
          <div v-show="second" align="center">
            <v-sheet align="right" width="1024" class="mt-4">
              <v-chip
                class="ma-2"
                color="grey darken-1"
                outlined
                style="cursor: pointer;"
                @click="moveRank2()"
              >
                더 많은 푼제 풀러가기
                <v-icon right>
                  mdi-arrow-right-bold
                </v-icon>
              </v-chip>
            </v-sheet>
            <Rank2Problems />
          </div>
          <div v-show="third" align="center">
            <v-sheet align="right" width="1024" class="mt-4">
              <v-chip
                class="ma-2"
                color="grey darken-1"
                outlined
                style="cursor: pointer;"
                @click="moveRank3()"
              >
                더 많은 푼제 풀러가기
                <v-icon right>
                  mdi-arrow-right-bold
                </v-icon>
              </v-chip>
            </v-sheet>
            <Rank3Problems />
          </div>
        </v-sheet>
      </v-col>
    </v-container>
  </div>
</template>

<script>
import BasicProblems from '@/components/problemList/BasicProblems';
import RecommendProblems from '@/components/problemList/RecommendProblems';
import Rank1Problems from '@/components/problemList/Rank1Problems';
import Rank2Problems from '@/components/problemList/Rank2Problems';
import Rank3Problems from '@/components/problemList/Rank3Problems';

export default {
  name: 'AllProblemRank',
  data: () => {
    return {
      first: true,
      second: false,
      third: false,
    };
  },

  components: {
    BasicProblems,
    RecommendProblems,
    Rank1Problems,
    Rank2Problems,
    Rank3Problems,
  },

  methods: {
    recommendFirst() {
      this.first = true;
      this.second = false;
      this.third = false;
    },
    recommendSecond() {
      this.first = false;
      this.second = true;
      this.third = false;
    },
    recommendThird() {
      this.first = false;
      this.second = false;
      this.third = true;
    },

    moveRank1() {
      this.$router.push({ name: 'ProblemRankList', params: { problemrank: 1 } }).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
    moveRank2() {
      this.$router.push({ name: 'ProblemRankList', params: { problemrank: 2 } }).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
    moveRank3() {
      this.$router.push({ name: 'ProblemRankList', params: { problemrank: 3 } }).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
  },
};
</script>

<style>
.recommendTitle {
  display: inline-block;
  width: 340px;
  height: 55px;
  background-color: #c3c3c3;
  color: #fff;
  text-align: center;
  font-size: 22px;
  line-height: 55px;
  vertical-align: top;
  cursor: pointer;
  margin-right: 2px;
}
.recommendTitle.on {
  background-color: #7eb649;
  background-repeat: no-repeat;
  background-position: center bottom;
}
</style>
