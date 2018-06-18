<template>
    <div class="fillcontain">
        <div>
            <el-button type="primary" class="newBtnStyle" round @click="handleAdd()">添加年级</el-button>
            <el-button type="primary" class="newBtnStyle" icon="el-icon-refresh" circle @click="reloadGrade()"></el-button>
        </div>
        <div class="table_container">
            <el-table :data="tableData" style="width: 100%">

                <el-table-column v-if="false" label="ID" prop="id"></el-table-column>
                <el-table-column label="年级" prop="name"></el-table-column>
                <el-table-column label="描述" prop="description"></el-table-column>

                <el-table-column label="操作" width="240px">
                  <template slot-scope="scope">
                    <el-button size="mini"
                      @click="handleUpdate(scope.$index, scope.row)">编辑</el-button>
                    <el-button size="mini" type="danger"
                      @click="handleDelete(scope.$index, scope.row)">删除</el-button>
                  </template>
                </el-table-column>
            </el-table>

            <el-dialog :title="opType" :visible.sync="dialogFormVisible" class='dialog-style'>
                <el-form :model="selectTable" :rules="rules" ref="selectTable">
                    <el-form-item label="ID:" label-width="120px" v-show="false">
                        <el-input v-model="selectTable.id" auto-complete="off" placeholder="ID"></el-input>
                    </el-form-item>
                    <el-form-item label="年级:" label-width="120px" prop="name">
                        <el-input v-model="selectTable.name" auto-complete="off"
                        placeholder="年级"></el-input>
                    </el-form-item>
                    <el-form-item label="描述" label-width="120px" prop="description">
                        <el-input type="textarea" v-model="selectTable.description" placeholder="描述"></el-input>
                    </el-form-item>
                </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submit('selectTable')">确 定</el-button>
                <el-button @click="resetForm('selectTable')">取 消</el-button>
              </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import _ from 'lodash'
    import {listGrades, createGrade, updateGrade, detailGrade, deleteGrade} from '@/api/getData'

    export default {
        data() {
            return {
                tableData: [],
                selectTable: {},
                opType: '',
                dialogFormVisible: false,
                rules: {
                    name: [
                        { required: true, message: '请输入年级名称', trigger: 'blur' },
                        { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
                    ]
                }
            }
        },
        created() {
            this.checkLogin();
            this.reloadGrade();
        },
        props:['role'],
        inject: ['reloadAllPages'],
    	components: {
    	},
        methods: {
            async reloadGrade(){
                try{
                	this.getGrades();
                } catch(err) {
                    console.log('获取数据失败', err);
                }
            },
            async getGrades() {
            	// TODO id -> current
                const response = await listGrades({});
                this.tableData = [];
                if (response && response.page) {
                    response.page.result.forEach(item => {
                        const row = {};
                        row.id = item.id;
                        row.name = item.name;
                        row.description = item.description;
                        this.tableData.push(row);
                    })
                }
            },
            handleAdd() {
                this.dialogFormVisible = true;
                this.opType = '添加年级';
                this.selectTable.id = 0;             
            },
            handleUpdate(index, row) {
                this.dialogFormVisible = true;
                this.opType = '编辑年级';
                _.assign(this.selectTable, row);
            },
            handleDelete(index, row) {
                this.$confirm('确认删除' + row.name + '？')
                  .then(_ => {
                    this.handleDelete_inner(index, row);
                  })
                  .catch(_ => {});
            },
            async handleDelete_inner(index, row) {
                try {
                    const res = await deleteGrade({id: row.id});
                    if (res.success === true) {
                        this.$message({
                            type: 'success',
                            message: '删除成功'
                        });
                        this.tableData.splice(index, 1);
                        this.reloadGrade();
                        this.reloadAllPages();
                    } else {
                        throw new Error(res.message)
                    }
                } catch(err) {
                    this.$message({
                        type: 'error',
                        message: err.message
                    });
                    console.log('删除失败');
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
            resetForm(formName) {
                this.$refs[formName].resetFields();
                this.dialogFormVisible = false;
            },
            async submit(formName) {
                if (!this.validForm(formName)) return;

                this.dialogFormVisible = false;                
                try {
                    let formData = _.assign(this.selectTable); 

                    var res;
                    if (this.selectTable.id > 0) {
                        res = await updateGrade(formData);
                    } else {
                        res = await createGrade(formData);
                    }
                    if (res.success === true) {
                        this.$message({
                            type: 'success',
                            message: '操作成功'
                        });
                        this.reloadGrade();
                        this.reloadAllPages();
                    } else {
                        this.$message({
                            type: 'error',
                            message: res.message
                        });
                    }
                } catch(err) {
                    console.log('操作失败', err);
                }
            },
        },
    }
</script>

<style lang="less" scoped>
	@import '../style/mixin';
    .table_container {
        padding: 20px;
    }

    .el-table td {
        text-align: left;
    }
    
    .dialog-style {
        margin-top: 2vh;
    }

    .dialog-style .el-form-item {
        margin-bottom: 16px;
        width: 400px;
        text-align: left;
    }

    .dialog-style .el-form-item .el-input {
        text-align: left;
    }

</style>