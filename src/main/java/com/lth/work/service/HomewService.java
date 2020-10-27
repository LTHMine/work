package com.lth.work.service;

import com.lth.work.pojo.Homew;

import java.util.List;

public interface HomewService {
    Homew findById(Integer id, Integer category);
    void updateHomew(Homew homew);
    List<Homew> findByCate(Integer category);
}
