<template>
  <v-simple-table>
    <template v-slot:default>
      <thead>
        <tr>
          <th class="text-center" style="width: 15%">제목</th>
          <th class="text-center" style="width: 30%">내용</th>
          <th class="text-center" style="width: 15%">유형</th>
          <th class="text-center" style="width: 10%">제출 코드</th>
          <th class="text-center" style="width: 10%">정답</th>
          <th class="text-center" style="width: 15%">제출 날짜</th>
        </tr>
      </thead>
      <tbody>
        <tr v-for="(item, index) in solvedProblems" :key="index">
          <td>{{ item.problem_title }}</td>
          <td>{{ item.problem_content.substring(0, 50) }}...</td>
          <td>{{ item.problem_group }}</td>
          <td @click="showSource(index)" style="cursor: pointer">
            {{ item.language }}<v-icon color="#FFB911">mdi-arrow-right-bold-box</v-icon>
          </td>          
          <td>            
            <v-icon color="#6CB832" v-if="item.solved_problem_correct == 1">mdi-check-circle</v-icon>
            <v-icon v-else>mdi-checkbox-blank-circle-outline</v-icon>
          </td>
          <td>{{ item.submit_at.substring(0,10)}}</td>
        </tr>
      </tbody>
    </template>
  </v-simple-table>
</template>

<script>
import axios from 'axios'

const SERVER_URL = process.env.VUE_APP_SERVER_URL;
// const SECRET_KEY = process.env.VUE_APP_SECRET_KEY;

export default {
  name: 'MySolvedProblem', 
  data: () => {
    return {
      solvedProblems: [],
    }
  },
  methods: {
    setSolvedProblem() {
      axios
        .post(`${SERVER_URL}/problem/user/solved`, {
          token: localStorage.getItem('jwt'),
        })
        .then((response) => {
          console.log(response.data);
          this.solvedProblems = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    showSource(idx) {
      //alert(this.solvedProblems[idx].solved_problem_content);
      this.sourceTitle = this.solvedProblems[idx].problem_title;
      this.sourceCode = this.solvedProblems[idx].solved_problem_content;
      this.isSourceCode = true;
    },
  },
  created() {
    this.setSolvedProblem();
  }
}
</script>

<style>

</style>