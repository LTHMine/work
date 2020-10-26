package com.lth.work.service;

import com.lth.work.pojo.Twork;

import java.util.List;

public interface TworkService {
    void insert(Twork twork);
    List<Twork> findByStuID(Integer StuID);
    List<Twork> findByName(String name);
    List<Twork> findByCate(Integer category);
    List<Twork> findByidCateHome(Integer stuID,Integer category,Integer workL);
}
