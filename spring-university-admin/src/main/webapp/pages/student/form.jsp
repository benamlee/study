<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- ========================================== -->
<!-- student/form.jsp - 学生表单（Spring 版）   -->
<!-- 对比 Java EE 版：表单 action 改为 save     -->
<!-- Java EE: form action="/student"            -->
<!-- Spring:  form action="/student/save"       -->
<!-- ========================================== -->
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>${empty student ? '添加' : '编辑'}学生 - Spring 版</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h1>${empty student ? '📝 添加学生' : '✏️ 编辑学生'}</h1>

        <!--
          Spring MVC 的 @ModelAttribute 会自动绑定表单参数
          action="/student/save" 对应 @PostMapping("/save")
        -->
        <form action="${pageContext.request.contextPath}/student/save" method="post" class="data-form">

            <c:if test="${not empty student}">
                <input type="hidden" name="id" value="${student.id}">
            </c:if>

            <div class="form-group">
                <label for="studentNo">学号 *：</label>
                <input type="text" id="studentNo" name="studentNo"
                       value="${student.studentNo}" required maxlength="20"
                       placeholder="请输入学号">
            </div>

            <div class="form-group">
                <label for="name">姓名 *：</label>
                <input type="text" id="name" name="name"
                       value="${student.name}" required maxlength="50"
                       placeholder="请输入姓名">
            </div>

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

            <div class="form-group">
                <label for="enrollYear">入学年份：</label>
                <input type="number" id="enrollYear" name="enrollYear"
                       value="${student.enrollYear}" min="2000" max="2030">
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-save">💾 保存</button>
                <a href="${pageContext.request.contextPath}/student/list" class="btn btn-cancel">取消</a>
            </div>
        </form>
    </div>
</body>
</html>
