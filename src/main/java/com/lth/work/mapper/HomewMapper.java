package com.lth.work.mapper;

import com.lth.work.pojo.Homew;
import org.springframework.stereotype.Repository;

@Repository
public interface HomewMapper {
    Homew findById(Integer id,Integer category);
    void updateHomew(Homew homew);
}
