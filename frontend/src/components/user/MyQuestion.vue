<template>
  <div>
    <v-container fluid>
      <h2 v-if="userInfo.stat == '학생'" style="font-size:1.5em">
        <v-icon color="brown">mdi-paw</v-icon> 무엇이든 선생님에게 물어보세요
        <v-icon color="brown">mdi-paw</v-icon> <br /><br />
        <v-btn color="#6f85ff" @click="isDialog = true">
          <v-icon color="white">mdi-help-rhombus</v-icon><span style="color:white">질문하기</span>
        </v-btn>
      </h2>
      <h2 v-else>
        <v-icon color="brown">mdi-paw</v-icon> 최근 등록된 질문 목록
        <v-icon color="brown">mdi-paw</v-icon>
      </h2>

      <br /><br /><br />

      <template v-if="userInfo.stat == '학생'">
        <v-row style="background-color:#FFFFCC">
          <v-col cols="3" style="margin-left:-5%"><h3 style="color:#6F85FF">제목</h3></v-col>
          <v-col cols="4" style="margin-left:-5%"><h3 style="color:#6CB832">내용</h3></v-col>
          <v-col cols="2" style="margin-left:2%"><h3 style="color:#FFB911">질문 날짜</h3></v-col>
          <v-col cols="2" style="margin-left:-1%"><h3 style="color:#FF4D55">관련 문제</h3></v-col>
          <v-col cols="1" style="margin-left:3%"><h3 style="color:#6F85FF">답변 현황</h3></v-col>
        </v-row>
        <br />
        <v-expansion-panels popout>
          <v-expansion-panel
            v-for="(question, i) in questionList"
            :key="i"
            hide-actions
            @click="rowClick(question)"
          >
            <v-expansion-panel-header style="border: 1px solid rgba(10,10,0,0.12)">
              <v-row align="center" class="spacer" no-gutters>
                <v-col cols="3">
                  {{ question.question_title.substring(0, 10) }}
                  <span v-if="question.question_title.length > 10">...</span>
                </v-col>
                <v-col cols="4">
                  {{ question.question_content.substring(0, 20) }}
                  <span v-if="question.question_content.length > 20">...</span>
                </v-col>
                <v-col cols="2">
                  {{ question.created_at.substring(0, 10) }}
                </v-col>
                <v-col cols="2">
                  <span v-if="question.problem_number == null || question.problem_number == 0">
                    ㅡ
                  </span>
                  <span v-else> [{{ question.problem_number }}] {{ question.problem_title }}</span>
                </v-col>
                <v-col cols="1">
                  <span v-if="question.question_answered"
                    >답변완료<v-icon color="#FFB911" size="16px">mdi-paw</v-icon></span
                  >
                  <span v-else>답변 대기중</span>
                </v-col>
              </v-row>
            </v-expansion-panel-header>

            <v-expansion-panel-content>
              <v-divider></v-divider>
              <br />
              <v-simple-table style="width:50%; margin-left:25%;">
                <tr style="height:60px">
                  <td
                    style="background-color:#eff1ff;border-bottom: 1px solid #DDDDDD;border-top: 1px solid #DDDDDD"
                    width="20%"
                  >
                    제목
                  </td>
                  <td
                    style="background-color:#effffc;border-bottom: 1px solid #DDDDDD;border-top: 1px solid #DDDDDD"
                  >
                    {{ question.question_title }}
                  </td>
                </tr>
                <tr style="height:60px">
                  <td style="background-color:#eff1ff;border-bottom: 1px solid #DDDDDD">
                    질문
                  </td>
                  <td style="background-color:#effffc;border-bottom: 1px solid #DDDDDD">
                    {{ question.question_content }}
                  </td>
                </tr>
                <tr
                  style="height:60px"
                  v-if="question.problem_number != null && question.problem_number != 0"
                >
                  <template>
                    <td style="background-color:#eff1ff;border-bottom: 1px solid #DDDDDD">
                      관련 문제
                    </td>
                    <td
                      style="background-color:#effffc;border-bottom: 1px solid #DDDDDD; cursor:pointer"
                    >
                      [{{ question.problem_number }}] {{ question.problem_title
                      }}<v-icon color="#FFB911">mdi-arrow-right-bold-box</v-icon>
                    </td>
                  </template>
                </tr>
                <tr style="height:60px">
                  <template v-if="question.question_reply != null">
                    <td style="background-color:#eff1ff;border-bottom: 1px solid #DDDDDD">
                      답변
                    </td>
                    <td style="background-color:#effffc;border-bottom: 1px solid #DDDDDD">
                      {{ question.question_reply }}
                    </td>
                  </template>
                </tr>
              </v-simple-table>

              <template v-if="question.question_reply == null">
                <span> "아직 선생님의 답변을 기다리는 중이에요"</span>
                <br /><br />
                <v-btn text @click="deleteQuestion" style="font-size:1em">
                  |<v-icon color="#555555" size="20">mdi-delete-outline</v-icon>
                  질문 삭제하기 |
                </v-btn>
              </template>
              <template v-else>
                <v-card width="30%" min-height="100px" style="margin-left:35%">
                  <v-card-text>
                    <img :src="question.teach_profile" width="20%" />
                    <h2>{{ question.teach_name }}</h2>
                    선생님이 답변을 달아주셨어요:)
                  </v-card-text>
                </v-card>
              </template>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </template>

      <template v-else>
        <v-row style="background-color:#FFFFCC">
          <v-col cols="3"><h3 style="color:#6F85FF">질문 학생</h3></v-col>
          <v-col cols="2" style="margin-left: -5%"><h3 style="color:#6CB832">제목</h3></v-col>
          <v-col cols="4" style="margin-left: -5%"><h3 style="color:#FFB911">질문 내용</h3></v-col>
          <v-col cols="2" style="margin-left: 1%"><h3 style="color:#FF4D55">질문 날짜</h3></v-col>
          <v-col cols="1" style="margin-left: 3%"><h3 style="color:#6F85FF">답변 현황</h3></v-col>
        </v-row>
        <br />
        <v-expansion-panels popout>
          <v-expansion-panel
            v-for="(question, i) in questionList"
            :key="i"
            hide-actions
            @click="rowClick(question)"
          >
            <v-expansion-panel-header style="border: 1px solid rgba(10,10,0,0.12)">
              <v-row align="center" class="spacer" no-gutters>
                <v-col cols="3">
                  <img :src="question.profile_content" width="10%" />
                  <h3>{{ question.name }}</h3>
                </v-col>

                <v-col cols="2">
                  {{ question.question_title.substring(0, 10) }}
                  <span v-if="question.question_title.length > 10">...</span>
                </v-col>

                <v-col cols="4">
                  {{ question.question_content.substring(0, 20) }}
                  <span v-if="question.question_content.length > 20">...</span>
                </v-col>

                <v-col cols="2">
                  {{ question.created_at.substring(0, 10) }}
                </v-col>

                <v-col cols="1">
                  <span v-if="question.question_answered"
                    >답변완료<v-icon color="#FFB911" size="16px">mdi-paw</v-icon></span
                  >
                  <span v-else>답변 대기중</span>
                </v-col>
              </v-row>
            </v-expansion-panel-header>

            <v-expansion-panel-content>
              <v-divider></v-divider>
              <br />
              <v-simple-table style="width:50%; margin-left:25%;">
                <tr style="height:60px">
                  <td
                    style="background-color:#eff1ff;border-bottom: 1px solid #DDDDDD;border-top: 1px solid #DDDDDD"
                    width="20%"
                  >
                    제목
                  </td>
                  <td
                    style="background-color:#effffc;border-bottom: 1px solid #DDDDDD;border-top: 1px solid #DDDDDD"
                  >
                    {{ question.question_title }}
                  </td>
                </tr>
                <tr style="height:60px">
                  <td style="background-color:#eff1ff;border-bottom: 1px solid #DDDDDD">
                    질문
                  </td>
                  <td style="background-color:#effffc;border-bottom: 1px solid #DDDDDD">
                    {{ question.question_content }}
                  </td>
                </tr>
                <tr
                  style="height:60px"
                  v-if="question.problem_number != null && question.problem_number != 0"
                >
                  <template>
                    <td style="background-color:#eff1ff;border-bottom: 1px solid #DDDDDD">
                      관련 문제
                    </td>
                    <td
                      style="background-color:#effffc;border-bottom: 1px solid #DDDDDD; cursor:pointer"
                    >
                      [{{ question.problem_number }}] {{ question.problem_title
                      }}<v-icon color="#FFB911">mdi-arrow-right-bold-box</v-icon>
                    </td>
                  </template>
                </tr>
                <tr style="height:60px">
                  <template v-if="question.question_reply != null">
                    <td style="background-color:#eff1ff;border-bottom: 1px solid #DDDDDD">
                      답변
                    </td>
                    <td style="background-color:#effffc;border-bottom: 1px solid #DDDDDD">
                      {{ question.question_reply }}
                    </td>
                  </template>
                </tr>
              </v-simple-table>

              <template v-if="question.question_reply == null">
                <span v-if="userInfo.stat.includes('교사')">
                  <v-btn elevation="1" style="background-color:#FFCA22" @click="isDialog = true">
                    <v-icon color="red">mdi-pen-plus</v-icon>답변 달기</v-btn
                  ></span
                >
              </template>
              <template v-else>
                <br /><br />
                <v-card width="30%" min-height="100px" style="margin-left:35%">
                  <img :src="question.teach_profile" width="20%" />
                  <h2>{{ question.teach_name }}</h2>
                  선생님이 답변을 달아주셨어요:)
                </v-card>
                <br /><br />
              </template>
            </v-expansion-panel-content>
          </v-expansion-panel>
        </v-expansion-panels>
      </template>
    </v-container>

    <v-dialog v-model="isDialog" width="50%">
      <v-card min-height="500px" style="position: relative">
        <v-card-title style="background-color:#FAFAFA">
          <img src="@/assets/img/codackji_logo.png" height="30px" />
        </v-card-title>

        <v-card-text v-if="userInfo.stat == '학생'" style="font-size:1.2em;">
          <br />
          <v-textarea
            label="제목"
            auto-grow
            outlined
            rows="1"
            row-height="10"
            v-model="title"
          ></v-textarea>
          <v-textarea
            label="질문 내용"
            auto-grow
            outlined
            rows="10"
            row-height="40"
            v-model="content"
          ></v-textarea>
        </v-card-text>

        <v-card-text v-else style="font-size:1.2em;">
          <br />
          <v-textarea
            label="답변 내용"
            auto-grow
            outlined
            rows="10"
            row-height="40"
            v-model="content"
          ></v-textarea>
        </v-card-text>

        <v-divider></v-divider>

        <v-card-actions>
          <v-spacer></v-spacer>
          <v-chip
            class="mr-2"
            close
            color="red darken-1"
            outlined
            @click="cancelQuestion"
            style="font-size:1.2em"
          >
            취소하기
          </v-chip>
          <v-chip @click="upload" outlined style="font-size:1.2em" color="primary">
            작성하기
            <v-icon right size="20">mdi-lead-pencil</v-icon>
          </v-chip>
        </v-card-actions>
      </v-card>
    </v-dialog>
  </div>
