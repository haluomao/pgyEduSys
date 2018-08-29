<template>
    <div class="fillcontain">
        <el-row>
            <el-col :span="9" v-if="!isSharing">
                <template v-if="isCourseware">
                    <div class="cTitleDiv"><span class="cTitleStyle">{{cateName}}互动小课堂</span> <br /><span class="cSubTitleStyle">像公开课一样优秀</span></div>
                </template>
                <template v-else>
                    <div class="grid-content bg-purple-dark">&nbsp;</div>
                </template>
            </el-col>

            <template v-if="isSharing">
                <el-col :span="8">
                    <el-radio-group v-model="sharingType">
                      <el-radio-button label="网上资源" @click="reload()"></el-radio-button>
                      <el-radio-button label="我的共享" @click="reload()"></el-radio-button>
                    </el-radio-group>
                </el-col>
            </template>

            <template v-if="isSuperAdmin || isSharing">
                <el-col :span="4">
                    <el-button type="primary" class="newBtnStyle" round @click="handleAdd()">添加资料</el-button>
                </el-col>
            </template>

            <el-col :span="8">
                <el-input :placeholder='"请输入"+catName+"名称"' v-model="nameQuery" class="input-with-select">
                    <el-button slot="append" icon="el-icon-search" @click="reload()"></el-button>
                </el-input>
            </el-col>

            <el-col :span="4">
                <el-button icon="el-icon-refresh" type="success"  @click="reload()"></el-button>
            </el-col>
        </el-row>
        <div class="table_container">
            <el-table :data="tableData" style="width: 100%" :show-header="true">

                <el-table-column v-if="false" :label='catName+"ID"' prop="id"></el-table-column>

                <el-table-column label="缩略图"  width="120px">
                    <template slot-scope="scope">
                        <img :src="scope.row.icon" style="width:90px; height:90px" />
                    </template>
                </el-table-column>
                <el-table-column :label='catName+"名称"' prop="name" width="200px"></el-table-column>
                <el-table-column :label='catName+"描述"' prop="description" ></el-table-column>

                <el-table-column label="操作" width="300px">
                  <template slot-scope="scope">
                  	<el-button size="mini" type="success"
                      @click="handleOpen(scope.$index, scope.row)">打开</el-button>
                    <template v-if="catName==='习题'"> 
                        <el-button size="mini" type="primary"
                            @click="handleQRCode(scope.$index, scope.row)">二维码</el-button>
                    </template>
                    <template v-if="isSuperAdmin"> 
                        <el-button size="mini" type="primary"
                          @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                        <el-button size="mini" type="danger"
                          @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                    </template>
                  </template>
                </el-table-column>
            </el-table>

            <div class="Pagination">
                <el-pagination
                  @current-change="handleCurrentChange"
                  :current-page="formData.page.currentPage"
                  :page-size="formData.page.pageSize"
                  :total="formData.page.totalCount"
                  layout="total, prev, pager, next">
                </el-pagination>
            </div>
        </div>

        <el-dialog
          title="二维码"
          :visible.sync="qrDialogVisible"
          width="240px">
          <div id='qrzone' />
          <span slot="footer" class="dialog-footer">
            <a id="downloadLink"></a>
            <el-button type="primary" @click="saveQr()">保存</el-button>
            <el-button @click="qrDialogVisible = false">关闭</el-button>
          </span>
        </el-dialog>

        <el-dialog :title="flashName"
          :visible.sync="flashDialogVisible" id="flashDialogStyle">
            <div>
                <embed class="embedStyle" :src='flashUrl' 
                    type='application/x-shockwave-flash'></embed>
            </div>
            <div class="retryClickStyle">
                预览出错了?<a :href='flashUrl' target="_blank">点我试试</a></div>
        </el-dialog>

        <el-dialog :title="pdfName"
          :visible.sync="pdfDialogVisible" id="pdfDialogStyle">
            <div class="pdfStyle">
                <button @click="function(){if(pdfCurrentPage>1) {pdfCurrentPage -= 1;}}">&lt;</button>
                <input v-model.number="pdfCurrentPage" type="number" style="width: 5em"> /{{pdfPageCount}}
                <button @click="function(){if(pdfCurrentPage < pdfPageCount) {pdfCurrentPage += 1;}}">&gt;</button>
                <button @click="pdfRotate += 90">&#x27F3;</button>
                <button @click="pdfRotate -= 90">&#x27F2;</button>
                <button @click="$refs.pdf.print()">打印</button>
                <button @click="pdfDialogVisible=false">关闭</button>
                <div>
                    <div v-if="pdfLoadedRatio > 0 && pdfLoadedRatio < 1" style="background-color: green; color: white; text-align: center" :style="{ width: pdfLoadedRatio * 100 + '%' }">{{ Math.floor(pdfLoadedRatio * 100) }}%</div>
                    <pdf v-if="pdfDialogVisible" ref="pdf" style="border: 1px solid red" :src="pdfUrl" :page="pdfCurrentPage" :rotate="pdfRotate" @progress="pdfLoadedRatio = $event"  @num-pages="pdfPageCount = $event" @link-clicked="pdfCurrentPage = $event"></pdf>
                </div>
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import _ from "lodash"
    import { MaterialTypeEnum, PublicLevelEnum } from '@/config/enum' 
    import {listMaterials, deleteMaterial} from '@/api/getData'
    import {getCookie} from '@/assets/cookie'
    import QRCode from 'qrcodejs2'
    import pdf from 'vue-pdf'
    import { baseLoginUrl } from '@/config/env'
    
    export default {
        data(){
            return {
                tableData: [],
                formData: {
                	page: {
                		currentPage: 1,
                		totalCount: 0,
                        pageSize: 20
                	}
                },
                nameQuery: '',
                isSuperAdmin: false,
                isTeacher: false,
                isSharing: false,
                isCourseware: true,
                qrStyle: '',
                catName: '课件',
                defaultImg: require('../assets/img/1.png'),
                gradeId: 0,
                categoryId: 0,
                qrDialogVisible: false,
                qrName: '',
                flashDialogVisible: false,
                flashName: '',
                flashUrl: '',
                pdfDialogVisible: false,
                pdfName: '',
                pdfUrl: '',
                cateName: '',
                pdfPageCount: 0,
                pdfCurrentPage: 1,
                pdfLoadedRatio: 0,
                pdfRotate: 0,
                sharingType: '我的共享'
            }
        },
        watch: {
            '$route' (to, form) {
                if (to.path.indexOf('coursewareList') > 0) {
                    this.reload();
                }
            }
        },
        created() {
            this.checkLogin();
            
            let cookie = getCookie('userInfo');
            if (cookie) {
                let userInfo = JSON.parse(cookie);
                this.isSuperAdmin = userInfo.role === 'SUPERADMIN';
                this.isTeacher = userInfo.role === 'TEACHER';
            }

            var self = this;
            this.initData();

            this.$root.Bus.$on('queryCoursewareList', function(val) {
                if (val) {
                    self.gradeId = val.gradeId;
                    self.categoryId = val.categoryId;
                    self.cateName = val.categoryId===1 ? '数学':(val.categoryId===2 ? '语文': ''); 
                }
                self.reload();
            });

            this.$root.Bus.$on('coursewareList', function(val) {
                self.reload();
            });
        },
    	components: {
            pdf: pdf
    	},
        methods: {
            qrcode (content) { 
                var ele = document.getElementById('qrzone');
                ele.innerHTML ='';    
                let qrcode = new QRCode('qrzone', {  
                    width: 200,  
                    height: 200, // 高度  
                    text: content // 二维码内容  
                    // render: 'canvas' // 设置渲染方式（有两种方式 table和canvas，默认是canvas）  
                    // background: '#f0f'  
                    // foreground: '#ff0'  
                });
            },
            saveQr() {
                // 获取base64的图片节点
                var img = document.getElementById('qrzone').getElementsByTagName('img')[0];
                // 构建画布
                var canvas = document.createElement('canvas');
                canvas.width = img.width;
                canvas.height = img.height;
                canvas.getContext('2d').drawImage(img, 0, 0);
                // 构造url
                var url = canvas.toDataURL('image/png');
                // 构造a标签并模拟点击
                var downloadLink = document.getElementById('downloadLink');
                downloadLink.setAttribute('href', url);
                downloadLink.setAttribute('download', this.qrName + '.png');
                downloadLink.click();
            },
            reload() {
                this.pdfDialogVisible = false;
                this.flashDialogVisible = false;
                this.getMaterials();

                let path = this.$route.path;
                let type = path.charAt(path.length - 1);
                this.isCourseware = false;
                this.isSharing = false;
                if (type === '2') {
                    this.catName = "教案";
                } else if (type === '3') {
                    this.catName = "习题";
                } else if (type === '4') {
                    this.catName = "共享资源";
                    this.isSharing = true;
                } else {
                    this.isCourseware = true;
                    this.catName = "课件";
                }
            },
            buildQueryForm() {
                let params = _.assign({}, this.formData.page);
                params.name = this.nameQuery;
                let path = this.$route.path;
                let type = path.charAt(path.length - 1);
                for (let key in MaterialTypeEnum.prop) {
                    if (MaterialTypeEnum.prop[key].value === 
                        parseInt(type, 10)) {
                        params.teachType = MaterialTypeEnum.prop[key].code;
                    }
                }
                params.gradeId = this.gradeId;
                params.categoryId = this.categoryId;

                if (this.isSharing === true) {
                    if (this.sharingType === PublicLevelEnum.prop['PRIVATE'].text) {
                        params.publicLevel = 'PRIVATE';
                    } else {
                        params.publicLevel = 'ONLINE_PUBLIC';
                    }
                } else {
                    params.publicLevel = 'PUBLIC';
                }
                return params;
            },
            async initData() {
                try {
                	this.reload();
                } catch(err) {
                    console.log('获取数据失败', err);
                }
            },
            async getMaterials() {
                const materials = await listMaterials(this.buildQueryForm());
                this.tableData = [];
                if (materials && materials.page) {
                    let {currentPage, pageSize, pageNo, totalCount} = materials.page;
                    this.formData.page.currentPage = currentPage;
                    this.formData.page.pageSize = pageSize;
                    this.formData.page.pageNo = pageNo;
                    this.formData.page.totalCount = totalCount;
                    materials.page.result.forEach(item => {
                        const row = {};
                        row.id = item.id;
                        if (item.avatar) {
                            row.icon = item.avatar;
                        } else {
                            row.icon = this.defaultImg;
                        }
                        row.name = item.name;
                        row.description = item.description;
                        row.price = item.price;
                        row.url = item.material;
                        row.downloadUrl = item.downloadUrl;
                        this.tableData.push(row);
                    })
                }
            },
            handleCurrentChange(val) {
                this.formData.page.currentPage = val;
            },
            handleAdd: function() {
                this.$router.push({path:'addCourseware/0'});
                this.$root.Bus.$emit('addCourseware', {});
            },
            handleOpen(index, row) {
                if (row.url.indexOf('.swf') > 0 || row.url.indexOf('.SWF') > 0) {
                    this.flashName = row.name;
                    this.flashUrl = row.url;
                    this.flashDialogVisible = true;
                } else if (row.url.indexOf('.pdf') > 0 || row.url.indexOf('.PDF') > 0) {
                    this.pdfName = row.name;
                    this.pdfUrl = row.url; //'https://cdn.mozilla.net/pdfjs/tracemonkey.pdf'; 
                    this.pdfPageCount = 0;
                    this.pdfRotate = 0;
                    console.log( this.pdfUrl);
                    this.pdfDialogVisible = true;
                } else {
                    window.open(row.url);
                }
            },
            handleQRCode(index, row) {
                this.qrDialogVisible = true;
                this.qrName = row.name;
                row.qrUrl = baseLoginUrl + '?redirect=' + encodeURIComponent(row.url);
                this.$nextTick(function() {
                    this.qrcode(row.qrUrl);
                });
            },
            handleQRCodeClose() {
                //document.getElementById('qrzone').innerHTML='';
                //this.qrStyle='';
            },
            handleEdit(index, row) {
                this.$router.push('/addCourseware/' + row.id);
                this.$root.Bus.$emit('addCourseware', {id:row.id});
            },
            
            async handleDelete(index, row) {
                try{
                    const res = await deleteMaterial({id: row.id});
                    if (res.success === true) {
                        this.$message({
                            type: 'success',
                            message: '删除成功'
                        });
                        this.tableData.splice(index, 1);
                        this.formData.page.totalCount = this.formData.page.totalCount - 1;
                    }else{
                        throw new Error(res.message)
                    }
                }catch(err){
                    this.$message({
                        type: 'error',
                        message: err.message
                    });
                    console.log('删除失败')
                }
            },
            async querySearchAsync(queryString, cb) {
                if (queryString) {
                    try{
                        const cityList = await searchplace(this.city.id, queryString);
                        if (cityList instanceof Array) {
                            cityList.map(item => {
                                item.value = item.address;
                                return item;
                            })
                            cb(cityList);
                        }
                    }catch(err){
                        console.log(err)
                    }
                }
            }
        }
    }
