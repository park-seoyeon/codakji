<template>
  <div>
    <!-- Two-way Data-Binding -->
    <!-- <codemirror v-model="code" :options="cmOptions" /> -->

    <!-- Or manually control the data synchronization -->
    <codemirror
      ref="cmEditor"
      :value="code"
      :options="cmOptions"
      @ready="onCmReady"
      @focus="onCmFocus"
      @input="onCmCodeChange"
    />
  </div>
</template>

<script>
import { codemirror } from 'vue-codemirror';

// import base style
import 'codemirror/lib/codemirror.css';

// import language js
import 'codemirror/mode/python/python.js';

// import theme style
import 'codemirror/theme/base16-dark.css';

// import more 'codemirror/some-resource...'
import 'codemirror/addon/hint/show-hint.css';
import 'codemirror/addon/hint/show-hint.js';
import 'codemirror/addon/hint/anyword-hint';
import 'codemirror/addon/search/searchcursor';
import 'codemirror/addon/search/search';
import 'codemirror/addon/edit/closebrackets.js'; //괄호 짝 맞추기

export default {
  name: 'Ide',
  components: {
    codemirror,
  },
  data: () => {
    return {
      code: '',
      cmOptions: {
        autoCloseBrackets: true, //자동 괄호 닫기
        tabSize: 4,
        mode: 'text/x-python',
        theme: 'base16-dark',
        lineNumbers: true,
        line: true,
        lineWrapping: true, //자동 줄바꿈
        extraKeys: {
          'Ctrl-Space': 'autocomplete',
        },
        // more CodeMirror options...
      },
    };
  },
  methods: {
    onCmReady(cm) {
      console.log('the editor is readied!', cm);
    },
    onCmFocus(cm) {
      console.log('the editor is focused!', cm);
    },
    onCmCodeChange(newCode) {
      //console.log('this is new code', newCode);

      this.childMessage = newCode;
      this.$emit('getCode', this.childMessage);

      this.code = newCode;
    },
  },
  computed: {
    codemirror() {
      return this.$refs.cmEditor.codemirror;
    },
  },
  mounted() {
    console.log('the current CodeMirror instance object:', this.codemirror);
    // you can use this.codemirror to do something...
  },
};
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped></style>