</template>

<script>
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: 'MyQuestion',
  props: {
    userInfo: Object,
  },
  data: () => {
    return {
      questionList: [],
      isDialog: false,
      title: '',
      content: '',
      selected: [],
    };
  },
  methods: {
    rowClick(item) {
      this.selected = item;
    },
    upload() {
      if (this.userInfo.stat == '학생') {
        if (confirm('질문을 등록하시겠습니까?')) {
          axios
            .post(`${SERVER_URL}/question/write`, {
              token: localStorage.getItem('jwt'),
              user_number: this.userInfo.number,
              question_title: this.title,
              question_content: this.content,
            })
            .then(() => {
              alert('질문이 등록되었습니다!');
              this.setQuestionList();
              this.cancelQuestion();
            })
            .catch(() => {
              alert('서버와 통신할 수 없습니다.');
            });
        }
      } else {
        axios
          .put(`${SERVER_URL}/answer/write`, {
            token: localStorage.getItem('jwt'),
            teach_user_number: this.userInfo.number,
            question_number: this.selected.question_number,
            question_reply: this.content,
          })
          .then(() => {
            alert('답변이 등록되었습니다!');
            location.reload();
          })
          .catch(() => {
            alert('서버와 통신할 수 없습니다.');
          });
      }
    },
    cancelQuestion() {
      this.title = '';
      this.content = '';
      this.isDialog = false;
    },
    deleteQuestion() {
      if (confirm('질문을 삭제하시겠습니까?')) {
        axios
          .delete(`${SERVER_URL}/question`, {
            data: {
              token: localStorage.getItem('jwt'),
              question_number: this.selected.question_number,
            },
          })
          .then(() => {
            alert('질문이 삭제되었습니다!');
            location.reload();
          })
          .catch(() => {
            alert('서버와 통신할 수 없습니다.');
          });
      }
    },
    setQuestionList() {
      if (this.userInfo.stat == '학생') {
        axios
          .post(`${SERVER_URL}/question`, {
            token: localStorage.getItem('jwt'),
          })
          .then((response) => {
            this.questionList = response.data;
          })
          .catch(() => {
            alert('서버와 통신할 수 없습니다.');
          });
      } else {
        axios
          .post(`${SERVER_URL}/question/all`, {
            token: localStorage.getItem('jwt'),
          })
          .then((response) => {
            this.questionList = response.data;
          })
          .catch(() => {
            alert('서버와 통신할 수 없습니다.');
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
