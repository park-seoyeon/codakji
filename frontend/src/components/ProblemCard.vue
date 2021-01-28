<template>
  <!-- <v-container> -->
  <v-col cols="12" sm="6" md="3" lg="2">
    <v-card>
      <v-card-title>{{ problem.id }}. {{ problem.title }}</v-card-title>
      <v-card-text>{{ problem.content }}</v-card-text>
    </v-card>
  </v-col>
  <!-- </v-container> -->
</template>

<script>
export default {
  props: {
    problem: Object,
  }
}
</script>

<style>

</style>