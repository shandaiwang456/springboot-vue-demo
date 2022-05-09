<template>
    <div style="padding: 10px">

        <div style="width: 400px;margin: 0 auto;">
            <div style="color: #409EFF; font-size: 30px; text-align: center;padding: 5px 0;">评分记录</div>
        </div>

<!--        <el-row :gutter="12">-->
<!--            <el-col :span="600">-->
<!--                <el-card shadow="always" style="font-style: italic;color: #606266;padding: 0"> 重要提示：操作栏中1~5表示评分；-1表示稍后再看；-2表示不喜欢；您可以将相应的记录进行修改，也可以将相关记录删除，我们的数据库会为您永久保留记录。 </el-card>-->
<!--            </el-col>-->
<!--        </el-row>-->

        <el-table :data="tableData"
                  :header-cell-style="{'text-align':'center'}"
                  :cell-style="{'text-align':'center'}"
                  border
                  stripe
                  style="width: 100%;margin-top: 10px">

            <el-table-column prop="filmid" label="电影ID"/>
            <el-table-column prop="filmname" label="电影名称"/>
            <el-table-column prop="rate" label="评分"/>
            <el-table-column prop="updatetime" label="创建时间"/>

            <el-table-column label="选项" width="200">
                <!--<el-table-column fixed="right" label="选项" width="200">-->

                <template #default="scope">

                    <el-button size="small" type="text" @click="handleEdit(scope.row)">编辑</el-button>

                    <el-popconfirm title="确认删除吗？" @confirm="handleDelete(scope.row)">
                        <template #reference>
                            <el-button size="small" type="danger">删除记录</el-button>
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

            <el-dialog v-model="dialogVisible" title="详细信息" width="30%">
                <el-form :model="form" label-width="120px">
                    <el-form-item label="电影ID">
                        <el-input v-model="form.filmid" style="width: 80%"></el-input>
                    </el-form-item>

                    <el-form-item label="电影名称">
                        <el-input v-model="form.filmname" style="width: 80%"></el-input>
                    </el-form-item>


                    <el-form-item label="评分">
                        <el-input v-model="form.rate" style="width: 80%"></el-input>
                    </el-form-item>

                    <el-form-item label="更新时间">
                        <el-date-picker v-model="form.updatetime" type="date" format="YYYY/MM/DD" style="width: 80%"></el-date-picker>
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
        name: "Userjournal",
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
                md5: '',
                al: {},

                tableData: []
            }
        },

        created() {
            this.load();

        },

        methods: {
            // 函数
            load() {
                this.md5 = localStorage.getItem("usermd5")
                request.get("/rate", {
                    params: {
                        pageNum: this.currentPage,
                        pageSize: this.pageSize,
                        search: this.md5
                        // search:"21a11752558fa7b126e58a2780c08e65"
                        // 暂时折衷方案，写上uid 进行生成 userjournal

                    }
                }).then(res => {
                    console.log(res);
                    console.log(res.data[1])
                    this.tableData = res.data;
                    this.total = res.data[1].total;
                    console.log(res.data[1].total);
                })
            },
            add() {
                this.dialogVisible = true;
                // 清空表单域
                this.form = {}
            },
            save() {
                // if (this.form.id) {
                    //更新
                    // request.get('/adminuserlog/'+this.form.filmid+'/'+localStorage.getItem("usermd5")).then(res1 => { // 获得此前的评分记录
                    //     if (res1.code == '0') { // 有此前的评分记录
                    //
                    //     }
                    // })
                console.log("[userjournal]<save>")
                this.al.filmid = this.form.filmid
                this.al.rate = this.form.rate
                this.al.total = 1
                this.al.usermd5 = localStorage.getItem("usermd5")
                console.log(this.al)
                    request.put("/adminuserlog", this.al).then(res => {
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
                // } else {
                //     // 新增
                //     request.post("/userjournal", this.form).then(res => {
                //         console.log(res);
                //         if (res.code === '0') {
                //             this.$message.success("更新成功")
                //         } else {
                //             this.$message({
                //                 type: "error",
                //                 message: res.msg
                //             })
                //         }
                //         this.load();
                //         //刷新表格数据
                //         this.dialogVisible = false
                //     })
                // }

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
            handleDelete(row) {
                this.form = JSON.parse(JSON.stringify(row));
                // this.al.usermd5 = localStorage.getItem("usermd5")
                var fid = this.form.filmid
                var md5 = localStorage.getItem("usermd5")
                console.log(fid+" / "+md5)
                request.delete("/adminuserlog/"+fid+"/"+md5).then(res => {

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