package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Book;
import com.example.demo.entity.Film;

public interface FilmMapper extends BaseMapper<Film> {
    // 本页唯一需要修改的就是 <Book> 实体
}
