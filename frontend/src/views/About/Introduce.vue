<template>
  <div>
    <v-sheet height="300" align="center">
      <v-row class="fill-height" align="center" justify="center">
        <div class="display-3">
          <v-img src="@/assets/img/banner/intro_banner.png" height="300">
            <v-container fill-height fluid>
              <v-row class="fill-height" align="center" justify="center">
                <v-col>
                  <div class="banner text-center" style="font-size:40px;">
                    코딱지는?
                  </div>
                  <p class="guide text-center" style="font-size:15px; line-height: 25px;">
                    코딱지는 모두가 알고리즘 실력을 기를 수 있는 곳 입니다.<br />
                    학생들은 친구들과 소통하며 알고리즘 능력과 코딩 실력을 기르고,<br />
                    선생님은 학생들의 좋은 멘토가 되어주세요!
                  </p>
                </v-col>
              </v-row>
            </v-container>
          </v-img>
        </div>
      </v-row>
    </v-sheet>
    <v-container style="background-color: #e6e6e6;">
      <v-flex style="margin-top: 20px; margin-bottom: 20px; background-color: #e6e6e6;">
        <v-sheet align="left" width="1024" style="background-color: #e6e6e6;">
          <div><img src="@/assets/img/intro_logo.png" height="80" class="mx-5 mb-5" /></div>
        </v-sheet>
        <div align="center" class="mt-3">
          <v-sheet
            align="left"
            width="1010"
            class="py-3"
            style="border-radius: 40px 80px / 80px 40px;"
          >
            <div style=" display: inline-block;" class="ml-4">
              <img src="@/assets/img/co.png" width="50" height="50" />
            </div>
            <div
              style="font-size: 30px; display: inline-block; position:absolute;"
              class="ml-3 pt-2"
              max-height="50"
            >
              학습 하기<span style="font-size: 17px;" class="guide ml-2"
                ><strong>다양한 문제로 코딩을 재미있게</strong></span
              >
            </div>
            <div class="guide ml-15 pt-2" width="600px">
              <p style="font-size: 17px;" width="100%">
                &lt;학습하기&gt;에서는 다양한 코딩 문제를 풀 수 있습니다.<br />
                학년별로 자신의 수준에 맞는 문제를 풀며 코딩 실력을 길러보세요.<br />
                "코기"가 여러분의 실력 향상을 도와줄거에요!
              </p>
              <div>
                <v-chip outlined color="amber darken-1" @click="moveAllRank()"
                  >학습 하기<v-icon right>
                    mdi-arrow-right-bold
                  </v-icon></v-chip
                >
              </div>
            </div>
          </v-sheet>
          <v-sheet
            align="right"
            width="1010"
            class="py-3 mt-10"
            style="border-radius: 40px 80px / 80px 40px;position:relative;"
          >
            <div style=" display: inline-block; " class="mr-4">
              <img src="@/assets/img/dack.png" width="50" height="50" />
            </div>
            <div
              style="font-size: 30px; display: inline-block; position:absolute; left:45%"
              class="ml-3 pt-2"
              max-height="50"
            >
              <span style="font-size: 17px;" class="guide"
                ><strong>화상을 통해 서로의 코드를 공유하자</strong></span
              >
              화상으로 함께 하기
            </div>
            <div class="guide mr-15 pt-2" width="600px">
              <p style="font-size: 17px;" width="100%">
                &lt;화상으로 함께하기&gt;에서는 화상 미팅으로 대화를 나누며 서로의 코드를 공유할 수
                있습니다.<br />
                친구들과 서로 코드에 대해 공유하고 나누며 실력릏 쌓아보아요.<br />
                어려움이 생기면 선생님이 여러분을 도와줄거에요!
              </p>
              <div>
                <v-chip outlined color="indigo accent-2"
                  >화상으로 함께 하기<v-icon right>
                    mdi-arrow-right-bold
                  </v-icon></v-chip
                >
              </div>
            </div>
          </v-sheet>
          <v-sheet
            align="left"
            width="1010"
            class="py-3 mt-10"
            style="border-radius: 40px 80px / 80px 40px;"
          >
            <div style=" display: inline-block;" class="ml-4">
              <img src="@/assets/img/ji.png" width="50" height="50" />
            </div>
            <div
              style="font-size: 30px; display: inline-block; position:absolute;"
              class="ml-3 pt-2"
              max-height="50"
            >
              공지사항<span style="font-size: 17px;" class="guide ml-2"
                ><strong>코딱지의 여러 알림을 한눈에</strong></span
              >
            </div>
            <div class="guide ml-15 pt-2" width="600px">
              <p style="font-size: 17px;" width="100%">
                &lt;공지사항&gt;에서는 코딱지에서 알려주는 여러 공지사항을 볼 수 있습니다.<br />
                코딱지의 최신 소식을 알 수 있어요.<br />
                FAQ에서는 자주 묻는 질문에 대한 답이 있으니 참고해주세요!
              </p>
              <div>
                <v-chip outlined color="red accent-2" @click="moveNotice()"
                  >공지사항<v-icon right>
                    mdi-arrow-right-bold
                  </v-icon></v-chip
                >
              </div>
            </div>
          </v-sheet>
        </div>
      </v-flex>
    </v-container>
    <v-container class="mt-3">
      <hr />
      <v-flex style="margin-top: 20px; margin-bottom: 20px;" align="center">
        <v-sheet class="mt-3" align="center" width="1116">
          <div style="font-size:40px">개발자 소개</div>
          <div class="mb-5 guide" style="font-size:17px">코딱지의 개발자를 소개합니다</div>
          <div
            style="display: inline-block; border-radius: 70%; width: 140px; height: 140px; overflow: hidden; border: solid 2px #F7BC44;"
            class="ml-10"
          >
            <img
              src="@/assets/img/person/seoyeon.png"
              style="width: 100%; height: 100%; object-fit: cover;"
            />
          </div>
          <div
            style="display: inline-block; border-radius: 70%; width: 140px; height: 140px; overflow: hidden; border: solid 2px #7384FF;"
            class="ml-10"
          >
            <img
              src="@/assets/img/person/rakyoung.png"
              style="width: 100%; height: 100%; object-fit: cover;"
            />
          </div>
          <div
            style="display: inline-block; border-radius: 70%; width: 140px; height: 140px; overflow: hidden; border: solid 2px #EC5B5B;"
            class="ml-10"
          >
            <img
              src="@/assets/img/person/juhee.png"
              style="width: 100%; height: 100%; object-fit: cover;"
            />
          </div>
          <div
            style="display: inline-block; border-radius: 70%; width: 140px; height: 140px; overflow: hidden; border: solid 2px #7EB649;"
            class="ml-10"
          >
            <img
              src="@/assets/img/person/junhee.png"
              style="width: 100%; height: 100%; object-fit: cover;"
            />
          </div>
          <div
            style="display: inline-block; border-radius: 70%; width: 140px; height: 140px; overflow: hidden; border: solid 2px #7384FF;"
            class="ml-10"
          >
            <img
              src="@/assets/img/person/hyeonaa.png"
              style="width: 100%; height: 100%; object-fit: cover;"
            />
          </div>
          <div
            style="display: inline-block; border-radius: 70%; width: 140px; height: 140px; overflow: hidden; border: solid 2px #F7BC44;"
            class="ml-10"
          >
            <img
              src="@/assets/img/person/cogi_profile.png"
              style="width: 100%; height: 100%; object-fit: cover;"
            />
          </div>
          <div>
            <div style="display: inline-block; font-size: 20px;" width="50" class="mr-13 ml-7">
              SeoYeon
            </div>
            <div style="display: inline-block; font-size: 20px;" width="50" class="mr-15 ml-14">
              RakYoung
            </div>
            <div style="display: inline-block; font-size: 20px;" width="50" class="mr-15 ml-12">
              JooHee
            </div>
            <div style="display: inline-block; font-size: 20px;" width="50" class="mr-15 ml-15">
              JunHee
            </div>
            <div style="display: inline-block; font-size: 20px;" width="50" class="mr-15 ml-15">
              HyeonA
            </div>
            <div style="display: inline-block; font-size: 20px;" width="50" class="ml-15 pl-3">
              Cogi
            </div>
          </div>
        </v-sheet>
      </v-flex>
    </v-container>
  </div>
</template>

<script>
export default {
  methods: {
    moveAllRank() {
      this.drawer = false;
      this.$router.push({ name: 'AllProblemRank' }).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },

    moveNotice() {
      this.$router.push({ name: 'Notice' }).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
  },
};
</script>

<style></style>
