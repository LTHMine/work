package com.lth.work.service.impl;

import com.lth.work.mapper.StudentMapper;
import com.lth.work.pojo.Student;
import com.lth.work.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentMapper studentMapper;

    @Override
    public Student findById(Integer id) {
        return studentMapper.findById(id);
    }

    @Override
    public Student findByName(String name) {
        return studentMapper.findByName(name);
    }

    @Override
    public List<Student> findAll() {
        return studentMapper.findAll();
    }

    @Override
    public void modifyStatus(Integer id, String work_name) {
        studentMapper.modifyStatus(id,work_name);
    }

    @Override
    public void addStudent(Student student) {
        studentMapper.addStudent(student);
    }

    @Override
    public void delStudent(Integer id) {
        studentMapper.delStudent(id);
    }
}
