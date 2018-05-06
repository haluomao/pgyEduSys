<template>
    <div class="fillcontain">
        <div>
            <el-button type="success" round @click="handleAdd()">添加类别</el-button>
        </div>
        <div class="table_container">
            <el-table
                :data="tableData"
                style="width: 100%">

                <el-table-column v-if="false"
                  label="类别ID"
                  prop="id">
                </el-table-column>

                <el-table-column
                  label="类别名称"
                  prop="name">
                </el-table-column>
                <el-table-column
                  label="类别描述"
                  prop="description">
                </el-table-column>
                <el-table-column
                  label="类别价格"
                  prop="price">
                </el-table-column>

                <el-table-column label="操作" width="200">
                  <template slot-scope="scope">
                    <el-button
                      size="mini"
                      @click="handleEdit(scope.$index, scope.row)">编辑</el-button>

                    <el-button
                      size="mini"
                      type="danger"
                      @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                  </template>
                </el-table-column>
            </el-table>
            <div class="Pagination">
                <el-pagination
                  @size-change="handleSizeChange"
                  @current-change="handleCurrentChange"
                  :current-page="formData.page.currentPage"
                  :page-size="20"
                  layout="total, prev, pager, next"
                  :total="formData.page.totalCount">
                </el-pagination>
            </div>
            <el-dialog :title="selectTable.opType" :visible.sync="dialogFormVisible">
                <el-form :model="selectTable">
                    <el-form-item label="类别名称" label-width="80px">
                        <el-input v-model="selectTable.name" auto-complete="off"></el-input>
                    </el-form-item>
                    <el-form-item label="类别描述" label-width="80px">
                        <el-input v-model="selectTable.description"></el-input>
                    </el-form-item>
                </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button @click="dialogFormVisible = false">取 消</el-button>
                <el-button type="primary" @click="submitCategory()">确 定</el-button>
              </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    //import headTop from '../components/headTop'
    import {baseUrl, baseUploadPath} from '@/config/env'
    import {listCategories, deleteCategory, updateCategory, createCategory} from '@/api/getData'
    export default {
        data(){
            return {
                baseUrl,
                baseUploadPath,
                tableData: [],
                formData: {
                	page: {
                		currentPage: 1,
                		totalCount: 0
                	}
                },
                selectTable: {
                    opType: ''
                },
                dialogFormVisible: false
            }
        },
        created() {
            this.initData();
        },
    	components: {
            //headTop
    	},
        methods: {
            async initData(){
                try{
                	this.getCategories();
                }catch(err){
                    console.log('获取数据失败', err);
                }
            },
            async getCategories(){
                const categories = await listCategories({id:2});
                this.tableData = [];
                if (categories && categories.page) {
                    let {currentPage, pageSize, pageNo, totalCount} = categories.page;
                    this.formData.page.currentPage = currentPage;
                    this.formData.page.pageSize = pageSize;
                    this.formData.page.pageNo = pageNo;
                    this.formData.page.totalCount = totalCount;
                    categories.page.result.forEach(item => {
                        const row = {};
                        row.id = item.id;
                        row.name = item.name;
                        row.description = item.description;
                        row.price = item.price;
                        this.tableData.push(row);
                    })
                }
            },
            handleSizeChange(val) {
                console.log(`每页 ${val} 条`);
            },
            handleCurrentChange(val) {
                this.formData.page.currentPage = val;
            },
            handleEdit(index, row) {
                this.dialogFormVisible = true;
                this.selectTable = row;
                this.selectTable.opType = '编辑类别';
            },
            handleAdd() {
                this.dialogFormVisible = true;
                this.selectTable.opType = '添加类别';
                delete this.selectTable.id;
            },
            async handleDelete(index, row) {
                try{
                    const res = await deleteCategory({id: row.id});
                    if (res.status == 1) {
                        this.$message({
                            type: 'success',
                            message: '删除类别成功'
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
                    console.log('删除类别失败')
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
            },
            async submitCategory(){
                this.dialogFormVisible = false;
                try{
                    Object.assign(this.selectTable);
                    var res;
                    if (this.selectTable.id > 0) {
                        res = await updateCategory(this.selectTable);
                    } else {
                        res = await createCategory(this.selectTable);
                    }
                    if (res.success === true) {
                        this.$message({
                            type: 'success',
                            message: '操作成功'
                        });
                        this.getCategories();
                    } else {
                        this.$message({
                            type: 'error',
                            message: res.message
                        });
                    }
                }catch(err){
                    console.log('操作失败', err);
                }
            },
        },
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
</style>