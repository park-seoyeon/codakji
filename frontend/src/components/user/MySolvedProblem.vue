<template>
  <div>
    <!--문제풀이 통계-->
    <v-row>
      <v-col cols="3"></v-col>
      <v-col cols="2">
        <v-icon size="100px" color="#ffb811">mdi-paw</v-icon><br /><h3>3~4학년</h3><br />
        <v-progress-linear
          color="light-blue"
          height="20"
          value="10"
          striped
          v-model="rank1"
        >{{rank1}}%</v-progress-linear>
      </v-col>
      <v-col cols="2">
        <v-icon size="100px" color="#6f85ff">mdi-paw</v-icon><br /><h3>5~6학년</h3><br />
        <v-progress-linear
          color="light-blue"
          height="20"
          value="10"
          striped
          v-model="rank2"
        >{{rank2}}%</v-progress-linear>
      </v-col>
      <v-col cols="2">
        <v-icon size="100px" color="#ff4d56">mdi-paw</v-icon><br /><h3>중등 이상</h3><br />
        <v-progress-linear
          color="light-blue"
          height="20"
          value="10"
          striped
          v-model="rank3"
        >{{rank3}}%</v-progress-linear>
      </v-col>
      <v-col cols="3"></v-col>
    </v-row>
    <br />

    <v-row>
      <v-col cols="3"></v-col>
      <v-col cols="6">
        <v-progress-linear v-model="accuracy" height="25" color="#6cb832" striped>
          <strong>정답률: {{ accuracy }}%</strong>
        </v-progress-linear>
      </v-col>
      <v-col cols="3"></v-col>
    </v-row>
    <br />
    <br />
    <v-simple-table>
      <template v-slot:default>
        <thead>
          <tr style="background-color:#FFEBFF">
            <th class="text-center" style="width: 15%">제목</th>
            <th class="text-center" style="width: 30%">내용</th>
            <th class="text-center" style="width: 15%">유형</th>
            <th class="text-center" style="width: 10%">제출 코드</th>
            <th class="text-center" style="width: 10%">정답</th>
            <th class="text-center" style="width: 15%">제출 날짜</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="(item, index) in solvedProblems"
            :key="index"
            @click="moveResult(item)"
            style="cursor: pointer;"
          >
            <td>{{ item.problem_title }}</td>
            <td>{{ item.problem_content.substring(0, 50) }}...</td>
            <td>{{ item.problem_group }}</td>
            <td @click="showSource(index)" style="cursor: pointer">
              {{ item.language
              }}<v-icon color="#FFB911">mdi-arrow-right-bold-box</v-icon>
            </td>
            <td>
              <v-icon color="#6CB832" v-if="item.solved_problem_correct == 1"
                >mdi-check-circle</v-icon
              >
              <v-icon v-else>mdi-checkbox-blank-circle-outline</v-icon>
            </td>
            <td>{{ item.submit_at.substring(0, 10) }}</td>
          </tr>
        </tbody>
      </template>
    </v-simple-table>
  </div>
</template>

<script>
import axios from "axios";

const SERVER_URL = process.env.VUE_APP_SERVER_URL;
// const SECRET_KEY = process.env.VUE_APP_SECRET_KEY;

export default {
  name: "MySolvedProblem",
  props: {
    userInfo: Object,
  },
  data: () => {
    return {
      solvedProblems: [],
      accuracy: 0,
      rank1: 0,
      rank2: 0,
      rank3: 0,
    };
  },
  methods: {
    setStatistics(){
      axios.post(`${SERVER_URL}/problem/user/stat`, {
        token : localStorage.getItem('jwt'),
        user_number : this.userInfo.number
      }).then((response) => {
        // console.log(response);
        this.accuracy = response.data.accuracy;
        this.rank1 = response.data.rank1;
        this.rank2 = response.data.rank2;
        this.rank3 = response.data.rank3;
      })
      .catch((error)=>{
        console.log(error);
      }
      );
    },
    setSolvedProblem() {
      axios
        .post(`${SERVER_URL}/problem/user/solved`, {
          token: localStorage.getItem("jwt"),
        })
        .then((response) => {
          // console.log(response.data);
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
    moveResult(item) {
      this.$router.push({ name: 'SolveResult', params: {
        problemnumber: item.problem_number,
        resultnumber: item.solved_problem_number,
      }})
    },
  },
  created() {
    this.setSolvedProblem();
    this.setStatistics();
  },
};
</script>

<style></style>
