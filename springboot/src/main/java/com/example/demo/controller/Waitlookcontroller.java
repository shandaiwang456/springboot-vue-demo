package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.commen.Result;
import com.example.demo.entity.Waitlook;
import com.example.demo.mapper.WaitlookMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/waitlook")
public class Waitlookcontroller {

    @Resource
    WaitlookMapper WaitlookMapper;

    @PostMapping
    public Result<?> save(@RequestBody Waitlook Waitlook) {
        // 增 && 查
        WaitlookMapper.insert(Waitlook);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Waitlook Waitlook) {
        // 改
        WaitlookMapper.updateById(Waitlook);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        // 删
        WaitlookMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        // 载入 load 传参包括：pageNum: this.currentPage,pageSize: this.pageSize,
        Page<Waitlook> WaitlookPage = WaitlookMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<Waitlook>lambdaQuery().like(Waitlook::getFilmname, search));
        return Result.success(WaitlookPage);
    }

}
