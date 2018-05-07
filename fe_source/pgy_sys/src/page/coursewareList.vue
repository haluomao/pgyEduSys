<template>
    <div class="fillcontain">
        <el-row>
            <el-col :span="12">
                <el-button type="success" round @click="handleAdd()">添加课件</el-button>
            </el-col>

            <el-col :span="8">
                <el-input placeholder="请输入课件名称" v-model="nameQuery" class="input-with-select">
                    <el-button slot="append" icon="el-icon-search" @click="reload()"></el-button>
                </el-input>
            </el-col>

            <el-col :span="4">
                <el-button type="success" icon="el-icon-refresh" circle @click="reload()"></el-button>
            </el-col>
        </el-row>
        <div class="table_container">
            <el-table :data="tableData" style="width: 100%" :show-header="true">

                <el-table-column v-if="false" label="课件ID" prop="id"></el-table-column>

                <el-table-column>
                    <template slot-scope="scope">
                        <img :src="scope.row.icon" style="width:90px; height:90px" />
                    </template>
                </el-table-column>
                <el-table-column label="课件名称" prop="name"></el-table-column>
                <el-table-column label="课件描述" prop="description"></el-table-column>

                <el-table-column label="操作" width="240px">
                  <template slot-scope="scope">
                  	<el-button size="mini"
                      @click="handleOpen(scope.$index, scope.row)">打开</el-button>
                    <el-button size="mini"
                      @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
                    <el-button size="mini" type="danger"
                      @click="handleDelete(scope.$index, scope.row)">删除</el-button>
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
    </div>
</template>

<script>
    import _ from "lodash"
    import {listMaterials, deleteMaterial} from '@/api/getData'

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
                nameQuery: ''
            }
        },
        created() {
            this.initData();
        },
    	components: {
    	},
        methods: {
            reload() {
                this.getMaterials();
            },
            buildQueryForm() {
                let params = _.assign({}, this.formData.page);
                params.name = this.nameQuery;
                params.type = this.$route.query.type;
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
                        row.icon = item.icon;
                        row.name = item.name;
                        row.description = item.description;
                        row.price = item.price;
                        row.url = item.url;
                        this.tableData.push(row);
                    })
                }
            },
            handleCurrentChange(val) {
                this.formData.page.currentPage = val;
            },
            handleAdd: function() {
                this.$router.push({path:'addCourseware'});
            },
            handleOpen(index, row) {
                window.open(row.url);
            },
            handleEdit(index, row) {
                this.$router.push({path:'addCourseware', params: {id: row.id}});
            },
            
            async handleDelete(index, row) {
                try{
                    const res = await deleteMaterial({id: row.id});
                    if (res.status == 1) {
                        this.$message({
                            type: 'success',
                            message: '删除成功'
                        });
                        this.tableData.splice(index, 1);
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
                            cb(cityList)
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
</style>