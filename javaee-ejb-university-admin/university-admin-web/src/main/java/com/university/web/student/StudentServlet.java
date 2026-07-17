package com.university.web.student;

import com.university.student.entity.Student;
import com.university.student.session.StudentService;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// ==========================================
// StudentServlet.java - 学生管理控制器
//
// Servlet = 服务器端的小程序
// 作用：接收 HTTP 请求，调用 EJB 处理业务，
//       然后跳转到 JSP 页面显示结果
//
// @WebServlet("/student/*") = 配置 URL 路径
// 当用户访问 http://localhost:9080/university-admin/student/xxx
// 时，由这个 Servlet 处理
//
// 工作流程：
// 浏览器请求 → StudentServlet (doGet/doPost)
//    → StudentService (EJB 业务逻辑)
//      → Student (JPA 操作数据库)
//    → 返回结果
//  → 跳转到 JSP 页面显示
// ==========================================

@WebServlet("/student/*")
public class StudentServlet extends HttpServlet {

    // ==========================================
    // @EJB = 注入 EJB（依赖注入）
    // Servlet 容器会自动创建 StudentService 的实例
    // 并赋值给这个变量
    // 我们不需要自己 new，直接调用方法即可
    // ==========================================
    @EJB
    private StudentService studentService;

    // ==========================================
    // doGet：处理 GET 请求
    // GET 请求通常用于：查看列表、显示表单页面
    // ==========================================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 获取请求路径信息，判断用户想做什么
        String pathInfo = request.getPathInfo(); // 如 "/list", "/add"
        String action = request.getParameter("action"); // 或 ?action=xxx

        // 如果没有指定路径，默认显示列表
        if (pathInfo == null || pathInfo.equals("/")) {
            pathInfo = "/list";
        }

        // 根据路径执行不同的操作
        switch (pathInfo) {
            case "/list":
                // 查询所有学生，放进 request 属性，转发到 list.jsp
                request.setAttribute("students", studentService.getAllStudents());
                request.getRequestDispatcher("/pages/student/list.jsp").forward(request, response);
                break;

            case "/add":
                // 显示添加学生的表单页面
                request.getRequestDispatcher("/pages/student/form.jsp").forward(request, response);
                break;

            case "/edit":
                // 编辑学生：先查出该学生数据，回填到表单
                Long editId = Long.parseLong(request.getParameter("id"));
                request.setAttribute("student", studentService.getStudent(editId));
                request.getRequestDispatcher("/pages/student/form.jsp").forward(request, response);
                break;

            case "/delete":
                // 删除学生：根据 ID 删除，然后重定向到列表
                Long deleteId = Long.parseLong(request.getParameter("id"));
                studentService.deleteStudent(deleteId);
                response.sendRedirect(request.getContextPath() + "/student/list");
                break;

            default:
                // 未知路径，重定向到列表
                response.sendRedirect(request.getContextPath() + "/student/list");
        }
    }

    // ==========================================
    // doPost：处理 POST 请求
    // POST 请求通常用于：提交表单（添加/更新）
    // ==========================================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 获取请求参数（来自表单的输入）
        String action = request.getParameter("action");

        // 从表单获取学生信息
        String studentNo = request.getParameter("studentNo");
        String name = request.getParameter("name");
        String gender = request.getParameter("gender");
        String enrollYearStr = request.getParameter("enrollYear");

        // 创建学生对象
        Student student = new Student();
        student.setStudentNo(studentNo);
        student.setName(name);
        student.setGender(gender);
        if (enrollYearStr != null && !enrollYearStr.isEmpty()) {
            student.setEnrollYear(Integer.parseInt(enrollYearStr));
        }

        if ("update".equals(action)) {
            // 更新：设置 ID，调用 updateStudent
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                student.setId(Long.parseLong(idStr));
                studentService.updateStudent(student);
            }
        } else {
            // 添加：调用 addStudent
            studentService.addStudent(student);
        }

        // 操作完成后重定向到列表页面
        // 重定向 = 让浏览器重新请求 /student/list
        response.sendRedirect(request.getContextPath() + "/student/list");
    }
}
