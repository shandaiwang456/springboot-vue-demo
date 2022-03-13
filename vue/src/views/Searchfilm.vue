<template>
    <!--用户搜索电影栏目-->
    <div style="padding: 10px">
        <div style="width: 400px;margin: 0 auto;background-color: indigo; border-radius:10px">
            <div style="color: #1be620; font-size: 40px; text-align: center;padding: 5px 0;">搜搜看看</div>
        </div>

        <div style="margin: 10px 0 10px 300px">
            <el-select v-model="value" class="m-2" placeholder="Select" size="large">
                <el-option v-for="item in options" :key="item.value" :label="item.label" :value="item.value"></el-option>
            </el-select>

            <el-input v-model="search" placeholder="输入关键字" style="width: 40%;margin-left: 5px" size="large" clearable/>
            <el-button type="primary" style="margin-left: 5px" size="large" @click="load">搜索</el-button>
        </div>

        <!--      表单-->
        <el-table :data="tableData"
                  :header-cell-style="{'text-align':'center'}"
                  :cell-style="{'text-align':'center'}"
                  border
                  stripe
                  style="width: 99%">

            <el-table-column prop="filmname" label="电影名称"/>

            <el-table-column prop="director" label="电影导演"/>
            <el-table-column prop="actor" label="主演" width="300"/>
            <el-table-column prop="filmtype" label="电影类型"/>


            <el-table-column label="选项" width="230">
                <template #default="scope">

                    <el-button size="small" type="text" @click="handleEdit(scope.row)">已看评分</el-button>

                    <el-button size="small" type="text" >稍后再看</el-button>

                    <el-popconfirm title="确认删除吗？" @confirm="handleDelete(scope.row.id)">
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

            <el-dialog v-model="dialogVisible" title="电影信息" width="30%">
                <el-form :model="form" label-width="120px">

                    <el-form-item label="电影名称">
                        <el-input v-model="form.filmname" style="width: 80%"></el-input>
                    </el-form-item>

                    <el-form-item label="IMDb">
                        <el-input v-model="form.imdb" style="width: 80%"></el-input>
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


                    <el-form-item label="发布时间">
                        <el-date-picker v-model="form.releasetime" type="date" format="YYYY/MM/DD" style="width: 80%"></el-date-picker>
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
    import { ref } from 'vue';

    export default {
        name: 'Searchfilm',
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

                tableData: [],
                options :[{value: 'Option1', label: '电影名称',}, {value: 'Option2', label: '导演',},
                    {value: 'Option3', label: '主演',}, {value: 'Option4', label: '电影类型',},],
                value : ref(''),
            }
        },

        created() {
            this.load();

        },

        methods: {
            // 函数
            load() {
                request.get("/film", {
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
                    request.put("/film", this.form).then(res => {
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
                    request.post("/film", this.form).then(res => {
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

                request.delete("/film/" + id).then(res => {

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
