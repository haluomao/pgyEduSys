<template>
    <div class="fillcontain">
        <div>
            <el-button type="primary" round @click="handleAdd()">添加用户</el-button>
            <el-button type="success" icon="el-icon-refresh" circle @click="reload()"></el-button>
        </div>
        <div class="table_container">
            <el-table :data="tableData" style="width: 100%">

                <el-table-column v-if="false" label="用户ID" prop="id"></el-table-column>
                <el-table-column label="登录名" prop="accountName"></el-table-column>
                <el-table-column label="姓名" prop="username"></el-table-column>
                <el-table-column label="角色" prop="role"></el-table-column>
                <el-table-column label="有效时间" prop="validTimeCN" width="240px"></el-table-column>

                <el-table-column label="操作" width="240px">
                  <template slot-scope="scope">
                    <el-button size="mini"
                      @click="handleUpdate(scope.$index, scope.row)">编辑</el-button>
					<el-button size="mini"
                      @click="handleUpdatePwd(scope.$index, scope.row)">重置密码</el-button>
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
                    <el-form-item label="登录名:" label-width="120px" prop="accountName">
                        <el-input v-model="selectTable.accountName" auto-complete="off" :disabled="selectTable.id!=0"
                        placeholder="登录名"></el-input>
                    </el-form-item>
                    <template v-if="selectTable.id===0">
                       <el-form-item label="密码" label-width="120px" prop="accountPassword">
                          <el-input type="password" v-model.trim="selectTable.accountPassword" placeholder="密码"></el-input>
                        </el-form-item>
                        <el-form-item label="确认密码" label-width="120px" prop="passwordConfirm">
                          <el-input type="password" v-model.trim="selectTable.passwordConfirm" placeholder="确认密码"></el-input>
                        </el-form-item> 
                    </template>
                    <el-form-item label="姓名" label-width="120px" prop="username">
                        <el-input v-model="selectTable.username" placeholder="姓名"></el-input>
                    </el-form-item>
                    <el-form-item label="角色" label-width="120px" prop="role">
                        <el-select v-model="selectTable.role" placeholder="请选择" :disabled="selectTable.id!=0">
                            <el-option
                              v-for="item in validRoles"
                              :key="item.value"
                              :label="item.text"
                              :value="item.code">
                            </el-option>
                        </el-select>
                    </el-form-item>
                    <el-form-item label="手机" label-width="120px" prop="phone">
                        <el-input v-model="selectTable.phone" placeholder="手机"></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱" label-width="120px">
                        <el-input v-model="selectTable.email" placeholder="邮箱"></el-input>
                    </el-form-item>
                    <el-form-item label="有效时间" label-width="120px" prop="validTime">
                        <el-date-picker
                          v-model="selectTable.validTime"
                          type="daterange"
                          align="right"
                          unlink-panels
                          range-separator="至"
                          start-placeholder="开始日期"
                          end-placeholder="结束日期"
                          :picker-options="pickerOptions">
                        </el-date-picker>
                    </el-form-item>
                </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submit('selectTable')">确 定</el-button>
                <el-button @click="resetForm('selectTable')">取 消</el-button>
              </div>
            </el-dialog>

            <el-dialog :title="opType" :visible.sync="dialogFormVisiblePwd" class='dialog-style'>
                <el-form :model="selectTable" :rules="rules" ref="selectTablePwd">
                    <el-form-item label="登录名:" label-width="120px">
                        <el-input v-model="selectTable.accountName" auto-complete="off" placeholder="登录名" disabled></el-input>
                    </el-form-item>
                    <el-form-item label="密码" label-width="120px" prop="accountPassword">
                      <el-input type="password" v-model.trim="selectTable.accountPassword" placeholder="密码"></el-input>
                    </el-form-item>
                    <el-form-item label="确认密码" label-width="120px" prop="passwordConfirm">
                      <el-input type="password" v-model.trim="selectTable.passwordConfirm" placeholder="确认密码"></el-input>
                    </el-form-item>
                </el-form>
              <div slot="footer" class="dialog-footer">
                <el-button type="primary" @click="submitPwd('selectTablePwd')">确 定</el-button>
                <el-button @click="resetForm('selectTablePwd')">取 消</el-button>
              </div>
            </el-dialog>
        </div>
    </div>
</template>

