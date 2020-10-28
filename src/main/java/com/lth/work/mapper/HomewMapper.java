package com.lth.work.mapper;

import com.lth.work.pojo.Homew;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomewMapper {
    Homew findById(Integer id,Integer category);
    void updateHomew(Homew homew);
    List<Homew> findByCate(Integer category);
    List<Homew> findAll();
}
