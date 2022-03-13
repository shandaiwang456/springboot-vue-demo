package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.commen.Result;
import com.example.demo.entity.Adminuserlog;
import com.example.demo.mapper.AdminuserlogMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/adminuserlog")
public class Adminuserlogcontroller {

    @Resource
    AdminuserlogMapper AdminuserlogMapper;

    @PostMapping
    public Result<?> save(@RequestBody Adminuserlog Adminuserlog) {
        // 增 && 查
        AdminuserlogMapper.insert(Adminuserlog);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Adminuserlog Adminuserlog) {
        // 改
        AdminuserlogMapper.updateById(Adminuserlog);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        // 删
        AdminuserlogMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        // 载入 load 传参包括：pageNum: this.currentPage,pageSize: this.pageSize,
        Page<Adminuserlog> AdminuserlogPage = AdminuserlogMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<Adminuserlog>lambdaQuery().like(Adminuserlog::getUsermd5, search));
        return Result.success(AdminuserlogPage);
    }

}
