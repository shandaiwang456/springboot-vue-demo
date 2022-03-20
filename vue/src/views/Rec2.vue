<template>
    <!--推荐算法二-->
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
                    <el-button type="primary" @click="check">查看详情</el-button>
                    <el-button type="primary" @click="rate">给它评分</el-button>
                    <el-button type="primary">稍后再看</el-button>
                    <el-button type="danger">我不喜欢</el-button>
                </div>

            </el-carousel-item>

        </el-carousel>


        <el-dialog v-model="dialogVisible1" title="详细信息" width="30%">

            <h3>{{ this.filmname }}</h3>

            <template #footer>
                  <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="save">确 定</el-button>
                  </span>
            </template>
        </el-dialog>

        <el-dialog v-model="dialogVisible2" title="评分" width="30%">

            <h3>{{ this.filmname }}</h3>

            <template #footer>
                  <span class="dialog-footer">
                    <el-button @click="dialogVisible = false">取消</el-button>
                    <el-button type="primary" @click="save">确 定</el-button>
                  </span>
            </template>
        </el-dialog>


    </div>


</template>

<script>


    import image1 from '../assets/imagefiles/11.png'
    import image2 from '../assets/imagefiles/12.png'
    import image3 from '../assets/imagefiles/13.png'
    import image4 from '../assets/imagefiles/14.png'
    import image5 from '../assets/imagefiles/15.png'

    import image6 from '../assets/imagefiles/16.png'
    import image7 from '../assets/imagefiles/17.png'
    import image8 from '../assets/imagefiles/18.png'
    import image9 from '../assets/imagefiles/19.png'
    import image10 from '../assets/imagefiles/20.png'
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

                form01: {},
                dialogVisible1: false,
                dialogVisible2: false,
                filmname: '影片名：功夫',
                form02: {},

            }
        },

        computed: {
            fites(){
                return [{name: this.fname[0], cover: path+this.img[0]}, {name: this.fname[1], cover: path+this.img[1]},
                    {name: this.fname[2], cover: path+this.img[2]}, {name: this.fname[3], cover: path+this.img[3]},
                    {name: this.fname[4], cover: path+this.img[4]}, {name: this.fname[5], cover: path+this.img[5]},
                    {name: this.fname[6], cover: path+this.img[6]}, {name: this.fname[7], cover: path+this.img[7]},
                    {name: this.fname[8], cover: path+this.img[8]}, {name: this.fname[9], cover: path+this.img[9]},];
            }
        },

        methods: {
            check() {
                // this.filmname = key;
                this.dialogVisible1 = true;
                // 清空表单域
                this.form01 = {}

            },
            rate() {
                this.dialogVisible2 = true;
                this.form02 = {}
            },

            load() {
                request.get("/admindailyrec?pageNum=2", {
                    params: {
                        pageNum: this.currentPage,
                        pageSize: this.pageSize,
                        search: this.search
                    }
                }).then(res => {
                    console.log(res.data.size)
                    console.log(res.data)
                    for(var i=0; i<res.data.size; i++){
                        this.img[i] = res.data.records[i].filmid+'.jpeg'
                        this.fname[i] = res.data.records[i].filmname
                        console.log(res.data.records[i].filmname)
                    }
                    this.total = res.data.total;
                })
            },
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











