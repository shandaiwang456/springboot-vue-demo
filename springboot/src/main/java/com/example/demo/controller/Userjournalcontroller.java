package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.commen.Result;
import com.example.demo.entity.Userjournal;
import com.example.demo.mapper.UserjournalMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/userjournal")
public class Userjournalcontroller {

    @Resource
    UserjournalMapper UserjournalMapper;

    @PostMapping
    public Result<?> save(@RequestBody Userjournal Userjournal) {
        // 增 && 查
        UserjournalMapper.insert(Userjournal);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Userjournal Userjournal) {
        // 改
        UserjournalMapper.updateById(Userjournal);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        // 删
        UserjournalMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        // 载入 load 传参包括：pageNum: this.currentPage,pageSize: this.pageSize,
        Page<Userjournal> UserjournalPage = UserjournalMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<Userjournal>lambdaQuery().like(Userjournal::getUid, search));
        return Result.success(UserjournalPage);
    }

}