</script>

<style lang="less">
	@import '../style/mixin';
    .demo-table-expand {
        font-size: 0;
    }
    .demo-table-expand label {
        width: 90px;
        color: #99a9bf;
    }
    .demo-table-expand .el-form-item {
        margin-right: 0;
        margin-bottom: 0;
        width: 50%;
    }
    .table_container{
        padding: 20px;
    }
    .Pagination{
        display: flex;
        justify-content: flex-start;
        margin-top: 8px;
    }
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
        border-color: #20a0ff;
    }
    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 120px;
        height: 120px;
        line-height: 120px;
        text-align: center;
    }
    .avatar {
        width: 120px;
        height: 120px;
        display: block;
    }
    .input-with-select .el-input-group__prepend {
        background-color: #fff;
    }
    .qrNewStyle, .pdfStyle { 
        text-align: center;
    }

    #flashDialogStyle .el-dialog, #pdfDialogStyle .el-dialog{
        height: 100%;
        width: 100%;
        margin-top: 1px !important;
    }
    #flashDialogStyle .el-dialog .el-dialog__header, 
    #pdfDialogStyle .el-dialog .el-dialog__header{
        padding: 10px 20px 0px;
    }
    #flashDialogStyle .el-dialog button {
        top: 5px;
        right: 5px;
        font-size: 32px;
    }
    #pdfDialogStyle .el-dialog button {
        top: 5px;
        right: 5px;
        font-size: 16px;
    }
    #flashDialogStyle .el-dialog__body, #pdfDialogStyle .el-dialog__body{
        padding: 10px 2px;
    }
    #flashDialogStyle i, #pdfDialogStyle i {
        color: #F00;
    }
    .embedStyle {
        left: 0px;
        top: 40px;
        bottom: 0px;
        position: absolute;
        text-align: center;
        width: 100%;
        height: 95%;
    }
    .retryClickStyle {
        position: absolute;
        top: 12px;
        right: 100px;
    }

    .cTitleDiv {
        margin-top: -14px;
        margin-bottom: -20px;
    }
    .cTitleStyle {
        font-size: 32px;
        color: #ff982a;
        //color: #f56c6c;
        //color: #d5596f;
    }
    .cSubTitleStyle {
        color: #ff982a;
        text-align: center;
    }
</style>