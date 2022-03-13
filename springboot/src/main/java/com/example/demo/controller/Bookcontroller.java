package com.example.demo.controller;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.commen.Result;
import com.example.demo.entity.Book;
import com.example.demo.mapper.BookMapper;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/book")
public class Bookcontroller {

    @Resource
    BookMapper BookMapper;

    @PostMapping
    public Result<?> save(@RequestBody Book Book) {
        // 增 && 查
        BookMapper.insert(Book);
        return Result.success();
    }

    @PutMapping
    public Result<?> update(@RequestBody Book Book) {
        // 改
        BookMapper.updateById(Book);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result<?> update(@PathVariable Long id) {
        // 删
        BookMapper.deleteById(id);
        return Result.success();
    }

    @GetMapping
    public Result<?> findPage(@RequestParam(defaultValue = "1") Integer pageNum,
                              @RequestParam(defaultValue = "10") Integer pageSize,
                              @RequestParam(defaultValue = "") String search) {
        // 载入 load 传参包括：pageNum: this.currentPage,pageSize: this.pageSize,
        Page<Book> BookPage = BookMapper.selectPage(new Page<>(pageNum, pageSize), Wrappers.<Book>lambdaQuery().like(Book::getName, search));
        return Result.success(BookPage);
    }

}
