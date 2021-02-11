<template>
  <v-container fluid>
    <v-row justify="center">
      <v-subheader v-if="userInfo.stat == '학생'" style="font-size:1.5em"
        >무엇이든 선생님에게 물어보세요</v-subheader
      >
      <v-subheader v-else>최근 등록된 질문 목록</v-subheader>

      <br /><br /><br />

      <v-expansion-panels popout>
        <v-expansion-panel
          v-for="(question, i) in questionList"
          :key="i"
          hide-actions
        >
          <v-expansion-panel-header>
            <v-row align="center" class="spacer" no-gutters>
              <!--선생님 프로필사진 -->
              <v-col>
                선생님 프로필사진
              </v-col>

              <!--선생님 이름 -->
              <v-col>
                선생님 이름
              </v-col>

              <!--질문 제목 -->
              <v-col>
                {{ question.question_title.substring(0, 50) }}...
              </v-col>

              <!--질문 등록 시간 -->
              <v-col>
                {{ question.created_at }}
              </v-col>

              <!--답변 유무 -->
              <v-col>
                <span v-if="question.question_answered">답변완료</span>
                <span v-else>답변 대기중</span>
              </v-col>
            </v-row>
          </v-expansion-panel-header>

          <v-expansion-panel-content>
            <v-divider></v-divider>
            <!--답변 내용 -->
            <v-card-text>{{ question.question_reply }}</v-card-text>
          </v-expansion-panel-content>
        </v-expansion-panel>
      </v-expansion-panels>
    </v-row>
  </v-container>
</template>

<script>
import axios from 'axios'

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: 'MyQuestion',
  props: {
    userInfo: Object,
  },
  data: () => {
    return {
      questionList: [],
    }
  },
  methods: {
    setQuestionList() {
      if (this.userInfo.stat == '학생') {
        //나의 질문 목록
        axios
          .post(`${SERVER_URL}/question`, {
            token: localStorage.getItem('jwt'),
          })
          .then((response) => {
            console.log('나의질문리스트');
            console.log(response.data);
            this.questionList = response.data;
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        //전체 질문 목록
      }
    },
  },
  created() {
    this.setQuestionList();
  }
}
</script>

<style>

</style>