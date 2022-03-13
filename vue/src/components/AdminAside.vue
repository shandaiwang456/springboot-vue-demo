<template>
    <div>
        <el-menu
                style="width: 200px; min-height: calc(100vh - 50px)"
                :default-active="path"
                router
                class="el-menu-vertical-demo">
            <!-- 此处是AdminAside 如果有router 此时已经进入 /admin 下面的index 就直接在/admin 下面的children 里面找 -->
            <!-- 如果不写 / 则直接在 children 里面找，也可以直接输入路径 如 /user 跳转到用户界面 -->


            <el-menu-item index="/admin/user">
                <!--index ="/admin/film" 可以直接跳转到管理员电影管理-->
                <template #title>用户管理</template>
            </el-menu-item>

            <el-menu-item index="/admin/film">
                <template #title>电影管理</template>
            </el-menu-item>

            <el-menu-item index="/admin/adminrec">
                <template #title>推荐列表</template>
            </el-menu-item>

            <el-menu-item index="/admin/adminrecord">
                <template #title>用户记录</template>
            </el-menu-item>



        </el-menu>
    </div>
</template>

<script>
    export default {
        name: "AdminAside",
        data() {
            return {
                // 返回参数 设置默认高亮的菜单
                path: this.$route.path
            }
        },
    }
</script>

<style scoped>

</style>