package com.lth.work.mapper;

import com.lth.work.pojo.Student;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StudentMapper {
    Student findById(Integer id);
    Student findByName(String name);

    List<Student> findAll();

    void modifyStatus(Integer id,String work_name);
}
