package com.university.student.service;

import com.university.student.entity.Student;
import java.util.List;

// ==========================================
// StudentService.java - 学生业务接口
//
// 这个接口三个版本都有：
//   Java EE 版：package com.university.student.session
//   Spring MVC 版：package com.university.student.service
//   Spring Boot 版：package com.university.student.service（同左）
//
// 为什么要有接口？
//   Java EE 中：EJB 要求 @Stateless bean 必须实现接口，
//              因为 EJB 容器通过代理进行远程调用（RMI）
//   Spring 中：不需要接口也能工作，
//             但保留接口有利于代码解耦和单元测试。
// ==========================================
public interface StudentService {
    List<Student> getAllStudents();
    Student getStudent(Long id);
    Student addStudent(Student student);
    Student updateStudent(Student student);
    void deleteStudent(Long id);
}
