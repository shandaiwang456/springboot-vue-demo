<template>
    <div style="width: 100%; height: 100vh ;background-color: indigo;overflow:hidden">
        <div style="width: 400px;margin: 70px auto">
            <div style="color: #cccccc; font-size: 30px; text-align: center;padding: 20px 0">欢迎注册</div>

            <el-form ref="form" :model="form" size="normal">
                <el-form-item >
                    <el-input v-model="form.username" placeholder="请输入用户名"></el-input>
                </el-form-item>

                <el-form-item >
                    <el-input  v-model="form.password" placeholder="请输入密码" show-password></el-input>
                </el-form-item>

                <el-form-item >
                    <el-input  v-model="form.confirm" placeholder="请确认密码" show-password></el-input>
                </el-form-item>

                <el-form-item >
                    <el-input  v-model="form.phonenum" placeholder="请输入手机号"></el-input>
                </el-form-item>

                <el-form-item >
                    <el-input  v-model="form.email" placeholder="请输入邮箱"></el-input>
                </el-form-item>

                <el-form-item >
                    <el-input  v-model="form.age" placeholder="请输入年龄"></el-input>
                </el-form-item>

                <el-form-item >
                    <el-input  v-model="form.sex" placeholder="请输入性别"></el-input>
                </el-form-item>

                <el-form-item >
                    <el-input  v-model="form.address" placeholder="请输入地址"></el-input>
                </el-form-item>

                <el-form-item >
                    <el-button style="width: 100%" type = "primary" @click="register">注  册</el-button>
                </el-form-item>


            </el-form>


        </div>
    </div>
</template>

<script>
import request from "@/utils/request";

    export default {
        name: "Register",
        data() {
            return {
                form:{}
            }
        },
        methods: {
            register() {

                if (this.form.password !== this.form.confirm){
                    this.$message({type: "error", message: '两次密码不一致'});
                    return
                }


                // 向后台发送请求，一般都是 post,把 this.form 发送给后台；request需要导入
                request.post("/user/register",this.form).then(res => {
                        if (res.code === '0') {
                            this.$message.success("注册成功");
                            // 登录成功需要页面跳转
                            this.$router.push("/login");
                            // 后续可以跳转到冷启动界面
                        }

                        else {

                        }
                    }
                    )
            }
        }
    }
</script>

<style scoped>

</style>