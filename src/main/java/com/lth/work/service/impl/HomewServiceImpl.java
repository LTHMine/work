package com.lth.work.service.impl;

import com.lth.work.mapper.HomewMapper;
import com.lth.work.pojo.Homew;
import com.lth.work.service.HomewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HomewServiceImpl implements HomewService {
    @Autowired
    private HomewMapper homewMapper;

    @Override
    public Homew findById(Integer id) {
        return homewMapper.findById(id);
    }

    @Override
    public void updateHomew(Homew homew) {
        homewMapper.updateHomew(homew);
    }

    @Override
    public List<Homew> findByCate(Integer category) {
        return homewMapper.findByCate(category);
    }

    @Override
    public List<Homew> findAll() {
        return homewMapper.findAll();
    }
}
