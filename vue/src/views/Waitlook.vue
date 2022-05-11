<template>
    <div style="padding: 10px">

        <!--      功能区域-->
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

        <!--      表格-->
        <el-table :data="tableData"
                  :header-cell-style="{'text-align':'center'}"
                  :cell-style="{'text-align':'center'}"
                  border
                  stripe
                  style="width: 100%">

            <el-table-column prop="filmname" label="电影名称"/>
            <el-table-column prop="director" label="导演"/>
            <el-table-column prop="actor" label="主演" width="230"/>
            <el-table-column prop="filmtype" label="电影类型" width="230"/>
            <el-table-column prop="imdb" label="IMDb"/>
            <el-table-column prop="creattime" label="添加时间"/>

            <el-table-column label="选项" width="230" >
                <template #default="scope">

                    <el-button size="small" type="text" @click="handleEdit(scope.row)">已看评分</el-button>

                    <el-button size="small" type="text" @click="handleEdit1(scope.row)">移出列表</el-button>

                    <el-popconfirm title="确认不喜欢吗？" @confirm="disslike(scope.row)">
                        <template #reference>
                            <el-button size="small" type="text">不喜欢</el-button>
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

                    <el-form-item label="电影名称">
                        <el-input v-model="form.filmname" style="width: 80%"></el-input>
                    </el-form-item>

                    <el-form-item label="导演">
                        <el-input v-model="form.director" style="width: 80%"></el-input>
                    </el-form-item>

                    <el-form-item label="主演">
                        <el-input v-model="form.actor" style="width: 80%"></el-input>
                    </el-form-item>

                    <el-form-item label="电影类型">
                        <el-input v-model="form.filmtype" style="width: 80%"></el-input>
                    </el-form-item>

                    <el-form-item label="IMDb">
                        <el-input v-model="form.imdb" style="width: 80%"></el-input>
                    </el-form-item>

                    <el-form-item label="评分">
                        <el-input v-model="form.fpoint" style="width: 80%"></el-input>
                    </el-form-item>

                    <el-form-item label="添加时间">
                        <el-date-picker v-model="form.creattime" type="date" format="YYYY/MM/DD" style="width: 80%"></el-date-picker>
                    </el-form-item>

                </el-form>

                <template #footer>
                      <span class="dialog-footer">
                        <el-button @click="dialogVisible = false">取消</el-button>
                        <el-button type="primary" @click="already_watch">确 定</el-button>
                      </span>
                </template>
            </el-dialog>

        </div>

        <div style="margin: 10px 0">
            <el-dialog v-model="dialogVisible1" title="是否删除" width="30%">

                <template #footer>
                      <span class="dialog-footer">
                        <el-button @click="dialogVisible1 = false">取消</el-button>
                        <el-button type="primary" @click=this.delete>确 定</el-button>
                      </span>
                </template>
            </el-dialog>

        </div>

    </div>
</template>

