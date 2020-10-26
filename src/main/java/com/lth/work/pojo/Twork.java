package com.lth.work.pojo;

import lombok.Data;

@Data
public class Twork {
    private Integer id; //主键，无实意
    private String workPath; //作业路径
    private String workName; //作业名字
    private Integer stuID;  //上传者学号
    private Integer category; //分类
    private Integer homework_id; //第几次作业
    private String beiz;  //备注

}
