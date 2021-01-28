<template>
  <v-footer dark padless>
      <v-card class="flex flex-grow-1" flat tile>
        <v-card-title class="yellow darken-2 py-2">
          <span class="black--text">저작권은 ⓒ코딱지에게 있으며
            무단 전재, 복사를 통한 상업적 이용을 금합니다.
          </span>
          <v-fab-transition>
            <v-btn right fixed black small
                  v-show="btnShow" @click="$vuetify.goTo('#header')">
              <v-icon>mdi-chevron-double-up</v-icon>
            </v-btn>
          </v-fab-transition>
        </v-card-title>
  
        <v-card-text class="py-0 white--text text-center">
          {{ new Date().getFullYear() }} — <strong>Vuetify</strong>
        </v-card-text>
      </v-card>
    </v-footer>
</template>

<script>
export default {
  name: "Footer",
  data: () => {
    return {
      btnShow: true
    }
  },  
  method: {
    // handleScroll() {
    //   this.btnShow = window.scrollY > 400;
    // },
    // beforMound() {
    //   window.addEventListener("scroll", this.handleScroll);
    // },
    // beforeDestroy() {
    //   window.removeEventListener("Scroll", this.handleScroll);
    // }
  }
}
</script>

<style>

</style>