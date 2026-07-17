package com.university.student.controller;

// ==========================================
// StudentController.java - Spring Boot 版控制器
//
// ★ 对比三个版本的控制器 ★
//
//   Java EE 版 (StudentServlet.java)：
//     @WebServlet("/student/*")
//     doGet() 中手动判断路径：
//       if (action.equals("list")) ...
//       else if (action.equals("add")) ...
//     request.getRequestDispatcher("...").forward(...)
//
//   Spring MVC 版 (StudentController.java)：
//     @Controller
//     @RequestMapping("/student")
//     @GetMapping("/list"), @PostMapping("/save")
//     每个方法对应一个 URL，自动分发
//     @Autowired 字段注入
//
//   Spring Boot 版（本文件）：
//     @Controller + @RequestMapping
//     @GetMapping / @PostMapping（同 Spring MVC）
//     @Valid + BindingResult → 自动校验参数
//     @RequiredArgsConstructor → 构造器注入
//     @Slf4j → 专业日志
// ==========================================

import com.university.student.entity.Student;
import com.university.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/student")
@RequiredArgsConstructor
// @RequiredArgsConstructor = 生成带参构造函数
// final 字段 studentService 作为构造参数
// Spring 自动注入

@Slf4j
// @Slf4j = 自动生成 Logger
// 用法：log.info("...") / log.warn("...")
public class StudentController {

    private final StudentService studentService;
    // 构造器注入（不是 @Autowired 字段注入）
    //
    // 对比：
    //   Spring MVC 版：@Autowired private StudentService studentService;
    //   Spring Boot 版：private final StudentService studentService;
    //
    // 好处：
    //   1. 依赖明确（所有依赖都在构造函数中）
    //   2. 不能为 null（final 必须初始化）
    //   3. 测试容易（直接 new 对象传参）

    @GetMapping("/list")
    public String list(Model model) {
        log.info("查询学生列表");
        model.addAttribute("students", studentService.getAllStudents());
        return "student/list";
        // = ViewResolver 自动拼装:
        //   prefix(/pages/) + "student/list" + suffix(.jsp)
        //   = /pages/student/list.jsp
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        // 传入一个空的学生对象，让 form.jsp 可以绑定字段
        model.addAttribute("student", new Student());
        return "student/form";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam Long id, Model model) {
        log.debug("编辑学生 ID: {}", id);
        Student student = studentService.getStudent(id);
        if (student == null) {
            log.warn("学生不存在 ID: {}", id);
            return "redirect:/student/list";
        }
        model.addAttribute("student", student);
        return "student/form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        log.info("删除学生 ID: {}", id);
        studentService.deleteStudent(id);
        return "redirect:/student/list";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Student student,
                       BindingResult result, Model model) {
        // ==========================================
        // ★ @Valid = 自动校验实体上的 @NotBlank @NotNull
        //
        // 如果校验失败（如学号为空），
        // BindingResult 会包含错误信息，
        // 直接返回表单页面显示错误提示。
        //
        // 对比 Spring MVC 版：
        //   没有任何校验，用户提交空数据也能保存
        //
        // 这就是 Spring Boot 的 "开箱即用" 优势
        // ==========================================
        if (result.hasErrors()) {
            model.addAttribute("errors", result);
            return "student/form";
        }

        if (student.getId() == null) {
            log.info("新增学生: {}", student.getStudentNo());
            studentService.addStudent(student);
        } else {
            log.info("更新学生 ID: {}", student.getId());
            studentService.updateStudent(student);
        }
        return "redirect:/student/list";
    }
}
