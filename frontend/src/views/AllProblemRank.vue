<template>
  <div>
    <h1>단계 선택</h1>
    <v-container>
      <v-row>
        <RankCard v-for="(rank, idx) in ranks" :key="idx" :rank="rank" />
      </v-row>
    </v-container>
  </div>
</template>

<script>
import RankCard from '@/components/RankCard';
export default {
  name: 'AllProblemRank',
  data: () => {
    return {
      ranks: [
        { id: 1, title: '1단계', content: '코딩의 기초 지식을 쌓는 1단계 문제' },
        { id: 2, title: '2단계', content: '코딩의 기초 지식을 쌓는 2단계 문제', color: '#FF5252' },
        { id: 3, title: '3단계', content: '코딩의 기초 지식을 쌓는 3단계 문제' },
        { id: 4, title: '4단계', content: '코딩의 기초 지식을 쌓는 4단계 문제' },
        { id: 5, title: '5단계', content: '코딩의 기초 지식을 쌓는 5단계 문제' },
        { id: 6, title: '6단계', content: '코딩의 기초 지식을 쌓는 6단계 문제' },
        { id: 7, title: '7단계', content: '코딩의 기초 지식을 쌓는 7단계 문제' },
        { id: 8, title: '8단계', content: '코딩의 기초 지식을 쌓는 8단계 문제' },
        { id: 9, title: '9단계', content: '코딩의 기초 지식을 쌓는 9단계 문제' },
      ],
    };
  },

  components: {
    RankCard,
  },
};
</script>

<style></style>
