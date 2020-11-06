package com.lth.work.mapper;


import com.lth.work.pojo.Category;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CateMapper {
    List<Category> findAll();
    Category findById(Integer id);
}
