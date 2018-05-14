<template>
  <div id="app">
    <template v-if="logined">
    <el-container> 
       <el-header style="padding:0">
          <div id='header' />
          <el-row>
              <el-col :span="4" class="el-menu-demo headerLogo">
                <span class="title">蒲公英教育康复<br />综合支持系统</span>
              </el-col>
              <el-col :span="18">
                <el-menu default-active="1" class="el-menu-demo headerStyle" mode="horizontal" @select="" text-color="#000" active-text-color="#ffd04b">
                  <el-menu-item index="1" @click="open('/coursewareList?type=1')">互动课件</el-menu-item>
                  <el-menu-item index="2" @click="open('/coursewareList?type=2')">经典教案</el-menu-item>
                  <el-menu-item index="3" @click="open('/coursewareList?type=3')">习题</el-menu-item>
                  <el-menu-item index="4" @click="open('/coursewareList?type=4')">我的共享</el-menu-item>
                  <el-menu-item index="5" @click="open('/accountList')">账户管理</el-menu-item>
                  <el-menu-item index="6" @click="open('/gradeList')">年级管理</el-menu-item>
                </el-menu>
              </el-col>
              <el-col :span="2" class="headerLogo">
                <el-dropdown>
                  <div class="userInfo">
                    <span class="el-dropdown-link">{{userInfo.username}}</span>
                  </div>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>个人中心</el-dropdown-item>
                    <el-dropdown-item><div @click="handleLogout()">退出</div></el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </el-col> 
          </el-row>
        </el-header>

      <el-container height="100%">
          <el-aside width="16.67%">
            <gradeSide v-if='grade'></gradeSide>
            <manageSide v-else></manageSide>
          </el-aside>
        <el-container>
            <el-main>
              <keep-alive>
                 <router-view></router-view>
            </keep-alive>
            </el-main>
            <el-footer>CopyRight@Felix</el-footer>
        </el-container>
      </el-container>
    </el-container>
  </template>
  <template v-else>
      <login @userSignIn="userSignIn"/>
  </template>
  </div>
</template>

<script>
import gradeSide from '@/components/gradeSide'
import manageSide from '@/components/manageSide'
import login from '@/page/login'
import {logout} from '@/api/getData'
import {getCookie, setCookie, delCookie} from '@/assets/cookie'

export default {
    name: 'App',
    data() {
      return {
        grade: true,
        logined: false,
        userInfo: ''
      }
    },
    components: {
      gradeSide,
      manageSide,
      login
    },
    mounted(){
      if (getCookie('userInfo')) {
        let userInfo = JSON.parse(getCookie('userInfo'))
        this.logined = true;
        this.userInfo = userInfo;
      }
    },
    methods: {
      open(url) {
        this.$router.push(url);
      },
      userSignIn(userInfo) {
        this.logined = true;
        this.userInfo = userInfo;
        setCookie('userInfo',JSON.stringify(userInfo), 1000*60);
      },
      async handleLogout() {
        try {
          let res = await logout(this.userInfo);
          if (res.success === true) {
            console.log("退出成功")
          } else {
            console.log(res.message)
          }
        } catch(err) {
          this.$message({
              type: 'error',
              message: err.message
          });
          console.log('退出失败');
        }

        delCookie('userInfo');
        this.logined = false;
        this.$router.push('/home');
      }
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

  .headerLogo {
    background-color: #3C8DC4;
    text-align: center;
    height: 60px;
  }

  .headerLogo .title {
    float: left;
    margin-top: 10px;
    margin-left: 40px;
    color: #fff;
  }

  .headerStyle {
    background-color: #3C8DC4;
  }

  .userInfo {
    color: #fff;
    padding-top: 20px;
  }

  .el-footer {
    text-align: center;
    height: 10px;   /* footer的高度一定要是固定值*/ 
    position: relative;
    bottom: 0px;
    left: 0px;
    right: 0px;
  }

</style>
