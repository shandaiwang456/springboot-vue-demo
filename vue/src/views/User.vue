<template>
    <div style="padding: 10px">

        <!--      此部分是：管理员后台系统封--用户管理界面-->
        <div style="margin: 10px 0">
            <el-button type="primary" @click="add">新增</el-button>
            <el-button type="primary">导入</el-button>
            <el-button type="primary">导出</el-button>
        </div>

        <!--      搜索区域-->
        <div style="margin: 10px 0">
            <el-input v-model="search" placeholder="输入关键字" style="width: 20%" clearable/>
            <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
        </div>

        <!--      表单-->
        <el-table :data="tableData"
                  :header-cell-style="{'text-align':'center'}"
                  :cell-style="{'text-align':'center'}"
                  border
                  stripe
                  style="width: 100%">
            <el-table-column prop="id" label="ID" width="70"/>
            <el-table-column prop="usermd5" label="uid" width="280"/>
            <el-table-column prop="username" label="用户名"/>

            <el-table-column prop="phonenum" label="电话号码"/>
            <el-table-column prop="email" label="邮箱"/>
            <el-table-column prop="age" label="年龄" width="70"/>
            <el-table-column prop="sex" label="性别" width="70"/>


            <el-table-column label="选项" width="150">
                <template #default="scope">

                    <el-button size="small" type="text" @click="handleEdit(scope.row)">编辑</el-button>

                    <el-popconfirm title="确认删除吗？" @confirm="handleDelete(scope.row.id)">
                        <template #reference>
                            <el-button size="small" type="danger">删除</el-button>
                        </template>
                    </el-popconfirm>

                </template>
            </el-table-column>
        </el-table>

        <div style="margin: 10px 0">
            <el-pagination
                    v-model:currentPage="currentPage"
                    :page-sizes="[5, 10, 20]"
                    :page-size="pageSize"
                    layout="total, sizes, prev, pager, next, jumper"
                    :total="total"
                    @size-change="handleSizeChange"
                    @current-change="handleCurrentChange">
            </el-pagination>

            <el-dialog v-model="dialogVisible" title="用户信息" width="30%">
                <el-form :model="form" label-width="120px">

                    <el-form-item label="用户uid">
                        <el-input v-model="form.usermd5" style="width: 80%"></el-input>
                    </el-form-item>

                    <el-form-item label="用户名">
                        <el-input v-model="form.username" style="width: 80%"></el-input>
                    </el-form-item>

                    <el-form-item label="密码">
                        <el-input v-model="form.password" style="width: 80%"></el-input>
                    </el-form-item>

                    <el-form-item label="电话号码">
                        <el-input v-model="form.phonenum" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱">
                        <el-input v-model="form.email" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="年龄">
                        <el-input v-model="form.age" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="性别">
                        <el-input v-model="form.sex" style="width: 80%"></el-input>
                    </el-form-item>
                    <el-form-item label="地址">
                        <el-input type="textarea" v-model="form.address" style="width: 80%"></el-input>
                    </el-form-item>
                </el-form>

                <template #footer>
      <span class="dialog-footer">
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="save">确 定</el-button>
      </span>
                </template>
            </el-dialog>

        </div>

    </div>
</template>

<script>


    import request from "../utils/request";

    export default {
        name: 'User',
        components: {},
        data() {
            return {
                // 从后台获取数据
                form: {},
                dialogVisible: false,
                search: '',
                currentPage: 1,
                pageSize: 10,
                total: 0,

                tableData: []
            }
        },

        created() {
            this.load();

        },

        methods: {
            // 函数
            load() {
                request.get("/user", {
                    params: {
                        pageNum: this.currentPage,
                        pageSize: this.pageSize,
                        search: this.search
                    }
                }).then(res => {
                    console.log(res);
                    this.tableData = res.data.records;
                    this.total = res.data.total;
                })
            },
            add() {
                this.dialogVisible = true;
                // 清空表单域
                this.form = {}
            },
            save() {
                if (this.form.id) {
                    //更新
                    request.put("/user", this.form).then(res => {
                        console.log(res);
                        if (res.code === '0') {
                            this.$message.success("更新成功")
                        } else {
                            this.$message({
                                type: "error",
                                message: res.msg
                            })
                        }
                        this.load();
                        //刷新表格数据
                        this.dialogVisible = false
                    })
                } else {
                    // 新增
                    request.post("/user", this.form).then(res => {
                        console.log(res);
                        if (res.code === '0') {
                            this.$message.success("更新成功")
                        } else {
                            this.$message({
                                type: "error",
                                message: res.msg
                            })
                        }
                        this.load();
                        //刷新表格数据
                        this.dialogVisible = false
                    })
                }
            },
            handleEdit(row) {
                this.form = JSON.parse(JSON.stringify(row));
                this.dialogVisible = true

            },
            handleSizeChange(pageSize) {
                // 改变当前页数出发
                this.pageSize = pageSize;
                this.load()

            },
            handleCurrentChange(pageNum) {
                // 改变当前每页个数出发
                this.currentPage = pageNum;
                this.load()

            },
            handleDelete(id) {
                console.log(id);

                request.delete("/user/" + id).then(res => {

                        if (res.code === '0') {
                            this.$message.success("删除成功")
                        } else {
                            this.$message({
                                type: "error",
                                message: res.msg
                            })
                        }
                        this.load()
                    }
                )
            },

        }
    }
</script>
                                                                                                                                                                                                  