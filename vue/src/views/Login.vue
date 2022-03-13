<template>
    <div style="width: 100%; height: 100vh ;background-color: indigo;overflow:hidden">
        <div style="width: 400px;margin: 150px auto">
            <div style="color: #1ae6d3; font-size: 40px; text-align: center;padding: 20px 0;">电影推荐系统</div>

            <el-form ref="form" :model="form" size="normal">
                <el-form-item prop="username">
                    <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
                </el-form-item>

                <el-form-item prop="password">
                    <el-input v-model="form.password " placeholder="请输入密码" show-password></el-input>
                </el-form-item>

                <el-form-item>
                    <el-button style="width: 100%" type="primary" @click="login">登 录</el-button>
                </el-form-item>

                <el-form-item>
                    <el-button style="width: 100%" type="primary" @click="$router.push('/register')">注 册</el-button>
                </el-form-item>

            </el-form>
        </div>
    </div>
</template>

<script>
    import request from "@/utils/request";

    export default {
        name: "Login",
        data() {
            return {
                form: {},
            }
        },
        methods: {
            login() {
                // 向后台发送请求，一般都是 post,把 this.form 发送给后台；request需要导入
                request.post("/user/login", this.form).then(res => {
                        // 后台需要写/user/login 接口，进入usercontroller
                        if (res.code === '0') {
                            this.$message.success("登录成功");
                            // 登录成功需要页面跳转
                            this.$router.push("/");
                        } else if (res.code === '-2') {
                            // code代码-2 表示管理员
                            this.$message.success("管理员登录成功");
                            // 登录成功需要页面跳转
                            this.$router.push("/admin/user");
                        } else {
                            this.$message({type: "error", message: res.msg})
                        }
                    }
                )
            }
        }
    }
</script>

<style scoped>

</style>