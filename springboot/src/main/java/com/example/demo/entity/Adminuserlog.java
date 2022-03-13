package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@TableName("adminuserlog")
@Data
public class Adminuserlog {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String usermd5;
    private String filmid;
    private String rate;

    @JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8")
    // 用于修改时间格式
    private Date generatetime;
}
