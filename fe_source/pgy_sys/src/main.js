// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import EleUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

import {checkLogin} from '@/api/getData'
import {delCookie} from '@/assets/cookie'

Vue.use(EleUI)
Vue.config.productionTip = false

Vue.prototype.checkLogin = function (){
  checkLogin({}).then(res => {
  	if (res.success !== true) {
      console.log('delete cookie.');
      this.$root.Bus.$emit('logout', {});
  	}
  });
}
// use mock data, remove the // below. then go config/env.js.
// require('./mock.js')

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  data: {
  	Bus: new Vue()
  },
  components: { App },
  template: '<App/>'
})
