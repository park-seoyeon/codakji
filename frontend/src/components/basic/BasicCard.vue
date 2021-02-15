<template>
  <v-col cols="12" sm="6" md="4">

    <h2><v-icon :color="color">mdi-book-open-page-variant</v-icon> {{ basic.name }}</h2><br>

    <!-- video태그는 v-bind 로 src를 지정하면 안나오길래 일단 이렇게 해두겠습니다.... -->
    <v-hover>
    <template v-slot:default="{ hover }">
    <v-card  @click="moveTopic" height="260px" elevation="0">

      <video v-if="basic.name=='변수'" height="100%" autoplay muted loop src="@/assets/video/variable.mp4" type="video/mp4"></video>
      <video v-if="basic.name=='자료형'" height="100%" autoplay muted loop src="@/assets/video/datatype.mp4" type="video/mp4"></video>
      <video v-if="basic.name=='연산자'" height="100%" autoplay muted loop src="@/assets/video/operator.mp4" type="video/mp4"></video>
      <video v-if="basic.name=='입력'" height="100%" autoplay muted loop src="@/assets/video/input.mp4" type="video/mp4"></video>
      <video v-if="basic.name=='출력'" height="100%" autoplay muted loop src="@/assets/video/output.mp4" type="video/mp4"></video>
      <video v-if="basic.name=='if 조건문'" height="100%" autoplay muted loop src="@/assets/video/if.mp4" type="video/mp4"></video>
      <video v-if="basic.name=='for 반복문'" height="100%" autoplay muted loop src="@/assets/video/for.mp4" type="video/mp4"></video>
      <video v-if="basic.name=='while 반복문'" height="100%" autoplay muted loop src="@/assets/video/while.mp4" type="video/mp4"></video>
      <video v-if="basic.name=='1차원 배열'" height="100%" autoplay muted loop src="@/assets/video/array.mp4" type="video/mp4"></video>

      <v-fade-transition>
          <v-overlay
            v-if="hover"
            absolute
            color="#555555"
          >
          <v-btn :color="color">학습 시작하기</v-btn>
          </v-overlay>
      </v-fade-transition>
     
    </v-card>
    </template>
    </v-hover>

  <br><br>
  </v-col>
</template>

<script>
export default {
  data: () => {
    return {
      overlay: false,
      color: 'blue',
    };
  },
  props: {
    basic: Object,
  },
  methods: {
    moveTopic() {
      //선택한 주제로 페이지를 이동한다
      this.$router.push({
        name: 'Basic',
        params: {
          topic: this.basic.topic,
          name: this.basic.name,
        },
      });
    },
  },
  created(){
    //책 아이콘이랑 버튼 색상 지정
    if(this.basic.name == '변수' || this.basic.name == 'if 조건문')this.color='#6F85FF';
    if(this.basic.name == '입력' || this.basic.name == '연산자')this.color='#FF4D55';
    if(this.basic.name == '자료형' || this.basic.name == 'for 반복문')this.color='#FFB911';
    if(this.basic.name == '출력' || this.basic.name == '1차원 배열')this.color='#6CB832';
  }
};
</script>

<style>
</style>