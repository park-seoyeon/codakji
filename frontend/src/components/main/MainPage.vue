<template>
  <div>
    <v-carousel cycle height="420" hide-delimiter-background show-arrows-on-hover>
      <v-carousel-item v-for="(item, i) in items" :key="i">
        <v-sheet :color="colors[i]" height="100%">
          <v-row class="fill-height" align="center" justify="center">
            <div class="display-3">
              <v-img :src="item.src" align="left">
                <v-container fill-height>
                  <div>
                    <div class="banner" style="font-size:20px">{{ subjects[i] }}</div>
                    <div class="banner" style="font-size:35px">{{ titles[i] }}</div>
                    <div class="guide" style="font-size: 15px">{{ contents[i] }}</div>
                    <v-btn class="guide" outlined plain @click="movePage(i)"
                      ><strong>바로가기</strong></v-btn
                    >
                  </div>
                </v-container>
              </v-img>
            </div>
          </v-row>
        </v-sheet>
      </v-carousel-item>
    </v-carousel>
  </div>
</template>

<script>
export default {
  data() {
    return {
      colors: ['#FFE183', '#FFA4A8', '#909FFF'],
      subjects: ['소개', '공지', '단계보기'],
      titles: ['코딱지', '새로운 공지사항 안내', '단계별 문제 보러가기'],
      contents: [
        '코딱지는 무엇을 하는 곳 일까요?',
        '코딱지 사용안내! 새로운 공지사항이 업데이트 되었어요',
        '코딱지에서 제공하는 여러가지 문제 함께 풀러 가볼까요?',
      ],
      items: [
        {
          src: require('@/assets/banner1.png'),
        },
        {
          src: require('@/assets/banner2.png'),
        },
        {
          src: require('@/assets/banner3.png'),
        },
      ],
    };
  },
  methods: {
    movePage(index) {
      if (index == 0) {
        this.$router.push({ name: 'Introduce' }).catch((error) => {
          if (error.name === 'NavigationDuplicated') {
            location.reload();
          }
        });
      } else if (index == 1) {
        this.$router.push({ name: 'Notice' }).catch((error) => {
          if (error.name === 'NavigationDuplicated') {
            location.reload();
          }
        });
      } else {
        this.$router.push({ name: 'AllProblemRank' }).catch((error) => {
          if (error.name === 'NavigationDuplicated') {
            location.reload();
          }
        });
      }
    },
  },
};
</script>

<style></style>
