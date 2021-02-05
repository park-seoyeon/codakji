<template>
  <v-row justify="center" align="center">
    <v-col>
      <div class="home">
        <MainPage />
      </div>
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
