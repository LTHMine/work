package com.lth.work.service.impl;

import com.lth.work.mapper.TworkMapper;
import com.lth.work.pojo.Twork;
import com.lth.work.service.TworkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TworkServiceImpl implements TworkService {
    @Autowired
    private TworkMapper tworkMapper;

    @Override
    public void insert(Twork twork) {
        tworkMapper.insert(twork);
    }

    @Override
    public List<Twork> findByStuID(Integer StuID) {
        return null;
    }

    @Override
    public List<Twork> findByName(String name) {
        return null;
    }

    @Override
    public List<Twork> findByCate(Integer category) {
        return null;
    }

    @Override
    public List<Twork> findByidCateHome(Integer stuID, Integer category, Integer workL) {
        return tworkMapper.findByidCateHome(stuID,category,workL);
    }
}
