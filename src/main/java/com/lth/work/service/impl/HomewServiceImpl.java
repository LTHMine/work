package com.lth.work.service.impl;

import com.lth.work.mapper.HomewMapper;
import com.lth.work.pojo.Homew;
import com.lth.work.service.HomewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HomewServiceImpl implements HomewService {
    @Autowired
    private HomewMapper homewMapper;

    @Override
    public Homew findById(Integer id, Integer category) {
        return homewMapper.findById(id,category);
    }

    @Override
    public void updateHomew(Homew homew) {
        homewMapper.updateHomew(homew);
    }
}
