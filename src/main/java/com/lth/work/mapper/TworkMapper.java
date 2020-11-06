package com.lth.work.mapper;

import com.lth.work.pojo.Twork;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TworkMapper {
    void insert(Twork twork);
    List<Twork> findByStuID(Integer StuID);
    List<Twork> findByName(String name);
    List<Twork> findByCate(Integer category);

    List<Twork> findAll();
    List<Twork> findPage();

    List<Twork> findByidCateHome(Integer stuID,Integer category,Integer workL);

}
