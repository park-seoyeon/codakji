<template>
  <v-footer dark padless app>
    <v-card flat tile class="flex grey lighten-5 white--text text-center">
      <v-card-text>
        <v-btn v-for="icon in icons" :key="icon" class="mx-4 grey--text" icon>
          <v-icon size="24px">
            {{ icon }}
          </v-icon>
        </v-btn>
        <v-fab-transition>
          <v-btn
            right
            fixed
            black
            small
            fab
            v-show="btnShow"
            @click="$vuetify.goTo('#header')"
          >
            <v-icon>mdi-chevron-double-up</v-icon>
          </v-btn>
        </v-fab-transition>
      </v-card-text>

      <v-card-text class="grey--text pt-0">
        &copy; 코딱지 by All rights reserved.
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
    btnShow: true,
    icons: ['mdi-facebook', 'mdi-twitter', 'mdi-linkedin', 'mdi-instagram'],
  }),
  methods: {
    // handleScroll() {
    //   this.btnShow = window.scrollY > 400;
    // },
    // beforMound() {
    //   window.addEventListener("scroll", this.handleScroll);
    // },
    // beforeDestroy() {
    //   window.removeEventListener("Scroll", this.handleScroll);
    // }
  },
};
</script>

<style></style>
