<template>
    <div>
        <el-menu
                style="width: 200px; min-height: calc(100vh - 50px)"
                :default-active="path"
                router
                class="el-menu-vertical-demo">
                <!--此处是Aside 如果有router 下面的index 就直接跳转到相关路由-->

            <el-sub-menu index="recommendation">
                <template #title>电影推荐</template>
                <el-menu-item index="/rec1">MFL推荐算法</el-menu-item>
                <el-menu-item index="/rec2">DBM推荐算法</el-menu-item>
            </el-sub-menu>

            <el-menu-item index="/searchfilm">
                <template #title>搜索电影</template>
            </el-menu-item>

            <el-menu-item index="/waitlook">
                <template #title>稍后再看</template>
            </el-menu-item>

            <el-menu-item index="/userjournal">
                <template #title>评分记录</template>
            </el-menu-item>

            <el-menu-item index="/userroom">
                <template #title>个人空间</template>
            </el-menu-item>


        </el-menu>
    </div>
</template>

<script>
    export default {
        name: "Aside",
        data() {
            return {
                // 返回参数 设置默认高亮的菜单
                path: this.$route.path
            }
        },
        created() {
            // 渲染界面执行函数
        }
    }

</script>

<style scoped>

</style>