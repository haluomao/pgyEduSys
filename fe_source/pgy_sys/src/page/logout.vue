<template>
    <div>
        <h3>欢迎 {{name}}</h3>
        <a href="#" @click="quit">注销登录</a>
    </div>
</template>

<script>
/*引入公共方法*/
import { setCookie,getCookie,delCookie } from '@/assets/cookie.js'

export default{
    data(){
        return{
            name: ''
        }
    },
    mounted(){
        /*页面挂载获取保存的cookie值，渲染到页面上*/
        let userInfo = getCookie('userInfo')
        if (userInfo === undefined) {
            this.$router.push('/')
        } else {
        	this.name = userInfo.username;
        }
    },
    methods:{
        quit(){
            /*删除cookie*/
            delCookie('userInfo')
        }
    }
}
</script>