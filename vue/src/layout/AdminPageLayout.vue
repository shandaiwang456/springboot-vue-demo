<template>
<!--    AdminPageLayout是自己写的框架-->
    <div>
        <!--        自定的头部-->
        <AdminHeader/>

        <!--        主体-->
        <div style="display: flex">
            <!--  侧边栏-->
            <AdminAside/>

            <!--  内容区域-->
            <router-view style="flex:1px"/>

        </div>
    </div>
</template>

<script>
    // 需要引用组件 在components文件夹中
    import AdminHeader from "@/components/AdminHeader";
    import AdminAside from "@/components/AdminAside";

    export default {
        name: "AdminPageLayout",
        // 本框架命名
        // 引入的组件
        components: {
            AdminHeader,
            AdminAside
        }
    }
</script>

<style scoped>

</style>