package com.university.web.staff;

import com.university.staff.entity.Staff;
import com.university.staff.session.StaffService;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// ==========================================
// StaffServlet.java - 教职工管理控制器
//
// 结构和 StudentServlet 完全一样
// 只是操作的是 Staff（教职工）数据
// ==========================================

@WebServlet("/staff/*")
public class StaffServlet extends HttpServlet {

    @EJB
    private StaffService staffService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            pathInfo = "/list";
        }

        switch (pathInfo) {
            case "/list":
                // 查询所有教职工，转发到 list.jsp
                request.setAttribute("staffList", staffService.getAllStaff());
                request.getRequestDispatcher("/pages/staff/list.jsp").forward(request, response);
                break;

            case "/add":
                // 显示添加表单
                request.getRequestDispatcher("/pages/staff/form.jsp").forward(request, response);
                break;

            case "/edit":
                // 编辑：查出数据回填到表单
                Long editId = Long.parseLong(request.getParameter("id"));
                request.setAttribute("staff", staffService.getStaff(editId));
                request.getRequestDispatcher("/pages/staff/form.jsp").forward(request, response);
                break;

            case "/delete":
                // 删除
                Long deleteId = Long.parseLong(request.getParameter("id"));
                staffService.deleteStaff(deleteId);
                response.sendRedirect(request.getContextPath() + "/staff/list");
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/staff/list");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        // 从表单获取教职工信息
        String staffNo = request.getParameter("staffNo");
        String name = request.getParameter("name");
        String department = request.getParameter("department");
        String position = request.getParameter("position");

        // 创建 Staff 对象
        Staff staff = new Staff();
        staff.setStaffNo(staffNo);
        staff.setName(name);
        staff.setDepartment(department);
        staff.setPosition(position);

        if ("update".equals(action)) {
            // 更新
            String idStr = request.getParameter("id");
            if (idStr != null && !idStr.isEmpty()) {
                staff.setId(Long.parseLong(idStr));
                staffService.updateStaff(staff);
            }
        } else {
            // 添加
            staffService.addStaff(staff);
        }

        response.sendRedirect(request.getContextPath() + "/staff/list");
    }
}
