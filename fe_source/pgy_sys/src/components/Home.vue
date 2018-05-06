<template>
	<div class="home">
		<span>欢迎来到蒲公英教育康复支持系统</span>
	</div>
</template>

<script>
import Vue from 'vue'
//import Header from './Header'
import EleUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'

Vue.use(EleUI)

export default{
  name: 'Home',
  data () {
    return {
      header: 'Welcome to Your Vue.js App',
      username: 'user',
      aside: '边边',
      main: 'Main',
      footer: '脚脚'
    }
  }
}
</script>

<style type="text/css">
  .el-header {
    background-color: #B3C0D1;
    color: #333;
    line-height: 60px;
  }
  
  .el-aside {
    color: #333;
  }
</style>
