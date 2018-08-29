<template>
  <div id="app">
    <template v-if="logined">
    <el-container> 
       <el-header style="padding:0">
          <div id='header' />
          <el-row>
              <el-col :span="4" class="el-menu-demo headerLogo">
                <span class="title"><a href="#/home" class="noStyleLink">蒲公英教育康复<br />综合支持系统</a></span>
              </el-col>
              <el-col :span="18">
                <el-menu id='el-menu-id' :default-active='$route.path' class="el-menu-demo headerStyle" mode="horizontal" @select="" text-color="#555" active-text-color="#ffd04b" router>
                  <el-menu-item index="/coursewareList1" @click="open('/coursewareList1')">互动课件</el-menu-item>
                  <el-menu-item index="/coursewareList2" @click="open('/coursewareList2')">经典教案</el-menu-item>
                  <el-menu-item index="/coursewareList3" @click="open('/coursewareList3')">习题</el-menu-item>
                  <template v-if="isTeacher">
                    <el-menu-item index="/coursewareList4" @click="open('/coursewareList4')">我的共享</el-menu-item>
                  </template>
                  <template v-if="isTeacher || isAdmin">
                    <el-menu-item index="/accountList" @click="open('/accountList')">账户管理</el-menu-item>
                  </template>
                  <template v-if="isSuperAdmin">
                    <el-menu-item index="/gradeList" @click="open('/gradeList')">年级管理</el-menu-item>
                  </template>
                  <template>
                    <el-menu-item index="/contact" @click="open('/contact')">联系我们</el-menu-item>
                  </template>
                </el-menu>
              </el-col>
              <el-col :span="2" class="headerLogo">
                <el-dropdown>
                  <div class="userInfo">
                    <span class="el-dropdown-link">{{userInfo.username}}</span>
                  </div>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item><div @click="handleLogout()">退出</div></el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </el-col> 
          </el-row>
        </el-header>

      <el-container height="100%">
          <el-aside class="headerASide" width="16.67%" v-if='showGrade'>
            <gradeSide></gradeSide>
          </el-aside>
        <el-container>
            <el-main>
              <keep-alive>
                 <router-view v-if='isRouterAlive'></router-view>
            </keep-alive>
            </el-main>
            <el-footer >CopyRight@2018 Primitives</el-footer>
        </el-container>
      </el-container>
    </el-container>
  </template>
  <template v-else>
      <login @userSignIn="userSignIn"/>
      <homepage :show-grade="showGrade" />
  </template>
  </div>
</template>

<script>
import gradeSide from '@/components/gradeSide'
import manageSide from '@/components/manageSide'
import homepage from '@/page/homepage'
import login from '@/page/login'
import {logout} from '@/api/getData'
import {getCookie, setCookie, delCookie} from '@/assets/cookie'

export default {
    name: 'App',
    data() {
      return {
        showGrade: true,
        logined: false,
        isAdmin: false,
        isSuperAdmin: false,
        isTeacher: false,
        userInfo: '',
        isRouterAlive: true
      }
    },
    provide() {
      return {
        reloadAllPages: this.reloadAllPages
      }
    },
    components: {
      gradeSide,
      manageSide,
      login,
      homepage
    },
    mounted(){
      var self = this;
      this.$root.Bus.$on('logout', function(val) {
          self.handleLogout();
      });
      this.updateLoginInfo();
    },
    methods: {
      reloadAllPages() {
        console.log("reloadAllPages");
        this.showGrade = false; 
        this.isRouterAlive = false;
        this.$nextTick(function(){
          this.showGrade = true;
          this.isRouterAlive = true;
        })
      },
      updateLoginInfo() {
        if (getCookie('userInfo')) {
          let userInfo = JSON.parse(getCookie('userInfo'))
          this.logined = true;
          this.userInfo = userInfo;
          this.isAdmin = userInfo.role === 'SUPERADMIN' || userInfo.role === 'ADMIN';
          this.isSuperAdmin = userInfo.role === 'SUPERADMIN';
          this.isTeacher = userInfo.role === 'TEACHER';
        }
      },
      open(url) {
        this.$router.push(url);
        //this.$root.Bus.$emit('refresh', '');
      },
      userSignIn(userInfo) {
        setCookie('userInfo',JSON.stringify(userInfo), 1000*60);
        this.updateLoginInfo();
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
        this.$router.push('/homepage');
      }
    }
}
</script>

<style>
  #app {
    font-family: 'Avenir', Helvetica, Arial, sans-serif;
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    height: 100%;
  }

  .el-button--primary, .newBtnStyle {
      background: #ff982a !important;
      border-color: #ff982a !important;
  }

  .el-button--primary:hover {
      background: #ffd04b !important;
      border-color: #ffd04b !important;
  }

  .el-button--primary:focus {
      background: #ffd04b !important;
      border-color: #ffd04b !important;
  }

  .el-radio-button__orig-radio:hover+.el-radio-button__inner {
      color: #FFF !important;
      background: #ffd04b !important;
      border-color: #ffd04b !important;
  }
  .el-radio-button__orig-radio:checked+.el-radio-button__inner {
      background: #ff982a !important;
      border-color: #ff982a !important;
      -webkit-box-shadow: -1px 0 0 0 !important;
  }

  html body {
    margin: 0;
    /*height: 100%;
    padding: 0;
    
    background-image: url('assets/bg.png');
    background-position:center;
    background-repeat:no-repeat;
    background-size: 100% 100%;*/
  }

  .el-textarea__inner {
    height: 100px;
  }

  .headerLogo {
    background-color: #15b699;
    text-align: center;
    height: 60px;
  }

  .headerLogo .title {
    float: left;
    margin-top: 10px;
    margin-left: 40px;
    color: #fff;
  }

  .headerASide {
    height: 600px;
    border-right: 1px solid #dddddd;
  }

  .headerStyle, .el-menu, #el-menu-id {
    background-color: #15b699;
  }

  .userInfo {
    color: #fff;
    padding-top: 20px;
  }

  .noStyleLink {
    text-decoration:none;
    color:#333;  
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
