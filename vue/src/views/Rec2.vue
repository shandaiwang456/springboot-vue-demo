<template>
    <!--推荐算法一-->
    <div style="padding: 10px">

        <div style="margin:0;font-size: larger;text-align: left;font-weight: bolder">
            今日影片推荐
        </div>

        <div style="margin: 10px 0">
            <el-input v-model="search" placeholder="想要查找的电影" style="width: 20%" clearable/>
            <el-button type="primary" style="margin-left: 5px" @click="load">查询</el-button>
        </div>

        <el-carousel :interval="4000" type="card" height="500px">

            <el-carousel-item v-for="item in fites" :key="item">

                <div style="text-align: center;height: 25px;">
                    {{ item.name }}
                    <!-- 此部分放电影名称和导演  background-color: fuchsia-->
                    <!--                    {{ this.filmname = item.name }}-->
                </div>

                <div style="height: 440px;text-align: center;margin: 5px">
                    <el-image style="width: 320px;height: 440px" :src=item.cover></el-image>
                </div>

                <div style="height: 25px;text-align: center;">
                    <!--                    background-color: #cccccc-->
                    <el-button type="primary" @click="check(item)">查看详情</el-button>
                    <el-button type="primary" @click="show(item)">给它评分</el-button>
                    <el-button type="primary">稍后再看</el-button>
                    <el-button type="danger" @click="dislike(item)">我不喜欢</el-button>
                </div>

            </el-carousel-item>

        </el-carousel>


        <el-dialog v-model="dialogVisible1" title="详细信息" width="30%">

            <h3>{{ this.filmname }}</h3>

            <template #footer>
                  <span class="dialog-footer">
                    <el-button @click="dialogVisible1 = false">取消</el-button>
                    <el-button type="primary" @click="save">确 定</el-button>
                  </span>
            </template>
        </el-dialog>

        <el-dialog v-model="dialogVisible_rate" title="评分" width="30%">

            <!--            <h3>{{ this.filmname }}</h3>-->
            <!--            <h3>{{img[0]}}</h3>-->
            <el-form :model="form_rate" label-width="120px">


                <el-form-item label="分数">
                    <el-input v-model="form_rate.point" style="width: 80%"></el-input>
                </el-form-item>

            </el-form>

            <template #footer>
                  <span class="dialog-footer">
                    <el-button @click="dialogVisible_rate = false">取消</el-button>
                    <el-button type="primary" @click="rate">确 定</el-button>
                  </span>
            </template>
        </el-dialog>


    </div>


</template>

