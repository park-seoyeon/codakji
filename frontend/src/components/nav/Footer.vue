<template>
  <v-footer app absolute dark padless>
    <v-card flat tile class="flex grey lighten-5 white--text text-center">
      <v-card-text>
        <v-btn v-for="icon in icons" :key="icon" class="mx-4 grey--text" icon>
          <v-icon size="24px">
            {{ icon }}
          </v-icon>
        </v-btn>
      </v-card-text>

      <v-card-text class="grey--text py-0">
        <div>&copy; 코딱지 by All rights reserved.</div>
        <span @click="moveIntroduce" style="cursor: pointer; padding-right: 6px;">About us</span> |
        <span class="px-2">codackjiworld@gmail.com</span>
      </v-card-text>

      <v-divider></v-divider>

      <v-card-text class="grey--text">
        {{ new Date().getFullYear() }} — <strong>Codackji</strong>
      </v-card-text>
    </v-card>
  </v-footer>
</template>

<script>
export default {
  name: 'Footer',
  data: () => ({
    btnShow: false,
    icons: ['mdi-facebook', 'mdi-twitter', 'mdi-linkedin', 'mdi-instagram'],
  }),
  methods: {
    moveIntroduce() {
      this.$router.push({ name: 'Introduce' }).catch((error) => {
        if (error.name === 'NavigationDuplicated') {
          location.reload();
        }
      });
    },
  },
};
</script>

<style></style>
