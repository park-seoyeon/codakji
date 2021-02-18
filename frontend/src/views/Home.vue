<template>
  <v-row justify="center" align="center">
    <v-col class="pt-0">
      <div class="home">
        <MainBanner/>
        <v-container>
          <v-layout column class="mb-5">
            <ChooseRank />
            <HitRateProblems />
            <PopularProblems />
          </v-layout>
        </v-container>
      </div>
    </v-col>
  </v-row>
</template>

<script>
import MainBanner from '@/components/main/MainBanner.vue';
import ChooseRank from '@/components/main/ChooseRank';
import PopularProblems from '@/components/problemList/PopularProblems';
import HitRateProblems from '@/components/problemList/HitRateProblems';

export default {
  name: 'Home',
  data: () => ({
    code: '',
  }),
  components: {
    MainBanner,
    ChooseRank,
    PopularProblems,
    HitRateProblems,
  },
};
</script>

<style scoped>

</style>
