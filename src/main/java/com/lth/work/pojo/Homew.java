package com.lth.work.pojo;

import lombok.Data;

@Data
public class Homew {
    private Integer id;
    private String homework;
    private String category;
    private String home_status;
    private String home_path;
    private Integer easy;
    private Integer normal;
    private Integer difficulty;
    private String uploadDate;
    private String uploadName;
}
