package com.lth.work.service;

import com.lth.work.pojo.Student;

import java.util.List;

public interface StudentService {
    Student findById(Integer id);
    Student findByName(String name);
    List<Student> findAll();
    void modifyStatus(Integer id,String work_name);
}
