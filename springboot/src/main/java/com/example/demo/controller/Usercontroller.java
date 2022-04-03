package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.commen.Result;
import com.example.demo.entity.User;
import com.example.demo.mapper.UserMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Wrapper;

@RestController
@RequestMapping("/user")
public class Usercontroller {

    @Resource
    UserMapper userMapper;

    @PostMapping
    public Result<?> save(@RequestBody User user) {
        // 增 && 查

        if (user.getPassword() == null) {
            user.setPassword("123456");
        }
        userMapper.insert(user);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody User user) {
        // 改
        userMapper.updateById(user);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        // 删
        userMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        // 载入 load 传参包括：pageNum: this.currentPage,pageSize: this.pageSize,
        Page<User> userPage = userMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<User>lambdaQuery().like(User::getUsername, search));
        return Result.success(userPage);
    }


    @PostMapping("/login")
    //接口名称是 user/login 但最前面写了user 此处直接写 "/login" 方法名是login
    public Result<?> login(@RequestBody User user) {
        // RequestBody 用 User 进行接收 form 表单里面包含了用户名和密码 解析成User

        // userMapper 查询user 传入参数 Wrappers sql 查询
         User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()).eq(User::getPassword, user.getPassword()));
//        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        // 暂时只需要用用户名登录，密码以后再说，密码上一条
        if (res == null) {
            return Result.error("-1", "用户名或密码错误");
        }

        if (res.getUsername().equals("管理员")) {
            return Result.error("-2", "管理员登录");
        }

        return Result.success(res);
        // 返回的 res.code 是 0
    }

    @PostMapping("/register")
    //接口名称是 user/login 但最前面写了user 此处直接写 "/login" 方法名是login
    public Result<?> register(@RequestBody User user) {

        User res = userMapper.selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, user.getUsername()));
        // 查找没有相同的用户名，才可以注册

        if (res != null) {
            return Result.error("-1", "用户名重复");
        }

//        if (res.getUsername().equals("管理员")){
//            return Result.error("-2","管理员登录");
//        }

        if (user.getPassword() == null) {
            user.setPassword("123456");
        }

        userMapper.insert(user);

        return Result.success();
        // 返回的 res.code 是 0
    }
}
