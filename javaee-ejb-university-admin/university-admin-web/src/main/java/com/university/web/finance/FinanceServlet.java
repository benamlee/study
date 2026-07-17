package com.university.web.finance;

import com.university.finance.entity.Tuition;
import com.university.finance.session.FinanceService;
import com.university.student.session.StudentService;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

// ==========================================
// FinanceServlet.java - 财务管理控制器
//
// 处理学费缴费记录的查看、添加、删除
// ==========================================

@WebServlet("/finance/*")
public class FinanceServlet extends HttpServlet {

    @EJB
    private FinanceService financeService;

    // 需要查询学生列表，用于缴费时选择学生
    @EJB
    private StudentService studentService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            pathInfo = "/list";
        }

        switch (pathInfo) {
            case "/list":
                // 查询所有缴费记录，转发到 list.jsp
                request.setAttribute("tuitions", financeService.getAllTuitions());
                request.getRequestDispatcher("/pages/finance/list.jsp").forward(request, response);
                break;

            case "/add":
                // 显示添加缴费表单
                // 同时传入学生列表，让用户选择缴费学生
                request.setAttribute("students", studentService.getAllStudents());
                request.getRequestDispatcher("/pages/finance/form.jsp").forward(request, response);
                break;

            case "/delete":
                // 删除缴费记录
                Long deleteId = Long.parseLong(request.getParameter("id"));
                financeService.deleteTuition(deleteId);
                response.sendRedirect(request.getContextPath() + "/finance/list");
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/finance/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 获取表单参数
        String studentIdStr = request.getParameter("studentId");
        String amountStr = request.getParameter("amount");
        String semester = request.getParameter("semester");
        String studentNo = request.getParameter("studentNo");
        String studentName = request.getParameter("studentName");

        // 创建 Tuition 对象
        Tuition tuition = new Tuition();
        if (studentIdStr != null && !studentIdStr.isEmpty()) {
            tuition.setStudentId(Long.parseLong(studentIdStr));
        }
        tuition.setStudentNo(studentNo);
        tuition.setStudentName(studentName);
        // amount 输入的是"元"，需要转换为"分"
        if (amountStr != null && !amountStr.isEmpty()) {
            double yuan = Double.parseDouble(amountStr);
            tuition.setAmount((int) (yuan * 100));
        }
        tuition.setSemester(semester);
        tuition.setPaymentDate(new Date()); // 当前时间为缴费日期

        // 保存
        financeService.addTuition(tuition);

        // 重定向到列表
        response.sendRedirect(request.getContextPath() + "/finance/list");
    }
}
