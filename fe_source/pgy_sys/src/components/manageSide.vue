<template>
	<div id='manageSide'>
		<el-menu default-active="1" class="el-menu-demo" mode="" @select="">
          <el-menu-item index="1"><a href="/#/accountInfo?type=1">账户信息</a></el-menu-item>
          <el-menu-item index="2"><a href="/#/coursewareList?type=2">修改密码</a></el-menu-item>
        </el-menu>
	</div>
</template>

<script>
	import {allGrades} from '@/api/getData'

	export default {
		data() {
			return {
				gradeSelect: '',
				grades: [{'value': 1, 'label': 2}]
			}
		}

	}
</script>

<style scoped>

</style>