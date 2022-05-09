package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.commen.Result;
import com.example.demo.entity.Admindailyrec;
import com.example.demo.entity.Adminuserlog;
import com.example.demo.entity.Film;
import com.example.demo.mapper.AdminuserlogMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.sql.Wrapper;
import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/adminuserlog")
public class Adminuserlogcontroller {

    @Resource
    AdminuserlogMapper AdminuserlogMapper;

    @PostMapping
    public Result<?> save(@RequestBody Adminuserlog Adminuserlog) {
        // 增 && 查
        System.out.println("[Adminuserlogcontroller]<post> filmid = "+Adminuserlog.getFilmid()+", md5 = "+Adminuserlog.getUsermd5()+", rate = "+Adminuserlog.getRate());
        if (Adminuserlog.getId()==null){
            Adminuserlog.setId((int) (System.currentTimeMillis()%Integer.MAX_VALUE));
        }
        Adminuserlog.setGeneratetime(new Date());
        AdminuserlogMapper.insert(Adminuserlog);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Adminuserlog adminuserlog) {
        // 改
        System.out.println("[Adminuserlogcontroller]<put> filmid = "+adminuserlog.getFilmid()+", md5 = "+adminuserlog.getUsermd5()+", rate = "+adminuserlog.getRate());
        adminuserlog.setGeneratetime(new Date());
        String md5 = adminuserlog.getUsermd5(), fid = adminuserlog.getFilmid();
        LambdaUpdateWrapper w = Wrappers.<Adminuserlog>lambdaUpdate().like(Adminuserlog::getUsermd5, md5).like(Adminuserlog::getFilmid, fid);
        AdminuserlogMapper.update(adminuserlog, w);
        return Result.success();
    }

    @DeleteMapping("/{fid}/{md5}")
    public Result<?> update(@PathVariable String fid,
                            @PathVariable String md5) {
        // 删
        System.out.println("[Adminuserlogcontroller]<put> filmid = "+fid+", md5 = "+md5);
        AdminuserlogMapper.delete(Wrappers.<Adminuserlog>lambdaQuery().like(Adminuserlog::getUsermd5, md5).like(Adminuserlog::getFilmid, fid));
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

    @GetMapping("/{fid}/{md5}")
    public Result<?> get(@PathVariable String fid,
                         @PathVariable String md5) {
        // 载入 load 传参包括：pageNum: this.currentPage,pageSize: this.pageSize,
        System.out.println("[Adminuserlogcontroller]<get> filmid = "+fid+", md5 = "+md5);
        Adminuserlog log = AdminuserlogMapper.selectOne(Wrappers.<Adminuserlog>lambdaQuery().like(Adminuserlog::getUsermd5, md5).like(Adminuserlog::getFilmid, fid));
        if (log != null) { // 已有以往评分
            return Result.success(log);
        }
        else return Result.error("1","null");
    }

}
