<template>
  <div>
    <v-tabs v-model="tabs" centered>
      <!-- v-tabsdml v-model="tabs"로 선택된 탭의 번호가 tabs로 연결 -->
      <!-- <v-tab v-for="n in 3" :key=n> 과 같이 for문 가능 -->
      <v-tab>채점 결과</v-tab>
      <v-tab>코드 비교</v-tab>
      <v-tab>해설</v-tab>

      <v-tabs-items v-model="tabs" class="mt-15">
        <!-- 위의 선택된 탭의 번호 tabs를 통해서 밑의 v-tab-item이 순서대로 배정 -->
        <!-- '채점 결과'라는 탭의 아이템(표시목록) -->
        <v-tab-item>
          <v-container>
            <!-- 모바일 (가로비 작은 화면에서 위아래로 -->
            <v-row no-gutters justify="center">
              <v-col cols="6" md="4">
                <v-textarea
                label="Your Answer"
                no-resize
                rows="5"
                :value="yourAnswer"
                ></v-textarea>
              </v-col>

              <v-divider
                inset
                vertical
                class="mx-5 mt-0 mb-5"
              ></v-divider>

              <v-col sm="6" md="4">
                <v-textarea
                  label="Correct Answer"
                  no-resize
                  rows="5"
                  :value="correctAnswer"
                ></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-tab-item>

        <!-- '코드 비교'라는 탭의 아이템 -->
        <v-tab-item>
          <p>정답인 경우 예시 코드 선활성화</p>
          <p>오답인 경우 예시 코드 선비활성화</p>
          <p>그 대신 어디에서 틀렸을지 예측해서 알려주기</p>
        </v-tab-item>

        <!-- '해설'이라는 탭의 아이템 -->
        <v-tab-item>
          <p>여기에 해설을 위한 이미지를 카로셀로 보여주기 등</p>
        </v-tab-item>
      </v-tabs-items>
    </v-tabs>
  </div>
</template>

<script>
export default {
  name: 'SolveResult',
  data: () => {
    return {
      tabs: null,
      description: false,
      isCorrect: false,
      yourAnswer: '사용자의 정답이 나오는 부분',
      correctAnswer: '원래 정답이 나오는 부분',
      yourCode: '사용자의코드가적혀있는모습이다',
      correctCode: '올바른코드들이적혀있는모습이다',
    }
  },
  methods: {
    mouseOver: function() {
      this.description = !this.description;
    },
    mouseLeave: function() {
      this.description = !this.description;
    },
  }
}
</script>

<style>

</style>