<script>


    import image1 from '../assets/imagefiles/1.png'
    import image2 from '../assets/imagefiles/2.png'
    import image3 from '../assets/imagefiles/3.png'
    import image4 from '../assets/imagefiles/4.png'
    import image5 from '../assets/imagefiles/5.png'

    import image6 from '../assets/imagefiles/6.png'
    import image7 from '../assets/imagefiles/7.png'
    import image8 from '../assets/imagefiles/8.png'
    import image9 from '../assets/imagefiles/9.png'
    import image10 from '../assets/imagefiles/10.png'
    import request from "../utils/request";


    const path = "http://localhost:9090/pic/"

    export default {
        name: "Rec2",

        created() {
            this.load();
        },

        data() {
            return {

                currentPage: 1,
                pageSize: 10,
                total: 0,
                img: [],
                fname:[],
                algor: 1,
                md5: localStorage.getItem("usermd5"),
                fid: 1,
                ffid: [],


                dialogVisible: false,
                dialogVisible1: false,
                dialogVisible_rate: false,
                filmname: '影片名：功夫',
                form_rate: {filmid:1, usermd5:localStorage.getItem("usermd5"),},
                form02: {},
                form: {},
                al: {},

            }
        },

        computed: {
            fites(){
                return [{name: this.fname[0], cover: path+this.img[0], fid:this.ffid[0],}, {name: this.fname[1], cover: path+this.img[1], fid:this.ffid[0],},
                    {name: this.fname[2], cover: path+this.img[2], fid:this.ffid[2],}, {name: this.fname[3], cover: path+this.img[3], fid:this.ffid[3],},
                    {name: this.fname[4], cover: path+this.img[4], fid:this.ffid[4],}, {name: this.fname[5], cover: path+this.img[5], fid:this.ffid[5],},
                    {name: this.fname[6], cover: path+this.img[6], fid:this.ffid[6],}, {name: this.fname[7], cover: path+this.img[7], fid:this.ffid[7],},
                    {name: this.fname[8], cover: path+this.img[8], fid:this.ffid[8],}, {name: this.fname[9], cover: path+this.img[9], fid:this.ffid[9],},];
            },
        },

        watch:{
        },

        methods: {
            rate() {
                if (this.form_rate.point) {
                    console.log("fid = "+this.fid)
                    console.log("point = "+this.form_rate.point)
                    this.al.filmid = this.fid
                    this.al.usermd5 = this.md5
                    this.al.rate = this.form_rate.point

                    request.get('/adminuserlog/'+this.fid+'/'+this.md5).then(res1 =>{ // 获得此前的评分记录
                        console.log(this.al)
                        if (res1.code == '0'){ // 有此前的评分记录
                            console.log("already has!!!!")
                            request.put("/adminuserlog", this.al).then(res => {
                                if (res.code === '0') {
                                    this.$message.success("更新成功")
                                } else {
                                    this.$message({
                                        type: "error",
                                        message: res.msg
                                    })
                                }
                                this.form_rate = {}
                                this.dialogVisible_rate = false;
                            })
                        }else {
                            console.log("new!!!!!")
                            request.post("/adminuserlog", this.al).then(res => {
                                if (res.code === '0') {
                                    this.$message.success("更新成功")
                                } else {
                                    this.$message({
                                        type: "error",
                                        message: res.msg
                                    })
                                }
                                this.form_rate = {}
                                this.dialogVisible_rate = false;
                            })
                        }
                    })
                }
            },

            dislike(item) {
                this.al.filmid = item.fid
                this.al.usermd5 = this.md5
                this.al.rate = 1
                console.log("fid = "+this.fid)

                request.get('/adminuserlog/'+item.fid+'/'+this.md5).then(res1 =>{ // 获得此前的评分记录
                    console.log(this.al)
                    if (res1.code == '0'){ // 有此前的评分记录
                        console.log("already has!!!!")
                        request.put("/adminuserlog", this.al).then(res => {
                            if (res.code === '0') {
                                this.$message.success("更新成功")
                            } else {
                                this.$message({
                                    type: "error",
                                    message: res.msg
                                })
                            }
                            this.form_rate = {}
                            this.dialogVisible_rate = false;
                        })
                    }else {
                        console.log("new!!!!!")
                        request.post("/adminuserlog", this.al).then(res => {
                            if (res.code === '0') {
                                this.$message.success("更新成功")
                            } else {
                                this.$message({
                                    type: "error",
                                    message: res.msg
                                })
                            }
                            this.form_rate = {}
                            this.dialogVisible_rate = false;
                        })
                    }
                })
            },

            addwaitlook(item){
                // item.fid
                this.form = JSON.parse(JSON.stringify(row));
                this.wl.filmname = this.form.filmname
                this.wl.imdb = this.form.imdb
                this.wl.director = this.form.director
                this.wl.actor = this.form.actor
                this.wl.filmtype = this.form.filmtype
                this.wl.useruid = localStorage.getItem("usermd5")
                request.post('/waitlook',this.wl).then(res =>{
                    if (res.code === '0') {
                        this.$message.success("更新成功")
                    } else {
                        this.$message({
                            type: "error",
                            message: res.msg
                        })
                    }
                })

            },

            check(ii){
                // this.filmname = key;
                this.dialogVisible1 = true;
                // 清空表单域
                // this.fid = ii;
                console.log("ii = "+ii)
                console.log(ii.fid+", "+ii.name)
                console.log("check()")
                this.check2()
            },
            check2(item){
                console.log(item)
                console.log(item.name)
                this.form01.filmid = this.fid
                return this.fid
            },
            load() {
                this.md5 = localStorage.getItem("usermd5")
                request.get("/admindailyrec", {
                    params: {
                        pageNum: this.currentPage,
                        pageSize: this.pageSize,
                        search: this.md5,
                        algor: 2
                    }
                }).then(res => {
                    for(var i=0; i<res.data.size; i++){
                        this.img[i] = res.data.records[i].filmid+'.jpeg'
                        this.fname[i] = res.data.records[i].filmname
                        this.ffid[i] = res.data.records[i].filmid
                    }
                    this.total = res.data.total;
                })
            },
            handleEdit(row) {
                this.form = JSON.parse(JSON.stringify(row));
                this.dialogVisible = true

            },
            show(item){
                this.dialogVisible_rate = true;
                this.fid = item.fid
            },
            item_index(){

            }
        }
    }
</script>

<style scoped>
    .el-carousel__item h3 {
        color: #475669;
        font-size: 14px;
        opacity: 0.75;
        line-height: 200px;
        margin: 0;
        text-align: center;
    }

    .el-carousel__item:nth-child(2n) {
        /*background-color: #48ff76;*/

    }

    .el-carousel__item:nth-child(2n + 1) {
        /*background-color: #ff235e;*/

    }


</style>
