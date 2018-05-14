<template>
	<div id='gradeSide'>
		<el-row>
			<label>年 级: </label>
			<el-select class='grade-select' v-model="gradeSelect" placeholder="请选择" @change="gradeChanged">
			    <el-option
			      v-for="item in grades"
			      :key="item.value"
			      :label="item.name"
			      :value="item.value">
			    </el-option>
			</el-select>
		</el-row>
		<el-row>
			<div class='icon-span'>
				<i class="el-icon-edit"></i>
				<span class="icon-name"><a href='/#/coursewareList?type=1&cat=1'>数学互动小课堂</a></span>
			</div>
		</el-row>
		<el-row>
			<div class='icon-span'>
				<i class="el-icon-service"></i>
				<span class="icon-name"><a href='/#/coursewareList?type=1&cat=2'>语文互动小课堂</a></span>
			</div>
		</el-row>
	</div>
</template>

<script>
	import {allGrades} from '@/api/getData'

	export default {
		data() {
			return {
				gradeSelect: '',
				grades: []
			}
		},
		mounted() {
			this.getAllGrades();
		},
		methods: {
			async getAllGrades() {
		        try{
		          const res = await allGrades();
		          if (res.success === true) {
		            this.grades = res.result;
		          } else {
		            console.log("获取年级失败");
		          }
		        } catch(e) {
		        	console.log(e);
		        }
		    },
			gradeChanged(){
				this.$root.Bus.$emit('send', this.gradeSelect);
			}
		}

	}
</script>

<style scoped>
#gradeSide {
	text-align: center;
}

.el-row {
	margin-top: 20px 
}

.grade-select {
	width: 100px;
}

.icon-span i {
	display: block;
    font-size: 20px;
}

.icon-span span {
	display: inline-block;
    line-height: normal;
    vertical-align: middle;
}
</style>