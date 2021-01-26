<template>
  <v-footer dark padless>
      <v-card class="flex flex-grow-1" flat tile>
        <v-card-title class="amber lighten-4">
          <strong class="black--text">저작권은 ⓒ코딱지에게 있으며
            무단 전재, 복사를 통한 상업적 이용을 금합니다.
          </strong>
        </v-card-title>
  
        <v-card-text class="py-1 white--text text-center">
          {{ new Date().getFullYear() }} — <strong>Vuetify</strong>
        </v-card-text>
      </v-card>
    </v-footer>
</template>

<script>
export default {

}
</script>

<style>

</style>