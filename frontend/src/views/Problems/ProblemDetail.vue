<template>
  <v-container>
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
        <!--<div align="right">
          <div v-if="description" align="right">
            해설보러가기!
          </div>
           <v-img
            width="60px"
            src="@/assets/img/watting_cogi.png"
            @mouseover="mouseOver"
            @mouseleave="mouseLeave"
          />
        </div> -->
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
        <Ide @getCode="getChildMessage"/>
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
      user_number:'',
      user_input:'',
      language:'',
      script:'',

      test_input:'',
      test_output:'',
      
      select: { lang: 'python3', mode: 'text/x-python' },
        items: [
          { lang: 'python3', mode: 'text/x-python' },
          { lang: 'java', mode: 'java' },
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
          // console.log(this.problemComments);
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
          user_number: localStorage.getItem("user_number"),
          user_input: this.test_input,
          language: this.select.lang,
          token: localStorage.getItem("jwt"),
          script: this.childMessage,
        })
        .then(res => {
          //console.log(res.data)
          this.test_output = res.data.output
        });
    },
    submit() {
      axios
        .post(`${SERVER_URL}/codeAPI/submit`, {
          problem_number: this.$route.params.problemnumber,
          user_number: localStorage.getItem("user_number"),
          user_input: "",
          language: this.select.lang,
          token: localStorage.getItem("jwt"),
          script: this.childMessage,
        })
        .then(res => {
          console.log(res.data)
        })
        .catch(error => {
          if (error.response.status === 401) {
            alert("로그인이 만료되었습니다.");
            this.$emit("expireLogin");
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
          console.log(error);
        });
      this.comment = '';
    },
  },
};
</script>

<style></style>
