<template>
  <v-container>
    <v-row>
      <v-col cols="6">
        <div>
          <h1>{{ problemTitle }}</h1>
          <p>{{ problemGroup }}</p>
        </div>
        <div>
          <p>{{ problemContent }}</p>
        </div>
        <div>
          <h3>문제 입력</h3>
          <p>{{ problemInput }}</p>
        </div>
        <div>
          <h3>문제 출력</h3>
          <p>{{ problemOutput }}</p>
        </div>
      </v-col>
      <v-col cols="6">
        <iframe
          src="https://jsfiddle.net/chrisvfritz/0dzvcf4d/embedded/result,html,js,css"
          allowfullscreen="allowfullscreen"
          frameborder="0"
        ></iframe>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
export default {
  data: () => {
    return {
      problemTitle: '',
      problemContent: '',
      problemInput: '',
      problemGroup: '',
      problemRank: '',
    };
  },
  methods: {},
};
</script>

<style></style>
