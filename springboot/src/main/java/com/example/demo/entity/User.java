package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@TableName("user")
@Data
public class User {

    @TableId(type = IdType.AUTO)
    private Integer id;
    private String usermd5;
    private String username;
//    private String nickname;
    private String password;
    private String phonenum;
    private String email;
    private Integer age;
    private String sex;
    private String address;
    private String admin;

}

