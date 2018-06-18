<template>
	<div id='addCourseware'>
		<el-container>
			<el-main>
		<el-form ref="form" :model="form" label-width="80px" :rules="rules">
      <el-form-item label="id：" v-show="false">
        <el-input v-model="form.id"></el-input>
      </el-form-item>
      <el-form-item label="类型：" prop="type">
        <el-select v-model="form.teachType" placeholder="请选择类型" :disabled="isEdit">
          <el-option
            v-for="item in types"
            :key="item.value"
            :label="item.text"
            :value="item.code">
          </el-option>
        </el-select>
      </el-form-item>
		  <el-form-item label="名称：" prop="name">
		    <el-input v-model="form.name"></el-input>
		  </el-form-item>
      <el-form-item label="作者：">
        <el-input v-model="form.author"></el-input>
      </el-form-item>
		  <el-form-item label="年级：" prop="gradeId">
		    <el-select v-model="form.gradeId" placeholder="请选择所属年级">
          <el-option
            v-for="item in grades"
            :key="item.id"
            :label="item.name"
            :value="item.id">
          </el-option>
		    </el-select>
		  </el-form-item>
      <el-form-item label="科目：" prop="categoryId">
        <el-select v-model="form.categoryId" placeholder="请选择所属科目">
          <el-option
            v-for="item in categories"
            :key="item.id"
            :label="item.name"
            :value="item.id">
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
  			  :before-upload="beforeAvatarUpload">
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
			  :on-success="handleCoursewareUploadSuccess"
			  :before-upload="beforeCoursewareUpload">
			  <i class="el-icon-upload"></i>
			  <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
			</el-upload>	
		  </el-form-item>
		  <el-form-item>
		    <el-button type="primary" class="newBtnStyle" @click="onSubmit('form')">立即创建</el-button>
		    <el-button @click="onCancel()">取消</el-button>
		  </el-form-item>
		</el-form>	
			</el-main>
		</el-container>
</div>
	</div>
</template>

<script>
import _ from 'lodash'
import { baseUploadPath } from '@/config/env'
import { MaterialTypeEnum } from '@/config/enum' 
import {upload, allGrades, allCategories, updateMaterial, createMaterial, detailMaterial} from '@/api/getData'

const defaultForm = {
  id: 0,
  gradeId: '',
  categoryId: ''
};
export default {
    data() {
      return {
      	uploadUrl: '',
      	imageUrl: '',
        grades: [],
        categories: [],
        isEdit: false,
        form: defaultForm,
        types: [],
        rules: {
            teachType: [
                { required: true, message: '请选择类型', trigger: 'blur' }
            ],
            name: [
                { required: true, message: '请输入课件名', trigger: 'blur' },
                { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
            ],
            gradeId: [
                { required: true, message: '请选择年级', trigger: 'blur' }
            ],
            categoryId: [
                { required: true, message: '请选择科目', trigger: 'blur' }
            ],
            description: [
                { required: true, message: '请输入描述', trigger: 'blur' },
                { min: 1, max: 1000, message: '长度在 1 到 1000 个字符', trigger: 'blur' }
            ],
            material:[
                { required: true, message: '请上传课件', trigger: 'blur' }
            ]
        }
      }
    },
    inject: ['reloadAllPages'],
    created() {
      this.checkLogin();
      this.reload();

      var self = this;
      this.$root.Bus.$on('addCourseware', function() {
          self.reload();
      });
    },
    methods: {
      reload() {
        this.types = [];
        for(var key in MaterialTypeEnum.prop){
          this.types.push(MaterialTypeEnum.prop[key]);
        }
        this.getAllGrades();
        this.getAllCategories();

        if (this.$route.params.id > 0){
          this.form.id = this.$route.params.id;
          this.isEdit = true;
          this.detail(this.form.id);
        } else {
          this.form = defaultForm;
          this.isEdit = false;
        }
      },
      async detail(cid) {
        try{
          const res = await detailMaterial({id: cid});
          if (res.success === true) {
            _.assign(this.form, res.result);
          } else {
            this.checkLogin();
            console.log("获取详情失败");
          }
        } catch(e) {
          this.checkLogin();
          console.log(e);
        }
      },
      async getAllGrades() {
        try{
          const res = await allGrades();
          if (res.success === true) {
            this.grades = res.result;
          } else {
            this.checkLogin();
            console.log("获取年级失败");
          }
        } catch(e) {
          console.log(e)
        }
      },
      async getAllCategories() {
        try{
          const res = await allCategories();
          if (res.success === true) {
            this.categories = res.result;
          } else {
            this.checkLogin();
            console.log("获取科目失败");
          }
        } catch(e) {
          this.checkLogin();
          console.log(e);
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
              if (this.form.id > 0) {
                  res = await updateMaterial(formData);
              } else {
                  res = await createMaterial(formData);
              }
              if (res.success === true) {
                  this.$message({ type: 'success', message: '操作成功'});
                  let type = MaterialTypeEnum.prop[this.form.teachType].value;
                  this.$router.push('/coursewareList' + type);
                  this.form = defaultForm;
                  this.$root.Bus.$emit('coursewareList', {});
              } else {
                  this.checkLogin();
                  this.$message({ type: 'error', message: res.message });
              }
          } catch(err) {
              console.log('操作失败', err);
          }
      },
      handleUploadSuccess(res, file) {
        if (res.success === true) {
            this.imageUrl = res.result.url;
            this.form.avatar = this.imageUrl;
        } else {
            this.$message({ type: 'error', message: res.message });
        }
      },
      handleCoursewareUploadSuccess(res) {
        if (res.success === true) {
            this.form.material = res.result.url;
        } else {
            this.$message({ type: 'error', message: res.message });
        }
      },
      beforeUpload(file, size) {
        this.checkLogin();
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
      onCancel() {
        this.$router.push({path:'/coursewareList1'});
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