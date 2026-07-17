package com.university.student.session;

import com.university.student.entity.Student;
import java.util.List;

// ==========================================
// StudentService.java - EJB 业务接口
//
// 这个接口定义了"学生管理"模块提供的所有业务功能
// EJB 接口分为两种：
//   @Local  — 本地接口（同一个应用内调用）
//   @Remote — 远程接口（跨服务器调用）
// 我们用的是 @Local，因为 Servlet 和 EJB 在同一个应用
// ==========================================

// @Local 标记这是一个"本地 EJB 接口"
// 接口的实现类（StudentServiceBean）提供具体逻辑
// Servlet 通过 @EJB 注入这个接口，然后调用方法
public interface StudentService {

    /** 添加一名学生到数据库 */
    void addStudent(Student student);

    /** 查询所有学生（按学号降序排列） */
    List<Student> getAllStudents();

    /** 根据 ID 查找单个学生 */
    Student getStudent(Long id);

    /** 更新学生信息 */
    void updateStudent(Student student);

    /** 根据 ID 删除学生 */
    void deleteStudent(Long id);
}
