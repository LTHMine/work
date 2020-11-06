package com.lth.work.service.impl;

import com.lth.work.mapper.CateMapper;
import com.lth.work.pojo.Category;
import com.lth.work.service.CateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CateServiceImpl implements CateService {

    @Autowired
    private CateMapper cateMapper;

    @Override
    public List<Category> findAll() {
        return cateMapper.findAll();
    }

    @Override
    public Category findById(Integer id) {
        return cateMapper.findById(id);
    }
}
