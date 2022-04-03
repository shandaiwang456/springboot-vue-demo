package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.commen.Result;
import com.example.demo.entity.Admindailyrec;
import com.example.demo.mapper.AdmindailyrecMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/admindailyrec")
public class Admindailyreccontroller {

    @Resource
    AdmindailyrecMapper AdmindailyrecMapper;

    @PostMapping
    public Result<?> save(@RequestBody Admindailyrec Admindailyrec) {
        // 增 && 查
        AdmindailyrecMapper.insert(Admindailyrec);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Admindailyrec Admindailyrec) {
        // 改
        AdmindailyrecMapper.updateById(Admindailyrec);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        // 删
        AdmindailyrecMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search,
                              @RequestParam(defaultValue = "") String algor) {
        // 载入 load 传参包括：pageNum: this.currentPage,pageSize: this.pageSize,
        Page<Admindailyrec> AdmindailyrecPage = AdmindailyrecMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<Admindailyrec>lambdaQuery().like(Admindailyrec::getUsermd5, search).like(Admindailyrec::getAlgor, algor));
        return Result.success(AdmindailyrecPage);
    }

}
