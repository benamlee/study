package com.university.student.controller;

import com.university.student.entity.Student;
import com.university.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

// ==========================================
// StudentController.java - Spring MVC 控制器
//
// 对比 Java EE 版本 (StudentServlet.java):
//
//   Java EE: @WebServlet("/student/*")
//   Spring:  @Controller + @RequestMapping("/student")
//
//   Java EE: doGet() / doPost() 手动判断路径
//   Spring:  @GetMapping / @PostMapping 自动分发
//
//   Java EE: 手动 request.getRequestDispatcher().forward()
//   Spring:  返回视图名字符串，框架自动转发
//
//   Java EE: @EJB 注入 Service
//   Spring:  @Autowired 注入 Service
//
// Spring MVC 让控制器代码更简洁、更专注。
// ==========================================

@Controller
// @Controller = 标记为 Spring MVC 的控制器
// Spring 会自动扫描并注册

@RequestMapping("/student")
// @RequestMapping("/student") = 这个控制器的
// 所有方法的 URL 都以 /student 开头
// 替代 @WebServlet("/student/*")
public class StudentController {

    @Autowired
    // @Autowired = 自动注入 Spring 容器中的 Bean
    // 替代 Java EE 的 @EJB
    private StudentService studentService;

    // ==========================================
    // 查询所有学生 → 显示列表页
    //
    // Java EE 版：doGet() 中判断 "/list"
    //   request.setAttribute("students", ...)
    //   request.getRequestDispatcher("list.jsp").forward(...)
    //
    // Spring 版：返回 "student/list" 字符串
    //   Spring 通过 ViewResolver 自动拼装
    //   前缀 /pages/ + "student/list" + 后缀 .jsp
    //   = /pages/student/list.jsp
    //
    // Model model = 用来传递数据到 JSP
    // 相当于 request.setAttribute()
    // ==========================================
    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "student/list";
    }

    // ==========================================
    // 显示添加学生表单
    // ==========================================
    @GetMapping("/add")
    public String addForm() {
        return "student/form";
    }

    // ==========================================
    // 显示编辑学生表单（回填数据）
    // @RequestParam Long id = 从 URL 获取参数
    // 如 /student/edit?id=3 获取到 id=3
    // ==========================================
    @GetMapping("/edit")
    public String editForm(@RequestParam Long id, Model model) {
        model.addAttribute("student", studentService.getStudent(id));
        return "student/form";
    }

    // ==========================================
    // 删除学生 → 重定向到列表
    // "redirect:/student/list" = 让浏览器跳转
    // 相当于 response.sendRedirect()
    // ==========================================
    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        studentService.deleteStudent(id);
        return "redirect:/student/list";
    }

    // ==========================================
    // 保存学生（新增或更新）
    //
    // @ModelAttribute Student student = 
    //   自动把表单参数绑定到 Student 对象
    //   表单的 name 属性 = 对象的属性名
    //   Spring 自动调用 setter 方法
    //
    // 对比 Java EE 版：
    //   String name = request.getParameter("name");
    //   Student s = new Student();
    //   s.setName(name);
    //   ...
    // Spring 自动完成了这些工作！
    // ==========================================
    @PostMapping("/save")
    public String save(@ModelAttribute Student student) {
        if (student.getId() == null) {
            studentService.addStudent(student);
        } else {
            studentService.updateStudent(student);
        }
        return "redirect:/student/list";
    }
}
