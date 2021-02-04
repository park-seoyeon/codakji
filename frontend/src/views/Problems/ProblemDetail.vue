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
        <div align="right">
          <div v-if="description" align="right">
            해설보러가기!
          </div>
          <!-- <v-btn plain x-large> -->
          <v-img
            width="60px"
            src="@/assets/img/watting_cogi.png"
            @mouseover="mouseOver"
            @mouseleave="mouseLeave"
          />
          <!-- </v-btn> -->
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
    </v-row>

    <hr />

    <div align="left">
      <div>1. 덧글 하나</div>
      <div>2. 덧글 둘</div>
      <div>3. 덧글 셋</div>
      <div>4. 덧글 넷</div>
      <div>5. 덧글 다섯</div>
    </div>
  </v-container>
</template>

<script>
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  data: () => {
    return {
      problemDetails: '',
      description: false,
    };
  },
  created() {
    this.getProblemDetail();
  },
  methods: {
    mouseOver: function() {
      this.description = !this.description;
    },
    mouseLeave: function() {
      this.description = !this.description;
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
  },
};
</script>

<style></style>
