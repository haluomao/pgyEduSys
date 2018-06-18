<template>
    <div class="fillcontain">
        <el-row>
            <el-col :span="12">
                <template v-if="isSuperAdmin">
                    <el-button type="primary" class="newBtnStyle" round @click="handleAdd()">添加资料</el-button>
                </template>
                <template v-else>
                    <div class="grid-content bg-purple-dark">&nbsp;</div>
                </template>
            </el-col>

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

                <el-table-column>
                    <template slot-scope="scope">
                        <img :src="scope.row.icon" style="width:90px; height:90px" />
                    </template>
                </el-table-column>
                <el-table-column :label='catName+"名称"' prop="name"></el-table-column>
                <el-table-column :label='catName+"描述"' prop="description"></el-table-column>

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
          <!-- <div id='qrzone' :class="qrStyle" @click="handleQRCodeClose()" /> -->
          <div id='qrzone' />
          <span slot="footer" class="dialog-footer">
            <a id="downloadLink"></a>
            <el-button type="primary" @click="saveQr()">保存</el-button>
            <el-button @click="qrDialogVisible = false">关闭</el-button>
          </span>
        </el-dialog>

        <el-dialog :title="flashName"
          :visible.sync="flashDialogVisible" >
            <div class="flashStyle">
            <embed style="RIGHT: 10px; POSITION: absolute; TOP: 10px" align=center
                :src='flashUrl' width="100%"
                type='application/x-shockwave-flash'
                wmode="transparent" quality="high"></embed>
            </div>
        </el-dialog>

        <el-dialog :title="pdfName"
          :visible.sync="pdfDialogVisible" width="80%">
            <div class="pdfStyle">
                <pdf :src='pdfUrl' />
            </div>
        </el-dialog>
    </div>
</template>

<script>
    import _ from "lodash"
    import { MaterialTypeEnum } from '@/config/enum' 
    import {listMaterials, deleteMaterial} from '@/api/getData'
    import {getCookie} from '@/assets/cookie'
    import QRCode from 'qrcodejs2'
    import pdf from 'vue-pdf'
    
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
                pdfUrl: ''
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
            
            var self = this;
            this.initData();

            this.$root.Bus.$on('queryCoursewareList', function(val) {
                if (val) {   
                    self.gradeId = val.gradeId;
                    self.categoryId = val.categoryId;
                }
                self.reload();
            });

            this.$root.Bus.$on('coursewareList', function(val) {
                self.reload();
            });

            let cookie = getCookie('userInfo');
            if (cookie) {
                let userInfo = JSON.parse(cookie)
                this.isSuperAdmin = userInfo.role === 'SUPERADMIN';
            }
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
                this.getMaterials();

                let path = this.$route.path;
                let type = path.charAt(path.length - 1);
                if (type === '2') {
                    this.catName = "教案";
                } else if (type === '3') {
                    this.catName = "习题";
                } else {
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
                return params;
            },
            async initData() {
                try {
                	this.getMaterials();
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
                if (false && (row.url.indexOf('.swf') > 0 || row.url.indexOf('.SWF') > 0)) {
                    this.flashName = row.name;
                    this.flashUrl = row.url;
                    this.flashDialogVisible = true;
                } else if (row.url.indexOf('.pdf') > 0 || row.url.indexOf('.PDF') > 0) {
                    this.pdfName = row.name;
                    this.pdfUrl = row.url;
                    this.pdfDialogVisible = true;
                } else {
                    window.open(row.url);
                }
            },
            handleQRCode(index, row) {
                this.qrDialogVisible = true;
                this.qrName = row.name;
                this.$nextTick(function() {
                    this.qrcode(row.url);
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
    .qrNewStyle { 
        text-align: center;
    }

    .flashStyle {
        text-align: center;
    }
</style>