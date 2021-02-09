<template>
  <div>
    <h1>여기는 FAQ 배너가 들어갈 공간입니다.</h1>

    <v-container>
      <v-col>
        <v-row>
          <v-expansion-panels accordion focusable>
            <v-expansion-panel
              v-for="(item,i) in faqData"
              :key="i"
            >
              <v-expansion-panel-header>{{ item.number }}. {{ item.title }}</v-expansion-panel-header>
              <v-expansion-panel-content style="white-space: pre-line; text-align: left;">
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
        { number: 1, title: '왜 코딱지인가요?', content: '코딩은 딱 지금부터!\n지금부터 코딩입니다'},
        { number: 2, title: '질문을 적어주세요', content: '코딩은 딱 지금부터!'},
        { number: 3, title: '왜 하드코딩 했나요?', content: '코딩은 딱 지금부터!'},
        { number: 4, title: '왜 2주도 안 남았나요?', content: '코딩은 딱 지금부터!'},
        { number: 5, title: '여긴 어디인가요?', content: '코딩은 딱 지금부터!'},
      ]
    }
  },
}
</script>

<style>

</style>