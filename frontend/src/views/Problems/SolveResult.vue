<template>
  <div>
    <v-chip style="position: absolute; top: 0; left: 10px; z-index: 50;">
      뒤로 가는 버튼
    </v-chip>
    <v-tabs v-model="tabs" centered class="mt-15">
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
              <v-col cols="12" md="4">
                <v-textarea
                label="Your Answer"
                no-resize
                rows="5"
                :value="yourAnswer"
                readonly
                outlined
                ></v-textarea>
              </v-col>

              <v-col cols="0" md="1"></v-col>

              <v-col cols="12" md="4">
                <v-textarea
                  label="Correct Answer"
                  no-resize
                  rows="5"
                  :value="correctAnswer"
                  readonly
                  outlined
                ></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-tab-item>

        <!-- '코드 비교'라는 탭의 아이템 -->
        <v-tab-item>
          <v-container>
            <v-row no-gutters justify="center">
              <v-col cols="12" sm="8" md="4">
                <v-textarea
                  label="Your Answer"
                  no-resize
                  rows="20"
                  :value="yourCode"
                  readonly
                  outlined
                ></v-textarea>
              </v-col>
              <v-col cols="0" sm="1"></v-col>
              <v-col cols="12" sm="8" md="4">
                <v-textarea
                  label="Correct Answer"
                  no-resize
                  rows="20"
                  :value="correctCode"
                  readonly
                  outlined
                ></v-textarea>
              </v-col>
            </v-row>
          </v-container>
        </v-tab-item>

        <!-- '해설'이라는 탭의 아이템 -->
        <v-tab-item>
          <v-container>
            <v-row>
              <!-- 카로셀로 이미지 보여줄까 -->
              <v-carousel v-model="model">
                <v-carousel-item
                  v-for="(color, i) in colors"
                  :key="color"
                >
                  <v-sheet
                    :color="color"
                    height="100%"
                    tile
                  >
                    <v-row
                      class="fill-height"
                      align="center"
                      justify="center"
                    >
                      <div class="display-3">
                        결과이미지 {{ i + 1 }}
                      </div>
                    </v-row>
                  </v-sheet>
                </v-carousel-item>
              </v-carousel>
            </v-row>
          </v-container>
        </v-tab-item>
      </v-tabs-items>
    </v-tabs>
  </div>
</template>

<script>
import axios from 'axios';

const SERVER_URL = process.env.VUE_APP_SERVER_URL;

export default {
  name: 'SolveResult',
  data: () => {
    return {
      tabs: null,
      description: false,
      isCorrect: false,
      yourAnswer: '사용자의 정답이 나오는 부분\n개행문자로 줄을 바꾼다',
      correctAnswer: '원래 정답이 나오는 부분',
      yourCode: '사용자의 코드가 적히는 부분\n마찬가지로 개행문자로 줄이 바뀐다',
      correctCode: '올바른 코드들이 적히는 부분',
      imageNumber: 0,
      model: 0,
      colors: [
        'primary',
        'secondary',
        'yellow darken-2',
        'red',
        'orange',
      ],
    }
  },
  methods: {
    mouseOver: function() {
      this.description = !this.description;
    },
    mouseLeave: function() {
      this.description = !this.description;
    },
  },
  created() {
    const resultInfo = {
      token: localStorage.getItem('jwt'),
      solved_problem_number: this.$route.params.resultnumber,
    }

    axios
      .post(`${SERVER_URL}/codeAPI/result`, resultInfo)
      .then(response => {
        console.log(response);
        // answer : true
        // cpuTime: "0.02"
        // error: null
        // memory: "5308"
        // statusCode: "200"
        this.yourAnswer = response.data.my_output;
        this.correctAnswer = response.data.correct_output;
        this.yourCode = response.data.my_code;
        this.correctCode = response.data.correct_code;
        this.imageNumber = response.data.img_number;
      })
      .catch(error => {
        if (error.response.status === 401) {
          alert("세션이 만료되었습니다.");
          this.$emit("expireLogin");
        } else {
          console.log(error);
        }
      });
  }
}
</script>

<style>

</style>