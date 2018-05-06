<template>
	<div id='addCourseware'>
		<el-container>
			<el-main>
		<el-form ref="form" :model="form" label-width="80px">
		  <el-form-item label="名称">
		    <el-input v-model="form.name"></el-input>
		  </el-form-item>
		  <el-form-item label="类别">
		    <el-select v-model="form.category" placeholder="请选择活动区域">
		      <el-option label="区域一" value="shanghai"></el-option>
		      <el-option label="区域二" value="beijing"></el-option>
		    </el-select>
		  </el-form-item>
		  <el-form-item label="描述">
		    <el-input type="textarea"	 v-model="form.description"></el-input>
		  </el-form-item>
		  <el-form-item label="作者">
		    <el-input v-model="form.author"></el-input>
		  </el-form-item>
		  <el-form-item label="缩略图">
		  	<el-upload
			  class="avatar-uploader"
			  :action="handonAction()"
			  :show-file-list="false"
			  :on-success="handleUploadSuccess"
			  :before-upload="beforeAvatarUpload"
			  :on-change="uploadFile">
			  <img v-if="imageUrl" :src="imageUrl" class="avatar">
			  <i v-else class="el-icon-plus avatar-uploader-icon"></i>
			</el-upload>	
		  </el-form-item>
		  <el-form-item label="上传课件">
		  	<el-upload
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
		    <el-button type="primary" @click="onSubmit">立即创建</el-button>
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
import {upload} from '@/api/getData'

export default {
    data() {
      return {
      	uploadUrl: '',
      	imageUrl: '',
        form: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        }
      }
    },
    methods: {
      onSubmit() {
        console.log('submit!');
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

.el-textarea__inner {
	height: 100px;
}

.avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
    background-color: #FFFFFF;
}

.avatar-uploader .el-upload:hover {
	border-color: #409EFF;
}

.avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
}
</style>