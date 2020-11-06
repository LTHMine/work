package com.lth.work.service;

import com.lth.work.pojo.Category;
import org.springframework.stereotype.Service;

import java.util.List;


public interface CateService {
    List<Category> findAll();
    Category findById(Integer id);

}
