<template>
	<div id='gradeSide'>
		<el-row>
			<label>年 级: </label>
			<el-select class='grade-select' v-model="gradeSelect" placeholder="请选择" @change="gradeChanged">
			    <el-option
			      v-for="item in grades"
			      :key="item.id"
			      :label="item.name"
			      :value="item.id">
			    </el-option>
			</el-select>
		</el-row>
		<template>
			<el-radio-group v-model="checkList" @change="gradeChanged">
				<el-row>
					<div class='icon-span'>
						<i class="el-icon-edit"></i>
					    <el-radio label="数学互动小课堂" border></el-radio>
					</div>
				</el-row>
				<el-row>
					<div class='icon-span'>
						<i class="el-icon-service"></i>
					    <el-radio label="语文互动小课堂" border></el-radio>
					</div>
				</el-row>
			</el-radio-group>
		</template>
	</div>
</template>

<script>
	import {allGrades} from '@/api/getData'

	export default {
		data() {
			return {
				gradeSelect: '',
				grades: [{id:0, name:'全部'}],
				checkList: ''
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
		            this.grades.splice(0, 0, {id:0, name:'全部'});
		          } else {
		            console.log("获取年级失败");
		          }
		        } catch(e) {
		        	console.log(e);
		        }
		    },
			gradeChanged() {
				var data = {gradeId: this.gradeSelect, categoryId: 0};
				if (this.checkList) {
					data.categoryId = this.checkList.indexOf('数学') >= 0 ? 1:2;
				} 
				this.$root.Bus.$emit('queryCoursewareList', data);
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