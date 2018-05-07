<template>
  <div id="app">
    <el-container>
      <el-header style="padding:0">
        <div id='header' />
          <el-row>
              <el-col :span="4"  style="{text-align:center}"><span>首页</span></el-col>
              <el-col :span="18">
                <el-menu default-active="1" class="el-menu-demo" mode="horizontal" @select=""
                background-color="#545c64" text-color="#fff" active-text-color="#ffd04b">
                  <el-menu-item index="1"><a href="/#/coursewareList?type=1">互动课件</a></el-menu-item>
                  <el-menu-item index="2"><a href="/#/coursewareList?type=2">经典教案</a></el-menu-item>
                  <el-menu-item index="3"><a href="/#/coursewareList?type=3">习题</a></el-menu-item>
                  <el-menu-item index="4"><a href="/#/categoryList">我的共享</a></el-menu-item>
                  <el-menu-item index="5"><a href="/#/accountList">账户管理</a></el-menu-item>
                  <el-menu-item index="6"><a href="/#/gradeList">年级管理</a></el-menu-item>
                </el-menu>
              </el-col>
              <el-col :span="2">
                <el-dropdown style="text-align: right; font-size: 12px">
                <span>{{username}}</span> 
                <el-dropdown-menu slot="dropdown">
                  <el-dropdown-item>个人中心</el-dropdown-item>
                    <el-dropdown-item>退出</el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </el-col> 
          </el-row>
        </el-header>

      <el-container>
        <el-aside width="16.67%">
          <gradeSide v-if='grade'></gradeSide>
          <manageSide v-else></manageSide>
        </el-aside>
        <el-container>
            <el-main>
              <router-view/>
            </el-main>
            <el-footer>CopyRight@Felix</el-footer>
        </el-container>
      </el-container>
    </el-container>
  </div>
</template>

<script>
import gradeSide from '@/components/gradeSide'
import manageSide from '@/components/manageSide'

export default {
  name: 'App',
  data() {
    return {
      username: 'zhangsan',
      grade: true
    }
  },
  components: {
    gradeSide,
    manageSide
  }
}
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
  }

  body {
    margin: 0;
  }

  .el-textarea__inner {
    height: 100px;
  }

</style>
