<template>
  <v-row justify="center" align="center">
    <v-col class="pt-0">
      <MainPage />
      <!-- <div class="home">
      </div> -->
    </v-col>
  </v-row>
</template>

<script>
// @ is an alias to /src
import MainPage from '@/components/main/MainPage.vue';


export default {
  name: 'Home',
  data: () => ({
    code: '',
  }),
  components: {
    MainPage,
  },
};
</script>
