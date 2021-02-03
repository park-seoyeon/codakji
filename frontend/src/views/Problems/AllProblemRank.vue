<template>
  <div>
    <v-sheet color="#909FFF" height="200" dark align="center">
      <v-row class="fill-height" align="center" justify="center">
        <div class="display-3">
          <v-img src="@/assets/img/rank_banner.png">
            <v-container fill-height fluid>
              <div style="margin-left: 430px">
                <div class="banner text-center" style="font-size:40px">단계 선택</div>
                <div class="guide text-center" style="font-size:15px">
                  코딱지에서 제공하는 문제들을 단계별로 풀어보아요
                </div>
              </div>
            </v-container>
          </v-img>
        </div>
      </v-row>
    </v-sheet>
    <v-container>
      <v-row>
        <RankCard v-for="(rank, idx) in ranks" :key="idx" :rank="rank" />
      </v-row>
    </v-container>
  </div>
</template>

<script>
import RankCard from '@/components/problem/RankCard';
export default {
  name: 'AllProblemRank',
  data: () => {
    return {
      ranks: [
        { id: 1, title: '1단계', color: 'grey lighten-5' },
        { id: 2, title: '2단계', color: 'red lighten-1' },
        { id: 3, title: '3단계', color: 'orange lighten-2' },
        { id: 4, title: '4단계', color: 'amber lighten-2' },
        { id: 5, title: '5단계', color: 'light-green lighten-1' },
        { id: 6, title: '6단계', color: 'blue accent-1' },
        { id: 7, title: '7단계', color: 'indigo lighten-2' },
        { id: 8, title: '8단계', color: 'deep-purple lighten-2' },
        { id: 9, title: '9단계', color: 'grey darken-3' },
      ],
    };
  },

  components: {
    RankCard,
  },
};
</script>

<style></style>
