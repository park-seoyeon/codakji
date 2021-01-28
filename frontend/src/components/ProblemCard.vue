<template>
  <v-container>
    <v-card width="300px">
      <v-card-title>{{ problem.id }}. {{ problem.title }}</v-card-title>
      <v-card-text>{{ problem.content }}</v-card-text>
    </v-card>
  </v-container>
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