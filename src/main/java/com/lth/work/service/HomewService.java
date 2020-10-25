package com.lth.work.service;

import com.lth.work.pojo.Homew;

public interface HomewService {
    Homew findById(Integer id, Integer category);
    void updateHomew(Homew homew);
}
