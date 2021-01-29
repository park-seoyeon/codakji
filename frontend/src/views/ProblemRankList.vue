<template>
  <div>
    <h1>{{ $route.params.problemrank }} 단계 문제들</h1>

    <v-container fluid>
      <v-row>
        <ProblemCard v-for="(problem, idx) in rankProblems" :key="idx" :problem="problem" />
      </v-row>
    </v-container>
  </div>
</template>

<script>
import axios from 'axios';
import ProblemCard from '@/components/ProblemCard';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: 'ProblemRankList',
  data: () => {
    return {
      rankProblems: [
        {
          id: 1,
          title: 'We Love Kriii',
          content: 'ACM-ICPC 인터넷 예선, Regional, 그리고 World Finals까지 ...',
        },
        {
          id: 2,
          title: '알람 시계',
          content: '상근이는 매일 아침 알람을 듣고 일어난다. ...',
        },
        { id: 3, title: '나머지', content: '(A+B)%C는 ((A%C) + (B%C))%C 와 같을까? ...' },
        {
          id: 4,
          title: '사분면 고르기',
          content: '흔한 수학 문제 중 하나는 주어진 점이 어느 사분면 ...',
        },
        { id: 5, title: '곱셈', content: '(세 자리 수) x (세 자리 수)는 다음과 같은 과정을 ...' },
      ],
    };
  },
  components: {
    ProblemCard,
  },
  methods: {},
  created() {
    console.log(window.location.href);
    // console.log(this.$route.params.problemrank);  // 현재 route 된 problemrank 값
    axios
      .get(`${SERVER_URL}/problem/rank/${this.$route.params.problemrank}`)
      .then((response) => {
        console.log(response);
      })
      .catch((error) => {
        console.log(error);
      });
  },
};
</script>

<style></style>