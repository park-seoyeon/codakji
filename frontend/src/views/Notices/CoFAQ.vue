<template>
  <div>
    <v-sheet height="300" align="center">
      <v-row class="fill-height" align="center" justify="center">
        <div class="display-3">
          <v-img src="@/assets/img/banner/FAQ_banner.png" height="300">
            <v-container fill-height fluid>
              <v-row class="fill-height" align="center" justify="center">
                <v-col>
                  <div class="banner text-center" style="font-size:40px;">
                    자주 묻는 질문
                  </div>
                  <p class="guide text-center" style="font-size:20px; line-height: 25px;">
                    코딱지를 이용하면서 생기는 궁금증에 대한 답변들을 확인하세요
                  </p>
                </v-col>
              </v-row>
            </v-container>
          </v-img>
        </div>
      </v-row>
    </v-sheet>

    <v-container class="my-5" style="max-width:1024px;">
      <v-col>
        <v-row justify="center">
          <v-expansion-panels accordion>
            <v-expansion-panel v-for="(item, i) in faqData" :key="i">
              <v-expansion-panel-header
                expand-icon="mdi-menu-down"
                class="guide"
                style="font-size:18px;"
              >
                <strong> {{ item.number }}. {{ item.title }} </strong>
              </v-expansion-panel-header>
              <v-expansion-panel-content
                class="guide"
                style="white-space: pre-line; text-align: left; font-size:16px;"
              >
                {{ item.content }}
              </v-expansion-panel-content>
            </v-expansion-panel>
          </v-expansion-panels>
        </v-row>
      </v-col>
    </v-container>
  </div>
</template>

<script>
export default {
  name: 'CoFAQ',
  data: () => {
    return {
      faqData: [
        {
          number: 1,
          title: '이메일을 변경하고 싶어요. 어떻게 해야하나요?',
          content:
            '로그인 후 화면 상단에 이름 버튼을 클릭 후 수정하기 > 이메일 수정 을 통해 이메일을 변경하실 수 있습니다.',
        },
        {
          number: 2,
          title: '코딱지를 사용하려면 비용을 지불해야하나요?',
          content:
            '아닙니다. 코딱지는 누구나 코딩을 배울 수 있도록 비영리 목적으로 만들어진 사이트 입니다. 누구나 무료로 본인의 알고리즘 역량을 기를 수 있습니다.',
        },
        {
          number: 3,
          title: '친목을 하면 안되나요? 어떤 경우가 친목인가요?',
          content:
            '특정 사용자의 이름이나 이메일을 언급하며 일정 이상의 대화를 댓글등에서 지속할 시 친목으로 보고 있습니다. 과도한 친목은 사이트 이용에 좋지 않은 영향을 줄 수 있으므로 제재하고 있습니다.',
        },
        {
          number: 4,
          title: '캐시 삭제는 어떻게 하나요?',
          content:
            '먼저 사용하는 인터넷의 종류(익스플로러11 / 엣지 / 크롬 등)를 확인해주세요. 그 다음 인터넷 검색창에 인터넷 종류에 맞는 캐시 삭제 방법을 검색해서 캐시를 삭제해주세요.',
        },
        {
          number: 5,
          title: '제공되는 해설 언어는 무엇이 있나요?',
          content: '현재 정답으로 제공되는 코드는 python3, java의 정답 코드를 보여드리고 있습니다.',
        },
        {
          number: 6,
          title: '선생님 자격은 어떻게 주어지나요?',
          content: '선생님 자격은 관리자에게 신청 후 관리자의 판단으로 자격을 부여하고 있습니다.',
        },
        {
          number: 7,
          title: '화상하기는 어떤 경우에 이용이 가능한가요?',
          content:
            '본인이 푼 문제에 의문이 생겼을 경우 선생님과의 화상 채팅을 통해 의문을 해결할 수 있고 학생들 서로의 소통을 통해 실력을 기를 수 있습니다.',
        },
        {
          number: 8,
          title: '댓글은 어떠한 경우에 재제를 받나요?',
          content:
            '타인을 비방하거나 악의적 목적으로 올린 댓글, 음란성 댓글 및 욕설이 포함된 댓글은 무통보 삭제 될 수 있습니다.',
        },
        {
          number: 9,
          title: '문제 제출 횟수에 제한이 있나요?',
          content:
            '문제 제출 횟수에 현재 제한을 두고 있지 않습니다. 그러나 너무 짧은 시간에 과도한 제출하기를 하게 될 경우 사이트에 무리를 줄 수 있어 제한하고 있습니다.',
        },
        {
          number: 10,
          title: '문제는 정답인데 해답코드와 코드가 다릅니다. 잘못 푼 걸까요?',
          content:
            '알고리즘, 코딩은 정해진 정답이 없습니다. 해답코드는 단지 이해를 돕기 위한 예시일 뿐 절대적인 정답이 아닙니다.',
        },
        {
          number: 11,
          title:
            '제 코드에 어떤 부분이 문제인지 알려주지 않는데 틀린 정답이라고 합니다. 무엇이 문제일까요?',
          content:
            '이 경우 대부분 문법적 오류가 아닌 접근방식이 잘못된 논리적 오류일 가능성이 높습니다. 더 좋은 풀이 방법을 찾아 보는 것을 추천드립니다.',
        },
      ],
    };
  },
};
</script>

<style></style>
