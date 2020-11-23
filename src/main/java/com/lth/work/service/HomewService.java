package com.lth.work.service;

import com.lth.work.pojo.Homew;
import com.lth.work.util.PageRequest;
import com.lth.work.util.PageResult;

import java.util.List;

public interface HomewService {
    Homew findById(Integer id);
    void updateHomew(Homew homew);
    List<Homew> findByCate(Integer category);
    List<Homew> findAll();

    PageResult findPage(PageRequest pageRequest,String sort);
    List<Homew> findByCatePost(Integer category);
    void insertHomew(Homew homew);
    List<Homew> findByUse();
}
