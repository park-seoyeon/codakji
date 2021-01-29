<template>
  <v-container>
    <v-row>
      <v-col cols="6">
        <div align="left">
          <div>
            <h1>{{ problemTitle }}</h1>
            <p>{{ problemGroup }}</p>
          </div>
          <div>
            <p>{{ problemContent }}</p>
          </div>
          <div>
            <h3>문제 입력</h3>
            <p>{{ problemInput }}</p>
          </div>
          <div>
            <h3>문제 출력</h3>
            <p>{{ problemOutput }}</p>
          </div>
        </div>
        <div align="right">
          <div v-show="description" align="right">
            해설보러가기!
          </div>
          <v-btn plain>
            <v-img
              height="80px"
              width="80px"
              src="@/assets/sleep_cogi.png"
              @mouseover="mouseOver"
              @mouseleave="mouseLeave"
            />
          </v-btn>
        </div>
      </v-col>
      <v-col cols="6">
        <iframe
          width="100%"
          height="500"
          src="https://jsfiddle.net/chrisvfritz/0dzvcf4d/embedded/result,html,js,css"
          allowfullscreen="allowfullscreen"
          frameborder="0"
        ></iframe>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data: () => {
    return {
      problemTitle: 'We Love Kriii',
      problemContent:
        'ACM-ICPC 인터넷 예선, Regional, 그리고 World Finals까지 이미 2회씩 진출해버린 kriii는 미련을 버리지 못하고 왠지 모르게 올해에도 파주 World Finals 준비 캠프에 참여했다.대회를 뜰 줄 모르는 지박령 kriii를 위해서 격려의 문구를 출력해주자.',
      problemInput: '본 문제는 입력이 없다.',
      problemOutput: '두 줄에 걸쳐 "강한친구 대한육군"을 한 줄에 한 번씩 출력한다.',
      problemGroup: '입출력과 사칙연산',
      problemRank: '1',
      description: false,
    };
  },
  methods: {
    mouseOver: function() {
      this.description = !this.description;
    },
    mouseLeave: function() {
      this.description = !this.description;
    },
  },
};
</script>

<style></style>
