<template>
	<div id='addCourseware'>
		<el-container>
			<el-main>
		<el-form ref="form" :model="form" label-width="80px" :rules="rules">
		  <el-form-item label="名称：" prop="name">
		    <el-input v-model="form.name"></el-input>
		  </el-form-item>
      <el-form-item label="作者：">
        <el-input v-model="form.author"></el-input>
      </el-form-item>
		  <el-form-item label="年级：" prop="grade">
		    <el-select v-model="form.grade" placeholder="请选择所属年级">
          <el-option
            v-for="item in grades"
            :key="item.value"
            :label="item.name"
            :value="item.value">
          </el-option>
		    </el-select>
		  </el-form-item>
      <el-form-item label="科目：" prop="category">
        <el-select v-model="form.category" placeholder="请选择所属科目">
          <el-option
            v-for="item in categories"
            :key="item.value"
            :label="item.name"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
		  <el-form-item label="描述：" prop="description">
		    <el-input type="textarea"	 v-model="form.description"></el-input>
		  </el-form-item>
		  <el-form-item label="缩略图：" prop="avatar">
		  	<el-upload
        v-model="form.avatar"
        list-type="picture-card"
			  :action="handonAction()"
			  :show-file-list="false"
			  :on-success="handleUploadSuccess"
			  :before-upload="beforeAvatarUpload"
			  :on-change="uploadFile">
			  <img v-if="imageUrl" :src="imageUrl" class="avatar">
			  <i v-else class="el-icon-plus"></i>
			</el-upload>	
		  </el-form-item>
		  <el-form-item label="课件：" prop="material" >
		  	<el-upload
        v-model="form.material"
			  class="upload-demo"
			  drag
			  :action="handonAction()"
			  :on-success="handleUploadSuccess"
			  :before-upload="beforeCoursewareUpload">
			  <i class="el-icon-upload"></i>
			  <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
			</el-upload>	
		  </el-form-item>
		  <el-form-item>
		    <el-button type="primary" @click="onSubmit('form')">立即创建</el-button>
		    <el-button>取消</el-button>
		  </el-form-item>
		</el-form>	
			</el-main>
		</el-container>
</div>
	</div>
</template>

<script>
import {baseUploadPath} from '@/config/env'
import {upload, allGrades, allCategories, updateMaterial, createMaterial} from '@/api/getData'

export default {
    data() {
      return {
      	uploadUrl: '',
      	imageUrl: '',
        grades: [],
        categories: [],
        form: {
        },
        rules: {
            name: [
                { required: true, message: '请输入课件名', trigger: 'blur' },
                { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
            ],
            grade: [
                { required: true, message: '请选择年级', trigger: 'blur' }
            ],
            category: [
                { required: true, message: '请选择科目', trigger: 'blur' }
            ],
            description: [
                { required: true, message: '请输入描述', trigger: 'blur' },
                { min: 1, max: 1000, message: '长度在 1 到 1000 个字符', trigger: 'blur' }
            ],
            avatar:[
                { required: true, message: '请上传图片', trigger: 'blur' }
            ],
            material:[
                { required: true, message: '请上传课件', trigger: 'blur' }
            ]
        }
      }
    },
    mounted() {
      this.getAllGrades();
      this.getAllCategories();
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
          console.log(e)
        }
      },
      async getAllCategories() {
        const res = await allCategories();
        if (res.success === true) {
          this.categories = res.result;
        } else {
          console.log("获取科目失败");
        }
      },
      validForm(formName) {
          let flag = true;
          this.$refs[formName].validate((valid) => {
            if (valid) {
              return true;
            } else {
              console.log('error submit!!');
              flag = false;
              return false;
            }
          });
          return flag;
      },
      async onSubmit(formName) {
          if (!this.validForm(formName)) return;

          try {
              let formData = _.assign(this.form);   

              var res;
              if (this.selectTable.id > 0) {
                  res = await updateMaterial(formData);
              } else {
                  res = await createMaterial(formData);
              }
              if (res.success === true) {
                  this.$message({ type: 'success', message: '操作成功'});
              } else {
                  this.$message({ type: 'error', message: res.message });
              }
          } catch(err) {
              console.log('操作失败', err);
          }
      },
      handleUploadSuccess(res, file) {
        this.imageUrl = URL.createObjectURL(file.raw);
      },
      beforeUpload(file, size) {
      	const isLtSize = file.size / 1024 / 1024 < size;

        if (!isLtSize) {
          this.$message.error('上传头像图片大小不能超过' + size + 'MB!');
        }
        return isLtSize;
      },
      beforeAvatarUpload(file) {
      	return this.beforeUpload(file, 1);
      },
      beforeCoursewareUpload(file) {
      	return this.beforeUpload(file, 100);
      },
      handonAction: function() {
      	return baseUploadPath;
      },
      uploadFile(file) {
      	console.log(file);
      }
    }
  }
</script>

<style scoped>
  .el-main {
      color: #333;
      text-align: left;
      line-height: 160px;
      width: 500px;
  }

  .el-input {
    width: 240px;
  }

  .el-textarea {
    width: 50%;
  }

</style>