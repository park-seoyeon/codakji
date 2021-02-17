import Vue from 'vue'
import App from './App.vue'
import vuetify from './plugins/vuetify';
import router from './router'

Vue.config.productionTip = false
window.Kakao.init('3190271abdfa86853f3bcbae839e30a2');

new Vue({
  vuetify,
  router,
  render: h => h(App)
}).$mount('#app')
