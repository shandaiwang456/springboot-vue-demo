package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.commen.Result;
import com.example.demo.entity.Film;
import com.example.demo.mapper.FilmMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/film")
public class Filmcontroller {

    @Resource
    FilmMapper FilmMapper;

    @PostMapping
    public Result<?> save(@RequestBody Film Film) {
        // 增 && 查
        FilmMapper.insert(Film);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Film Film) {
        // 改
        FilmMapper.updateById(Film);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        // 删
        FilmMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        // 载入 load 传参包括：pageNum: this.currentPage,pageSize: this.pageSize,
        Page<Film> FilmPage = FilmMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<Film>lambdaQuery().like(Film::getFilmname, search));
        return Result.success(FilmPage);
    }

    @GetMapping("/{id}")
    public Result<?> get(@PathVariable Long id) {
        // 增 && 查
        Film film = FilmMapper.selectById(id);
        return Result.success(film);
    }
}
