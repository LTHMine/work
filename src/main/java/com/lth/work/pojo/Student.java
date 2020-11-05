package com.lth.work.pojo;

import lombok.Data;

@Data
public class Student {
    private Integer id; //学号
    private String name;
    private String password;
    private Integer python_status;
    private Integer web_status;
    private Integer net_status;

}

