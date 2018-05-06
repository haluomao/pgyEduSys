<template>
    <el-container>
  <el-header>Header</el-header>
  <el-main>Main</el-main>
</el-container>
</template>

<script>
	import {logout} from '@/api/getData'
	import {baseImgPath} from '@/config/env'
	import {mapActions, mapState} from 'vuex'

    export default {
    	data() {
    		return {
    			baseImgPath
    		}
    	},
    	created(){
    		if (!this.adminInfo.id) {
    			this.getAdminData();
    		}
    	},
    	computed: {
    		// ...将一个数组变成参数序列
    		...mapState(['adminInfo']),
    	},
		methods: {
			...mapActions(['getAdminData']),
			async handleCommand(command) {
				if (command == 'home') {
					this.$router.push('/home');
				}else if(command == 'logout'){
					const res = await logout()
					if (res.status == 1) {
						this.$message({
	                        type: 'success',
	                        message: '退出成功'
	                    });
	                    this.$router.push('/');
					}else{
						this.$message({
	                        type: 'error',
	                        message: res.message
	                    });
					}
				}
			},
		}
    }
</script>

<style lang="less">
	@import '../style/mixin';
	.header_container{
		background-color: #EFF2F7;
		height: 60px;
		display: flex;
		justify-content: space-between;
		align-items: center;
		padding-left: 20px;
	}
	.avator{
		.wh(36px, 36px);
		border-radius: 50%;
		margin-right: 37px;
	}
	.el-dropdown-menu__item{
        text-align: center;
    }
</style>

<!-- <template>
	<el-menu :default-active="activeIndex" class="el-menu-demo" mode="horizontal" @select="handleSelect">
		<el-menu-item index="1"><a href="#/1" target="_blank">处理中心</a></el-menu-item>
		<el-menu-item index="2"><a href="#/1" target="_blank">处理中心</a></el-menu-item>
		<el-menu-item index="3"><a href="#/1" target="_blank">消息中心</a></el-menu-item>
		<el-menu-item index="4"><a href="#/1" target="_blank">订单管理</a>
		</el-menu-item>
	</el-menu>
</template>

<script>

export default {
  name: 'Header',
  data () {
    return {
    	activeIndex: 1
    }
  }
}
</script> -->