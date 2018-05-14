<template>
    <div>
        <div class="login-wrap" v-show="showLogin">
            <h3>登录</h3>
            <p v-show="showTip">{{tip}}</p>
            <input type="text" placeholder="请输入用户名" v-model="username">
            <input type="password" placeholder="请输入密码" v-model="password">
            <button @click="handleLogin">登录</button>
            <div @click="toRegister">没有账号？马上注册</div>
        </div>
    </div>
</template>


<script>
	// ref: https://segmentfault.com/a/1190000009329619
	import {login} from '@/api/getData'

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
                newPassword: ''
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
		        if(this.username == "" || this.password == ""){
		            alert("请输入用户名或密码");
		        } else {
		            try {
	                    const res = await login({'username':this.username,'password':this.password});
	                    if (res.success === true) {
	                        this.$message({
	                            type: 'success',
	                            message: '登录成功'
	                        });
	                        this.$emit('userSignIn', res.result);
	                        this.$router.push('/home');
	                    } else {
	                        this.tip = res.message;
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
    .login-wrap{text-align:center;}
    input{display:block; width:250px; height:40px; line-height:40px; margin:0 auto; margin-bottom: 10px; outline:none; border:1px solid #888; padding:10px; box-sizing:border-box;}
    p{color:red;}
    button{display:block; width:250px; height:40px; line-height: 40px; margin:0 auto; border:none; background-color:#41b883; color:#fff; font-size:16px; margin-bottom:5px;}
    span{cursor:pointer;}
    span:hover{color:#41b883;}
</style>