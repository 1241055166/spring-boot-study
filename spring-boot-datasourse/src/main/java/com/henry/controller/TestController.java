package com.henry.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.henry.domain.Student;
import com.henry.domain.Teacher;
import com.henry.mapper.StudentMapper;
import com.henry.mapper2.TeacherMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Description:
 * @Author: huangjw-b
 */
@RestController
@Slf4j
public class TestController {

    @Autowired
    StudentMapper studentMapper;

    @Autowired
    TeacherMapper teacherMapper;

    @GetMapping("test")
    @Transactional
    public String test() {
        Student student = new Student();
        student.setSName("alex222");
        int studentRows = studentMapper.insert(student);
        int num = 1 / 0;
        Teacher teacher = new Teacher();
        teacher.setTName("大哥2222");
        int teacherInsert = teacherMapper.insert(teacher);
        log.info("rows:{}, rows2:{}", studentRows, teacherInsert);

        return "ok";
    }

    @GetMapping("list")
    public Object list() {
        return studentMapper.selectList(new LambdaQueryWrapper<>());
    }

    @GetMapping("lis2t")
    public Object list2() {
        return teacherMapper.selectList(new LambdaQueryWrapper<>());
    }
}
