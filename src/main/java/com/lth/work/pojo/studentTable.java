package com.lth.work.pojo;

import lombok.Data;

import java.util.List;

@Data
public class studentTable {
        private Integer code;
        private String msg;
        private Integer count;
        private List<Student> data;
}
