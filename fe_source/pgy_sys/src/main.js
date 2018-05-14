// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import App from './App'
import router from './router'
import EleUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(EleUI)
Vue.config.productionTip = false

// use mock data, remove the // below. then go config/env.js.
require('./mock.js')

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
