<template>
    <div class="loginParent" :style="loginParent">
        <div>
            <div class="login-wrap" v-show="showLogin">
                <h3>登录</h3>
                <p v-show="showTip" class="alertStyle">{{tip}}</p>
                <div class="login-field">
                    <p>
                        <label class="login-label">账户</label>
                        <input class="login-input" type="text" placeholder="请输入用户名" v-model="username">
                    </p>
                    <p class="none">
                        <label class="login-label">密码</label>
                        <input class="login-input" type="password" placeholder="请输入密码" v-model="password">
                    </p>
                </div>
                <el-button @click="handleLogin" class="login-button">登录</el-button>
                <div>没有账号？<a href="mailto:maofg@qq.com">联系我们</a></div>
            </div>
        </div>
    </div>
</template>


<script>
	// ref: https://segmentfault.com/a/1190000009329619
	import {login} from '@/api/getData'
    import md5 from 'js-md5'

    export default{
        data(){
            return{
                showLogin: true,
                showRegister: false,
                showTip: false,
                tip: '',
                username: '',
                password: '',
                newUsername: '',
                newPassword: '',
                loginParent: {
                    backgroundImage: "url(" + require("../assets/img/login-bg.jpg") + ")"
                }
            }
        },
        methods:{
        	toRegister(){
			    console.log("to impl");
			},
			toLogin(){
			    this.showRegister = false;
			    this.showLogin = true;
			},
        	async handleLogin() {
		        if (this.username == "" || this.password == "") {
                    this.showTip = true;
		            this.tip = "请输入用户名或密码";
		        } else {
                    try {
	                    const res = await login({'username':this.username,'password':md5(this.password)});
	                    if (res.success === true) {
                            location.reload();
                            console.log('登录成功');
	                        this.$emit('userSignIn', res.result);
                            if (this.$route.query.redirect) {
    	                        window.location.href = this.$route.query.redirect;
                            }
	                    } else {
                            this.showTip = true;
                            // this.$router.push('/pgy/login.html');
                            this.tip = '用户名或密码错误，或账户已过期';
                            console.log(res.message);
                        }
	                } catch(err) {
	                    this.$message({
	                        type: 'error',
	                        message: err.message
	                    });
	                    console.log('登录失败');
	                }
		        }
		    }
		}
    }
</script>


<style scoped>
    .loginParent {
        padding: 0;
        margin: 0;
        min-width: 400px;
        min-height: 600px;
        width: 100%;
        height: 100%;
        position: absolute;
        background-size:cover; 
        font-size: 18px;
    }

    .login-wrap {
        text-align:center;
        margin-top: 10%;
        margin-left: 25%;
        margin-right: 25%;

        min-width: 200px;
        background: rgba(255, 255, 255, 0.6);
        filter: Alpha(opacity=60);
        padding: 60px 140px;
        border-radius: 7px;
        box-shadow: 0px 1px 5px rgba(154, 169, 177, 0.5);
        background: #fff\9;
        filter: Alpha(opacity=60);
    }

    .login-field {
        border: 1px solid #d4d4d4;
        border-radius: 7px;
        margin: 0 auto;
        background: #fff;
    }

    .login-field p {
        border-bottom: 1px solid #d4d4d4;
        height: 50px;
        line-height: 50px;
        position: relative;
        background: #fff\9;
        margin: 0 auto;
    }

    .login-field p.none {
        border-bottom: none;
    }

    .login-label {
        width: 20%;
        text-align: right;
        display: inline-block;
        margin-right: 3%;
        vertical-align: middle;
    }

    .login-input {
        width: 63%;
        border: 0;
        height: 20px;
        vertical-align: middle;
        padding-left: 2%;
        color: #666;
        -webkit-appearance: none;
        background-color: transparent;
    }
    input {
        color: inherit;
        font: inherit;
        margin: 0;
    }
    
    .login-button { 
        padding: 10px 0px;
        text-align: center;
        color: #fff;
        margin: 0 auto;
        margin-top: 10px;
        margin-bottom: 10px;
        display: block;
        border: none;
        outline: none;
        width: 100%;
        border-radius: 5px;
        position: relative\9;
        background: #15b699;
        background: -moz-linear-gradient(top,#15b699,#01b290);
        background: -webkit-linear-gradient(top,#15b699,#01b290);
        background: -o-linear-gradient(top,#15b699,#01b290);
        font-size: 18px;
    }
    .alertStyle {
        color: red;
    }
    span{cursor:pointer;}
    span:hover{color:#41b883;}
</style>