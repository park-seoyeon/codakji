<template>
  <!--낙영님 안녕하세요 이 주석을 보면 봤다고 말씀 부탁드립니다! 제발 잘 전송되길...-->
  <v-container fluid>
    <v-row justify="center">
      <h3 v-if="userInfo.stat == '학생'" style="font-size:1.5em">
        <v-icon color="brown">mdi-paw</v-icon> 무엇이든 선생님에게 물어보세요
        <v-icon color="brown">mdi-paw</v-icon> <br /><br /><v-btn
          color="#6f85ff"
          ><v-icon color="white">mdi-help-rhombus</v-icon> 질문하기</v-btn
        ><br /><br /><br />
        <h4>나의 질문 목록</h4>

<br>
<v-row align="center" class="spacer" no-gutters>
                <!-- 제목 -->
                <v-col cols="4">
                  제목
                </v-col>

                <!-- 내용 -->
                <v-col cols="5">
                  내용
                </v-col>

                <!-- 질문한 날짜 -->
                <v-col cols="2">
                  날짜
                </v-col>
                <!-- 답변 유무 -->
                <v-col cols="1">
                  답변유무
                </v-col>

                
              </v-row>

      </h3>
      <h3 v-else>
        <v-icon color="brown">mdi-paw</v-icon> 최근 등록된 질문 목록
        <v-icon color="brown">mdi-paw</v-icon>
      </h3>

      <br /><br /><br />

      <!-- 학생인 경우 : 나의 질문 목록 -->
      <template v-if="userInfo.stat == '학생'">


        <v-expansion-panels popout>
          <v-expansion-panel
            v-for="(question, i) in questionList"
            :key="i"
            hide-actions
          >
            <v-expansion-panel-header
              style="border: 1px solid rgba(10,10,0,0.12)"
            >
              <v-row align="center" class="spacer" no-gutters>
                <!-- 제목 -->
                <v-col cols="4">
                  제목{{ question.question_title.substring(0, 10) }}
                </v-col>

                <!-- 내용 -->
                <v-col cols="5">
                  내용
                </v-col>

                <!-- 질문한 날짜 -->
                <v-col cols="2">
                  날짜
                </v-col>
                <!-- 답변 유무 -->
                <v-col cols="1">
                  답변유무
                </v-col>

                
              </v-row>
            </v-expansion-panel-header>

            <v-expansion-panel-content>
              <v-divider></v-divider>
              <!--상세 내용 -->
              <br />
              <v-simple-table style="width:50%; margin-left:25%;">
                <tr style="height:60px">
                  <td style="background-color:#eff1ff" width="20%">제목</td>
                  <td style="background-color:#effffc">
                    {{ question.question_title }}
                  </td>
                </tr>
                <tr style="height:60px">
                  <td style="background-color:#eff1ff">질문</td>
                  <td style="background-color:#effffc">
                    {{ question.question_content }}
                  </td>
                </tr>
                <tr style="height:60px">
                  <template v-if="question.question_reply != null">
                    <td style="background-color:#eff1ff">답변</td>
                    <td style="background-color:#effffc">
                      {{ question.question_reply }}
                    </td>
                  </template>
                </tr>
              </v-simple-table>

              <template v-if="question.question_reply == null">
                <span v-if="userInfo.stat.includes('교사')">
                  <v-btn
                    elevation="1"
                    style="background-color:#FFCA22"
                    @click="addReply"
                  >
                    <v-icon color="red">mdi-pen-plus</v-icon>답변 달기</v-btn
                  ></span
                >
              </template>
              <template v-else>
                <br /><br />
                <v-card width="30%" height="20%" style="margin-left:35%">
                  "선생님 프로필, 이름" 선생님이 답변을 달아주셨어요 :)
                </v-card>
                <br /><br />
              </template>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </template>

      <!-- 관리자나 교사 : 전체 질문 목록, 교사는 답변을 달 수 있다. -->
      <template v-else>
        <v-expansion-panels popout>
          <v-expansion-panel
            v-for="(question, i) in questionList"
            :key="i"
            hide-actions
          >
            <v-expansion-panel-header
              style="border: 1px solid rgba(10,10,0,0.12)"
            >
              <v-row align="center" class="spacer" no-gutters>
                <!--학생 프로필사진, 이름 -->
                <v-col cols="3">
                  학생 프로필사진, 이름
                </v-col>

                <!--질문 제목 -->
                <v-col cols="2">
                  {{ question.question_title.substring(0, 10) }}
                  <span v-if="question.question_title.length > 10">...</span>
                </v-col>

                <!--질문 내용-->
                <v-col cols="4">
                  질문 내용
                  {{ question.question_content.substring(0, 20) }}
                  <span v-if="question.question_content.length > 20">...</span>
                </v-col>

                <!--질문 등록 시간 -->
                <v-col cols="2">
                  {{ question.created_at.substring(0, 10) }}
                </v-col>

                <!--답변 유무 -->
                <v-col cols="1">
                  <span v-if="question.question_answered"
                    >답변완료<v-icon color="#FFB911">mdi-paw</v-icon></span
                  >
                  <span v-else>답변 대기중</span>
                </v-col>
              </v-row>
            </v-expansion-panel-header>

            <v-expansion-panel-content>
              <v-divider></v-divider>
              <!--상세 내용 -->
              <br />
              <v-simple-table style="width:50%; margin-left:25%;">
                <tr style="height:60px">
                  <td style="background-color:#eff1ff" width="20%">제목</td>
                  <td style="background-color:#effffc">
                    {{ question.question_title }}
                  </td>
                </tr>
                <tr style="height:60px">
                  <td style="background-color:#eff1ff">질문</td>
                  <td style="background-color:#effffc">
                    {{ question.question_content }}
                  </td>
                </tr>
                <tr style="height:60px">
                  <template v-if="question.question_reply != null">
                    <td style="background-color:#eff1ff">답변</td>
                    <td style="background-color:#effffc">
                      {{ question.question_reply }}
                    </td>
                  </template>
                </tr>
              </v-simple-table>

              <template v-if="question.question_reply == null">
                <span v-if="userInfo.stat.includes('교사')">
                  <v-btn
                    elevation="1"
                    style="background-color:#FFCA22"
                    @click="addReply"
                  >
                    <v-icon color="red">mdi-pen-plus</v-icon>답변 달기</v-btn
                  ></span
                >
              </template>
              <template v-else>
                <br /><br />
                <v-card width="30%" height="20%" style="margin-left:35%">
                  "선생님 프로필, 이름" 선생님이 답변을 달아주셨어요 :)
                </v-card>
                <br /><br />
              </template>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </template>
    </v-row>
  </v-container>
</template>

<script>
import axios from "axios";

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: "MyQuestion",
  props: {
    userInfo: Object,
  },
  data: () => {
    return {
      questionList: [],
    };
  },
  methods: {
    addReply() {
      //alert('답변달기');
    },
    setQuestionList() {
      if (this.userInfo.stat == "학생") {
        //나의 질문 목록
        axios
          .post(`${SERVER_URL}/question`, {
            token: localStorage.getItem("jwt"),
          })
          .then((response) => {
            console.log("나의질문리스트");
            console.log(response.data);
            this.questionList = response.data;
          })
          .catch((error) => {
            console.log(error);
          });
      } else {
        //전체 질문 목록
        axios
          .post(`${SERVER_URL}/question/all`, {
            token: localStorage.getItem("jwt"),
          })
          .then((response) => {
            console.log("전체질문리스트");
            console.log(response.data);
            this.questionList = response.data;
          })
          .catch((error) => {
            console.log(error);
          });
      }
    },
  },
  created() {
    this.setQuestionList();
  },
};
</script>

<style></style>
