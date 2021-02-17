<template>
  <v-container class="mt-16">
    <v-row>
      <v-col cols="6">
        <div align="left">
          <div>
            <h1>{{ problemDetails.problem_title }}</h1>
            <p>&lt;문제 유형: {{ problemDetails.problem_group }}&gt;</p>
          </div>
          <div>
            <h3>문제</h3>
            <p>{{ problemDetails.problem_content }}</p>
          </div>
          <div>
            <h3>문제 입력</h3>
            <p>{{ problemDetails.problem_input }}</p>
          </div>
          <div>
            <h3>문제 출력</h3>
            <p>{{ problemDetails.problem_output }}</p>
          </div>
        </div>
      </v-col>

      <!-- <v-col cols="6">
        <iframe
          width="100%"
          height="500"
          src="https://jsfiddle.net/chrisvfritz/0dzvcf4d/embedded/result,html,js,css"
          allowfullscreen="allowfullscreen"
          frameborder="0"
        ></iframe>
      </v-col> -->
      <v-col cols="6">
        <v-col cols="3">
          <v-select
            v-model="select"
            :items="items"
            item-text="lang"
            item-value="mode"
            label="Select"
            persistent-hint
            return-object
            single-line
          ></v-select>
        </v-col>
        <Ide @getCode="getChildMessage" />
      </v-col>
    </v-row>
    <v-row class="mt-10" align="center">
      <v-col class="mr-5" style="background-color: #F0F0F0;">
        <div align="left">
          <h3>예제 입력</h3>
        </div>
        <v-textarea v-model="problemDetails.problem_testcase_input1" readonly> </v-textarea>
      </v-col>
      <v-col class="ml-5" style="background-color: #F0F0F0;">
        <div align="left">
          <h3>예제 출력</h3>
        </div>
        <v-textarea v-model="problemDetails.problem_testcase_output1" readonly> </v-textarea>
      </v-col>
    </v-row>
    <v-row class="mt-7">
      <v-col cols="12" align="left">
        <h3>Test</h3>
      </v-col>
      <v-container class="pd-2" style="background-color: #F9F9F9; border-top: solid 1px;">
        <div class="ma-5 pb-3 guide" style="border-bottom: solid 1px #EAEAEA;" align="left">
          Input 을 입력하고 테스트하기를 선택하면 Output 결과를 확인할 수 있습니다. Input 값을 넣지
          않으면 기본 Input값이 적용되어 실행됩니다. Test는 채점을 하는 것이 아니며 정답 여부를
          알려주지 않습니다.
        </div>
        <v-row class="mt-4" align="center">
          <v-col cols="6" align="left">
            <div class="mb-3">
              <h3>Input</h3>
            </div>
            <v-textarea solo v-model="test_input" placeholder="Input 값을 입력하시오"></v-textarea>
          </v-col>
          <v-col cols="6" align="left">
            <div class="mb-3">
              <h3>Output</h3>
            </div>
            <v-textarea
              solo
              v-model="test_output"
              placeholder="Output 값이 출력됩니다"
            ></v-textarea>
          </v-col>
        </v-row>
      </v-container>
      <v-col align="center">
        <v-chip class="mr-5" color="indigo darken-1" label large outlined @click="test()">
          테스트하기
          <v-icon right>
            mdi-replay
          </v-icon>
        </v-chip>
        <v-chip class="ml-5" dark color="indigo darken-1" label large @click="submit()">
          제출하기
          <v-icon right>
            mdi-arrow-right-bold
          </v-icon>
        </v-chip>
      </v-col>
    </v-row>
    <v-col cols="12" class="mt-10 guide" align="left">
      <div class="mb-3">
        <span>댓글</span><span style="font-size:10px;"> ({{ problemComments.length }})</span>
      </div>
      <hr class="mb-2" />
      <v-row>
        <v-col cols="11" class="pr-0 p1-2">
          <v-textarea
            solo
            label="댓글을 입력해주세요"
            :counter="100"
            height="100"
            v-model="comment"
          ></v-textarea>
        </v-col>
        <v-col cols="1" class="px-0">
          <v-btn color="grey darken-2" dark height="100" @click="writeComment()">댓글 쓰기</v-btn>
        </v-col>
      </v-row>
      <Comment v-for="(comment, idx) in visiblePages()" :key="idx" :comment="comment" />
    </v-col>
    <v-pagination
      class="mt-2"
      color="grey darken-2"
      v-model="page"
      :length="Math.ceil(problemComments.length / perPage)"
      circle
      total-visible="perPage"
      page-aria-label="problemComments"
    ></v-pagination>
  </v-container>
