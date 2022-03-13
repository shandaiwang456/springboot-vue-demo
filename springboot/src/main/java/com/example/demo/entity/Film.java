package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@TableName("film")
@Data
public class Film {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String filmid;
    private String imdb;
    private String filmname;
    private String director;
    private String actor;
    private String filmtype;
    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    // 用于修改时间格式
    private Date releasetime;
}
