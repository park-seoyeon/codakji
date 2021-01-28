<template>
    <div>
      <h1>{{ $route.params.problemrank }} 단계 문제들</h1>

      <v-layout wrap>
          <ProblemCard
            v-for="(problem, idx) in rankProblems"
            :key="idx"
            :problem="problem" />
      </v-layout>

    </div>
</template>

<script>
import axios from 'axios'
import ProblemCard from '@/components/ProblemCard'


const SERVER_URL = process.env.VUE_APP_SERVER_URL

export default {
  name: "ProblemRankList",
  data: () => {
    return {
      rankProblems: [
        { id: 1, title: "출력문 사용하기", content: "요구하는 문자를 모니터 화면에..." },
        { id: 2, title: "아 모르겠다~", content: "문제2 내용들" },
        { id: 3, title: "아모르파티", content: "문제3 내용들" },
        { id: 4, title: "문제4", content: "문제4 내용들" },
        { id: 5, title: "문제5", content: "문제5 내용들" },
      ],
    }
  },
  components: {
    ProblemCard,
  },
  methods: {

  },
  created() {
    console.log(window.location.href)
    // console.log(this.$route.params.problemrank);  // 현재 route 된 problemrank 값
    axios.get(`${SERVER_URL}/problem/rank/${this.$route.params.problemrank}`)
      .then(response => {
        console.log(response)
      })
      .catch(error => {
        console.log(error)
      })
  }
}
</script>

<style>

</style>