<script>


    import request from "../utils/request";

    export default {
        name: 'Waitlook',
        components: {},
        data() {
            return {
                // 从后台获取数据
                form: {},
                dialogVisible: false,
                dialogVisible1: false,
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
                request.get("/waitlook", {
                    params: {
                        pageNum: this.currentPage,
                        pageSize: this.pageSize,
                        search: this.md5
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
            already_watch() {
                // request.get('/film/'+this.form.id).then(res =>{
                //     console.log(res);
                //     // 有无此电影
                //     if (res.code === '0') {
                //         request.get('/adminuserlog/'+res.data.filmid+'/'+localStorage.getItem("usermd5")).then(res1 =>{ // 获得此前的评分记录
                //             // this.al.id = this.form.id
                //             this.al.filmid = res.data.filmid
                //             this.al.rate = this.form.fpoint
                //             this.al.total = 1
                //             this.al.usermd5 = localStorage.getItem("usermd5")
                //             console.log(this.al)
                //             if (res1.code == '0'){ // 有此前的评分记录
                //                 console.log("already has!!!!")
                //                 request.put("/adminuserlog", this.al).then(res => {
                //                     if (res.code === '0') {
                //                         this.$message.success("更新成功")
                //                     } else {
                //                         this.$message({
                //                             type: "error",
                //                             message: res.msg
                //                         })
                //                     }
                //                 })
                //             }else {
                //                 console.log("new!!!!!")
                //                 request.post("/adminuserlog", this.al).then(res => {
                //                     if (res.code === '0') {
                //                         this.$message.success("更新成功")
                //                     } else {
                //                         this.$message({
                //                             type: "error",
                //                             message: res.msg
                //                         })
                //                     }
                //                 })
                //             }
                //         })
                //     } else {
                //         this.$message({
                //             type: "error",
                //             message: res.msg
                //         })
                //     }
                // })
                this.dialogVisible = false
                this.$message.success("更新成功")
            },
            disslike(row) {
                this.form = JSON.parse(JSON.stringify(row));
                this.delete()
                // this.form = JSON.parse(JSON.stringify(row));
                // request.get('/film/'+this.form.id).then(res =>{
                //     console.log(res);
                //     // 有无此电影
                //     if (res.code === '0') {
                //         request.get('/adminuserlog/'+res.data.filmid+'/'+localStorage.getItem("usermd5")).then(res1 =>{ // 获得此前的评分记录
                //             // this.al.id = this.form.id
                //             this.al.filmid = res.data.filmid
                //             this.al.rate = 1
                //             this.al.total = 1
                //             this.al.usermd5 = localStorage.getItem("usermd5")
                //             console.log(this.al)
                //             if (res1.code == '0'){ // 有此前的评分记录
                //                 console.log("already has!!!!")
                //                 request.put("/adminuserlog", this.al).then(res => {
                //                     if (res.code === '0') {
                //                         this.$message.success("更新成功")
                //                     } else {
                //                         this.$message({
                //                             type: "error",
                //                             message: res.msg
                //                         })
                //                     }
                //                 })
                //             }else {
                //                 console.log("new!!!!!")
                //                 request.post("/adminuserlog", this.al).then(res => {
                //                     if (res.code === '0') {
                //                         this.$message.success("更新成功")
                //                     } else {
                //                         this.$message({
                //                             type: "error",
                //                             message: res.msg
                //                         })
                //                     }
                //                 })
                //             }
                //         })
                //     } else {
                //         this.$message({
                //             type: "error",
                //             message: res.msg
                //         })
                //     }
                // })
            },
            // save() {
            //     if (this.form.id) {
            //         //更新
            //         request.put("/waitlook", this.form).then(res => {
            //             console.log(res);
            //             if (res.code === '0') {
            //                 this.$message.success("更新成功")
            //             } else {
            //                 this.$message({
            //                     type: "error",
            //                     message: res.msg
            //                 })
            //             }
            //             this.load();
            //             //刷新表格数据
            //             this.dialogVisible = false
            //         })
            //     } else {
            //         // 新增
            //         request.post("/waitlook", this.form).then(res => {
            //             console.log(res);
            //             if (res.code === '0') {
            //                 this.$message.success("更新成功")
            //             } else {
            //                 this.$message({
            //                     type: "error",
            //                     message: res.msg
            //                 })
            //             }
            //             this.load();
            //             //刷新表格数据
            //             this.dialogVisible = false
            //         })
            //     }
            //
            // },
            delete(){
                console.log(this.form.id)
                request.delete("/waitlook/"+this.form.id).then(res => {
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
                    this.dialogVisible1 = false
                })
            },
            handleEdit(row) {
                this.form = JSON.parse(JSON.stringify(row));
                this.dialogVisible = true

            },
            handleEdit1(row) {
                this.form = JSON.parse(JSON.stringify(row));
                this.dialogVisible1 = true

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

                request.delete("/waitlook" + id).then(res => {

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
