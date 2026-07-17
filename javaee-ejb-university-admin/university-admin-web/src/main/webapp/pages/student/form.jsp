<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- ========================================== -->
<!-- student/form.jsp - 学生表单（添加/编辑）   -->
<!--
  这个页面同时用于"添加"和"编辑"两种场景
  如果 ${student} 不为空 → 编辑模式
  如果 ${student} 为空 → 添加模式
  
  编辑模式：表单回填已有数据，提交时 action=update
  添加模式：表单空白，提交时 action=create
-->
<!-- ========================================== -->
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>${empty student ? '添加' : '编辑'}学生</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <!-- 标题：根据是否传入 student 显示不同标题 -->
        <h1>${empty student ? '📝 添加学生' : '✏️ 编辑学生'}</h1>

        <!-- ========================================== -->
        <!-- 表单：提交到 /student（POST 方法）          -->
        <!-- 表单中的 name 属性对应 Servlet 中          -->
        <!-- request.getParameter("xxx") 的参数名        -->
        <!-- ========================================== -->
        <form action="${pageContext.request.contextPath}/student" method="post" class="data-form">
            
            <!-- 隐藏字段：告诉 Servlet 是添加还是更新 -->
            <input type="hidden" name="action" value="${empty student ? 'create' : 'update'}">
            
            <!-- 编辑模式时，传入学生 ID -->
            <c:if test="${not empty student}">
                <input type="hidden" name="id" value="${student.id}">
            </c:if>

            <!-- 学号输入框 -->
            <div class="form-group">
                <label for="studentNo">学号 *：</label>
                <input type="text" id="studentNo" name="studentNo"
                       value="${student.studentNo}" required maxlength="20"
                       placeholder="请输入学号（如 2024001）">
            </div>

            <!-- 姓名输入框 -->
            <div class="form-group">
                <label for="name">姓名 *：</label>
                <input type="text" id="name" name="name"
                       value="${student.name}" required maxlength="50"
                       placeholder="请输入学生姓名">
            </div>

            <!-- 性别：单选框 -->
            <div class="form-group">
                <label>性别：</label>
                <label class="radio-label">
                    <input type="radio" name="gender" value="男"
                           ${student.gender == '男' ? 'checked' : ''}> 男
                </label>
                <label class="radio-label">
                    <input type="radio" name="gender" value="女"
                           ${student.gender == '女' ? 'checked' : ''}> 女
                </label>
            </div>

            <!-- 入学年份：数字输入框 -->
            <div class="form-group">
                <label for="enrollYear">入学年份：</label>
                <input type="number" id="enrollYear" name="enrollYear"
                       value="${student.enrollYear}" min="2000" max="2030"
                       placeholder="如 2024">
            </div>

            <!-- 按钮区域 -->
            <div class="form-actions">
                <button type="submit" class="btn btn-save">💾 保存</button>
                <a href="${pageContext.request.contextPath}/student/list" class="btn btn-cancel">取消</a>
            </div>
        </form>
    </div>
</body>
</html>
