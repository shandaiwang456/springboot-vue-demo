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

            <el-carousel-item v-for="item in fits" :key="item">

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


    export default {
        name: "Rec2",
        data() {
            return {

                fits: [{name: '落水狗', cover: image1}, {name: '被解救的姜戈', cover: image2},
                    {name: '偷拐抢骗', cover: image3}, {name: '买凶拍人', cover: image4},
                    {name: '无耻混蛋', cover: image5}, {name: '鬼子来了', cover: image6},
                    {name: '窃听风暴', cover: image7}, {name: '飞越疯人院', cover: image8},
                    {name: '十二怒汉', cover: image9}, {name: '肖申克的救赎', cover: image10},],
                form01: {},
                dialogVisible1: false,
                dialogVisible2: false,
                filmname: '影片名：功夫',
                form02: {},

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











