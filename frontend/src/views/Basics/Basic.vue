<template>
  <div>

  <!--배너-->
  <v-sheet color="#FFB911" height="300" light align="center">
    <v-row class="fill-height" align="center" justify="center">
      <div class="banner text-center" style="font-size: 40px">
        <!--기초부터 차근차근 배워보아요-->
        <span
          v-for="(t, index) in title"
          :key="index"
          class="item"
          :style="{ animationDelay: index * 100 + 'ms' }"
          v-text="t"
        />
      </div>
    </v-row>
  </v-sheet>

 
  <!-- 학습 영상 -->
 <br><br>
  <v-row>
    <v-col cols="2"></v-col>
    <v-col cols="8">
        <video v-if="title =='변수'" width="80%" controls muted loop src="@/assets/video/testvideo.mp4" type="video/mp4" class="videoOutline" ></video>
        <video v-if="title =='자료형'" width="80%" controls muted loop src="@/assets/video/testvideo.mp4" type="video/mp4" class="videoOutline" ></video>
        <video v-if="title =='연산자'" width="80%" controls muted loop src="@/assets/video/testvideo.mp4" type="video/mp4" class="videoOutline" ></video>
        <video v-if="title =='입력'" width="80%" controls muted loop src="@/assets/video/testvideo.mp4" type="video/mp4" class="videoOutline" ></video>
        <video v-if="title =='출력'" width="80%" controls muted loop src="@/assets/video/testvideo.mp4" type="video/mp4" class="videoOutline" ></video>
        <video v-if="title =='if 조건문'" width="80%" controls muted loop src="@/assets/video/testvideo.mp4" type="video/mp4" class="videoOutline" ></video>
        <video v-if="title =='for 반복문'" width="80%" controls muted loop src="@/assets/video/testvideo.mp4" type="video/mp4" class="videoOutline" ></video>
        <video v-if="title =='while 반복문'" width="80%" controls muted loop src="@/assets/video/testvideo.mp4" type="video/mp4" class="videoOutline" ></video>
        <video v-if="title =='1차원 배열'" width="80%" controls muted loop src="@/assets/video/testvideo.mp4" type="video/mp4" class="videoOutline" ></video>
    </v-col>
  </v-row>

  <!-- 코드 실행창 -->
  따라해보세요 실습 이미지 | 코드 실행창
  <!-- 좋아요 버튼, 댓글 -->    
  <!--뒤로가기 버튼 -->

  <br>
  <v-btn @click="back" color="primary"><v-icon>mdi-arrow-left-thin-circle-outline</v-icon>돌아가기</v-btn>
  <br><br><br><br><br>

  </div>
</template>

<script>
export default {
  name: 'Basic',   
  data: () => {
    return {
      title: '', //한글 예)자료형
      topic: '', //영어 예) datatype
    };
  },
  methods: {
    back() {
      alert('뒤로가기 어떻게 하나요');
     
    },
  },
  created() {
    this.title = this.$route.params.name;
    this.topic = this.$route.params.name;
  },
};
</script>

<style scoped>
@keyframes text-in {
  0% {
    transform: translate(0, -20px);
    opacity: 0;
  }
}
.item {
  display: inline-block;
  min-width: 0.3em;
  font-size: 2rem;
  animation: text-in 0.8s cubic-bezier(0.22, 0.15, 0.25, 1.43) 0s backwards;
}

.videoOutline:focus{
  outline: none;
}
</style>
