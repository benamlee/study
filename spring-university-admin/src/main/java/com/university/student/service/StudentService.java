package com.university.student.service;

import com.university.student.entity.Student;
import java.util.List;

// ==========================================
// StudentService.java - 学生业务接口
//
// 和 Java EE 版的接口完全一样
// 只是包名从 session 改为 service
//（Spring 的习惯叫 service，EJB 的习惯叫 session）
// ==========================================
public interface StudentService {
    void addStudent(Student student);
    List<Student> getAllStudents();
    Student getStudent(Long id);
    void updateStudent(Student student);
    void deleteStudent(Long id);
}
