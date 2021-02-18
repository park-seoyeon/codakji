<template>
  <codemirror
    ref="cmEditor"
    :value="code"
    :options="cmOptions"
    @input="onCmCodeChange"
    style="text-align: left;"
  />
</template>

<script>
import { codemirror } from 'vue-codemirror';

import 'codemirror/lib/codemirror.css';
import 'codemirror/mode/python/python.js';
import 'codemirror/theme/base16-dark.css';
import 'codemirror/addon/hint/show-hint.css';
import 'codemirror/addon/hint/show-hint.js';
import 'codemirror/addon/hint/anyword-hint';
import 'codemirror/addon/search/searchcursor';
import 'codemirror/addon/search/search';
import 'codemirror/addon/edit/closebrackets.js';

export default {
  name: 'Ide',
  components: {
    codemirror,
  },
  data: () => {
    return {
      code: '',
      cmOptions: {
        autoCloseBrackets: true,
        tabSize: 4,
        mode: 'text/x-python',
        theme: 'base16-dark',
        lineNumbers: true,
        line: true,
        lineWrapping: true,
        extraKeys: {
          'Ctrl-Space': 'autocomplete',
        },
      },
    };
  },
  methods: {
    onCmCodeChange(newCode) {
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
};
</script>

<style scoped></style>
