<template>
  <div>
    <v-sheet color="#FFB911" height="300" light align="center">
      <v-row class="fill-height" align="center" justify="center">
        <div class="banner text-center" style="font-size: 40px">
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
    
    <br><br>

    <v-row>
      <v-col cols="2"></v-col>
      <v-col cols="8">
          <video v-if="title =='변수'" width="80%" controls muted loop src="@/assets/video/variable.mp4" type="video/mp4" class="videoOutline" ></video>
          <video v-if="title =='자료형'" width="80%" controls muted loop src="@/assets/video/datatype.mp4" type="video/mp4" class="videoOutline" ></video>
          <video v-if="title =='연산자'" width="80%" controls muted loop src="@/assets/video/operator.mp4" type="video/mp4" class="videoOutline" ></video>
          <video v-if="title =='입력'" width="80%" controls muted loop src="@/assets/video/input.mp4" type="video/mp4" class="videoOutline" ></video>
          <video v-if="title =='출력'" width="80%" controls muted loop src="@/assets/video/output.mp4" type="video/mp4" class="videoOutline" ></video>
          <video v-if="title =='if 조건문'" width="80%" controls muted loop src="@/assets/video/if.mp4" type="video/mp4" class="videoOutline" ></video>
          <video v-if="title =='for 반복문'" width="80%" controls muted loop src="@/assets/video/for.mp4" type="video/mp4" class="videoOutline" ></video>
          <video v-if="title =='while 반복문'" width="80%" controls muted loop src="@/assets/video/while.mp4" type="video/mp4" class="videoOutline" ></video>
          <video v-if="title =='1차원 배열'" width="80%" controls muted loop src="@/assets/video/array.mp4" type="video/mp4" class="videoOutline" ></video>
      </v-col>
    </v-row>

    <br><br><br>

    <v-btn @click="moveBasic" color="primary"><v-icon>mdi-arrow-left-thin-circle-outline</v-icon>돌아가기</v-btn>
    
    <br><br><br><br><br>
  </div>
</template>

<script>
export default {
  name: 'Basic',   
  data: () => {
    return {
      title: '',
      topic: '',
    };
  },
  methods: {
    moveBasic() {
      this.$router.push({ name: 'LearnBasic' });
    },
  },
  created() {
    this.title = this.$route.params.name;
    this.topic = this.$route.params.topic;
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
