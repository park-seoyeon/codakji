<template>
  <v-row no-gutters justify="center" align="center">
    <v-col cols="12" sm="4">
      <div class="home">
        <HelloWorld msg="Welcome to Your Vue.js App" />
      </div>
    </v-col>
  </v-row>
</template>

<script>
// @ is an alias to /src
import HelloWorld from '@/components/HelloWorld.vue';

export default {
  name: 'Home',
  components: {
    HelloWorld,
  },
};
</script>
