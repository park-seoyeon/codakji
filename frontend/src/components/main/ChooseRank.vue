<template>
  <v-col>
    <v-sheet class="mx-auto mt-5" align="left">
      <div style=" display: inline-block;">
        <img src="@/assets/img/wheel.png" width="50" height="50" />
      </div>
      <div
        style="font-size: 30px; display: inline-block; position:absolute;"
        class="ml-5 pt-2"
        max-height="50"
      >
        코딱지 추천 문제 모음
      </div>
      <div style="font-size: 20px;" class="ml-5 pt-2 guide">
        알고리즘 실력을 늘릴 수 있는 코딱지의 문제들을 소개합니다
      </div>
    </v-sheet>
    <v-sheet class="mx-auto mt-5">
      <v-container>
        <v-row>
          <RankCard v-for="(rank, idx) in ranks" :key="idx" :rank="rank" />
        </v-row>
      </v-container>
    </v-sheet>
  </v-col>
</template>

<script>
import RankCard from '@/components/problem/RankCard';

export default {
  data: () => {
    return {
      ranks: [
        {
          id: 0,
          title: '모든 학년',
          item: require('@/assets/img/rank/all_rank.png'),
          content: '코딱지에서의 첫걸음',
        },
        {
          id: 1,
          title: '초등 3-4 학년',
          item: require('@/assets/img/rank/rank1.png'),
          content: '코딱지에서 기초를 함께',
        },
        {
          id: 2,
          title: '초등 5-6 학년',
          item: require('@/assets/img/rank/rank2.png'),
          content: '좀 더 응용된 문제를 풀러',
        },
        {
          id: 3,
          title: '중등 이상',
          item: require('@/assets/img/rank/rank3.png'),
          content: '컴퓨팅적 사고를 위해',
        },
      ],
    };
  },

  components: {
    RankCard,
  },
};
</script>

<style></style>
