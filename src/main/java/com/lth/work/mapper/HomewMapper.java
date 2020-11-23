package com.lth.work.mapper;

import com.lth.work.pojo.Homew;
import com.lth.work.pojo.Twork;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HomewMapper {
    Homew findById(Integer id);
    void updateHomew(Homew homew);
    List<Homew> findByCate(Integer category);
    List<Homew> findAll();
    List<Homew> findPage( @Param("sort") String sort);
    List<Homew> findByCatePost(Integer category);
    List<Homew> findByUse();
    void insertHomew(Homew homew);
}