<script>
    import timeUtil from '../util/timeUtil'
    import {RoleEnum} from '@/config/enum' 
    import md5 from 'js-md5'
    import _ from 'lodash'
    import {listAccounts, createAccount, updateAccount, updateAccountPwd, detailAccount, deleteAccount} from '@/api/getData'

    const defaultValue = {
        validTime: ''
    };
    export default {
        data(){
            return {
                tableData: [],
                formData: {
                },
                selectTable: defaultValue,
                opType: '',
                dialogFormVisible: false,
                dialogFormVisiblePwd : false,
                validRoles: [],
                pickerOptions:  {
                    shortcuts: [{
                        text: '最近六个月',
                        onClick(picker) {
                          const end = new Date();
                          const start = new Date();
                          start.setTime(start.getTime() - 3600 * 1000 * 24 * 180);
                          picker.$emit('pick', [start, end]);
                        }
                      }, {
                        text: '最近一年',
                        onClick(picker) {
                          const end = new Date();
                          const start = new Date();
                          start.setTime(start.getTime() - 3600 * 1000 * 24 * 365);
                          picker.$emit('pick', [start, end]);
                        }
                      }
                    ]
                },
                rules: {
                    accountName: [
                        { required: true, message: '请输入登录名', trigger: 'blur' },
                        { min: 3, max: 20, message: '长度在 3 到 20 个字符', trigger: 'blur' }
                    ],
                    username: [
                        { required: true, message: '请输入姓名', trigger: 'blur' }
                    ],
                    phone: [
                        { required: true, message: '请输入手机', trigger: 'blur' }
                    ],
                    validTime: [
                        { required: true, message: '请选择有效时间', trigger: 'blur' }
                    ],
                    accountPassword: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        { min: 4, max: 20, message: '长度在 4 到 20 个字符', trigger: 'blur' }
                    ],
                    passwordConfirm:[
                        {validator: this.validatePass, trigger: 'blur' }
                    ]
                }
            }
        },
        created() {
            this.initData();
        },
        props:['role'],
    	components: {
    	},
        methods: {
            validatePass: function(rule, value, callback){            
                if (value === '') {
                    callback(new Error('请输入密码'));
                  } else if (value !== this.selectTable.accountPassword) {
                    callback(new Error('两次输入密码不一致!'));
                  } else {
                    callback();
                  }
            },
            loadRoles() {
                this.validRoles = [RoleEnum.prop[RoleEnum.TEACHER]];
                if (this.role) {
                    this.validRoles = [RoleEnum.prop[this.role]];
                }
            },
        	timeFormatter: function(row, col, cellValue) {
        		return timeUtil.utcToMinute(cellValue);
        	},
            reload() {
                this.getAccounts();
            },
            async initData(){
                try{
                	this.getAccounts();
                } catch(err) {
                    console.log('获取数据失败', err);
                }
            },
            async getAccounts(){
            	// TODO id -> current
                const response = await listAccounts({id:2});
                this.tableData = [];
                if (response) {
                    response.result.forEach(item => {
                        const row = {};
                        row.id = item.id;
                        row.accountName = item.accountName;
                        row.username = item.username;
                        row.role = RoleEnum.prop[item.role].text;
                        row.status = item.status;
                        row.beginTime = item.beginTime;
                        row.endTime = item.endTime;
                        row.validTimeCN = timeUtil.toDateCN(item.beginTime) + "-" + timeUtil.toDateCN(item.endTime);
                        this.tableData.push(row);
                    })
                }
            },
            handleAdd() {
                this.dialogFormVisible = true;
                this.opType = '添加账户';
                this.selectTable.validTime = new Array(new Date(), timeUtil.oneYearAfter(new Date()));
                this.selectTable.id = 0; 
                this.loadRoles();                
            },
            handleUpdate(index, row) {
                this.dialogFormVisible = true;
                this.opType = '编辑账户';
                _.assign(this.selectTable, row);
                this.selectTable.validTime = _.clone([this.selectTable.beginTime, this.selectTable.endTime]);
                delete this.selectTable.accountPassword;
                delete this.selectTable.passwordConfirm;
            },
            handleUpdatePwd(index, row) {
                this.dialogFormVisiblePwd = true;
                this.opType = '重置密码';
                this.selectTable.id = row.id;
                this.selectTable.accountName = row.accountName;
            },
            handleDelete(index, row) {
                this.$confirm('确认删除' + row.accountName + '？')
                  .then(_ => {
                    this.handleDelete_inner(index, row);
                  })
                  .catch(_ => {});
            },
            async handleDelete_inner(index, row) {
                try{
                    const res = await deleteAccount({id: row.id});
                    if (res.success === true) {
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
                this.dialogFormVisiblePwd = false;
            },
            async submit(formName) {
                if (!this.validForm(formName)) return;

                this.dialogFormVisible = false;                
                try {
                    let formData = _.assign(this.selectTable);                    
                    formData.beginTime = this.selectTable.validTime[0];
                    formData.endTime = this.selectTable.validTime[1];
                    this.selectTable = defaultValue;

                    var res;
                    if (this.selectTable.id > 0) {
                        delete formData.role;
                        res = await updateAccount(formData);
                    } else {
                        formData.accountPassword = md5(this.selectTable.accountPassword);
                        res = await createAccount(formData);
                    }
                    if (res.success === true) {
                        this.$message({
                            type: 'success',
                            message: '操作成功'
                        });
                        this.reload();
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
            async submitPwd(formName) {
                if (!this.validForm(formName)) return;

                this.dialogFormVisiblePwd = false;
                try{
                    let formData = _.assign(this.selectTable);
                    formData.accountPassword = md5(this.selectTable.accountPassword);
                    this.selectTable = defaultValue;

                    var res = await updateAccountPwd(formData);
                    if (res.success === true) {
                        this.$message({
                            type: 'success',
                            message: '操作成功'
                        });
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
        width : 300px;
        text-align: left;
    }

</style>