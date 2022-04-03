package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/* 新建接口流程： （复制粘贴命名的时候，注意名字替换）
    1.首先数据库建表 Book;
    2.新建 entity 注意一一对应；注意引入 @TableName("book")
    3.修改 mapper 复制粘贴就行，注意改一下实体类
    4.最后修改 controller Ctrl + F 全选替换全部

*/
@TableName("userjournal")
@Data
public class Userjournal {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String uid;
    private String filmid;
    private String filmname;
    private String rate;
    private long total;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    // 用于修改时间格式
    private Date updatetime;

}

