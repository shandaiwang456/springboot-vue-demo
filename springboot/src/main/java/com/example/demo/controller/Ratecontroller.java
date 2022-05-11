package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.commen.Result;
import com.example.demo.entity.Adminuserlog;
import com.example.demo.entity.Film;
import com.example.demo.entity.Userjournal;
import com.example.demo.mapper.AdminuserlogMapper;
import com.example.demo.mapper.FilmMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/rate")
public class Ratecontroller {
    @Resource
    AdminuserlogMapper AdminuserlogMapper;
    @Resource
    FilmMapper filmMapper;

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        // 载入 load 传参包括：pageNum: this.currentPage,pageSize: this.pageSize,
        Page<Adminuserlog> adminuserlogPage = AdminuserlogMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<Adminuserlog>lambdaQuery().like(Adminuserlog::getUsermd5, search));
        List<Adminuserlog> adminuserlogs = adminuserlogPage.getRecords();
        List<Userjournal> userjournals = new ArrayList<>();
        for (int i = 0; i < adminuserlogs.size(); i++){
            Adminuserlog log = adminuserlogs.get(i);
            String fid = log.getFilmid();
            String rate = log.getRate();
            long total = adminuserlogPage.getTotal();
            Date gentime = log.getGeneratetime();

            Userjournal userjournal = new Userjournal();
            userjournal.setFilmid(fid);
            userjournal.setRate(rate);
            userjournal.setUpdatetime(gentime);
            userjournal.setTotal(total);

            //LambdaQueryWrapper<Film> wrapper = Wrappers.<Film>lambdaQuery().like(Film::getFilmid, fid);
            QueryWrapper<Film> wrapper = new QueryWrapper<>();
            wrapper.select("filmname").eq("filmid",fid);
            List<Film> films = filmMapper.selectList(wrapper);
            Film film = filmMapper.selectOne(wrapper);

            String filmname = film.getFilmname();
            userjournal.setFilmname(filmname);

            userjournals.add(userjournal);
        }

        return Result.success(userjournals);
    }
}