</template>

<script>
import Ide from './../../components/problem/Ide.vue';
import Comment from '@/components/problem/Comment';
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  data: () => {
    return {
      page: 1,
      perPage: 5,
      problemDetails: '',
      description: false,
      problemComments: '',
      comment: '',
      childMessage: '',
      problem_number: '',
      user_number: '',
      user_input: '',
      language: '',
      script: '',

      test_input: '',
      test_output: '',

      select: { lang: 'python3', mode: 'text/x-python' },
      items: [
        { lang: 'python3', mode: 'text/x-python' },
        { lang: 'java', mode: 'java' },
        { lang: 'c', mode: 'c' },
        { lang: 'cpp', mode: 'cpp' },
      ],
    };
  },
  components: {
    Ide,
    Comment,
  },
  created() {
    this.getProblemDetail();
    this.getProblemComment();
  },
  computed: {},
  methods: {
    visiblePages() {
      return this.problemComments.slice((this.page - 1) * this.perPage, this.page * this.perPage);
    },
    getProblemDetail() {
      axios
        .get(`${SERVER_URL}/problem/${this.$route.params.problemnumber}`)
        .then((response) => {
          this.problemDetails = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },

    getProblemComment() {
      axios
        .get(`${SERVER_URL}/problem/comment/${this.$route.params.problemnumber}`)
        .then((response) => {
          this.problemComments = response.data;
        })
        .catch((error) => {
          console.log(error);
        });
    },

    getChildMessage: function(text) {
      this.childMessage = text;
    },
    test() {
      axios
        .post(`${SERVER_URL}/codeAPI/test`, {
          problem_number: this.$route.params.problemnumber,
          user_number: localStorage.getItem('user_number'),
          user_input: this.test_input,
          language: this.select.lang,
          token: localStorage.getItem('jwt'),
          script: this.childMessage,
        })
        .then((res) => {
          this.test_output = res.data.output;
        });
    },
    submit() {
      const solvedInfo = {
        problem_number: this.$route.params.problemnumber,
        user_number: localStorage.getItem('user_number'),
        user_input: '',
        language: this.select.lang,
        token: localStorage.getItem('jwt'),
        script: this.childMessage,
      };

      axios
        .post(`${SERVER_URL}/codeAPI/submit`, solvedInfo)
        .then((res) => {
          this.$router.push({
            name: 'SolveResult',
            params: {
              problemnumber: solvedInfo.problem_number,
              resultnumber: res.data,
            },
          });
        })
        .catch((error) => {
          if (error.response.status === 401) {
            alert('세션이 만료되었습니다.');
            this.$emit('expireLogin');
          } else {
            console.log(error);
          }
        });
    },
    writeComment() {
      axios
        .post(`${SERVER_URL}/problem/comment`, {
          token: localStorage.getItem('jwt'),
          user_number: localStorage.getItem('user_number'),
          problem_number: this.$route.params.problemnumber,
          comment_content: this.comment,
        })
        .then(({ data }) => {
          let msg = '댓글 쓰기에 실패하였습니다.';
          if (data === 'success') {
            this.getProblemComment();
          } else {
            alert(msg);
          }
        })
        .catch((error) => {
          if (error.response.status === 401) {
            alert('세션이 만료되었습니다.');
            this.$emit('expireLogin');
          } else {
            console.log(error);
          }
        });
      this.comment = '';
    },
  },
};
</script>

<style></style>
