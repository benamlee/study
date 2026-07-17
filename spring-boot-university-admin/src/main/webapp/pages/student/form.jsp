<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- ========================================== -->
<!-- student/form.jsp - 学生表单                -->
<!--                                             -->
<!-- ★ 新增功能：表单校验错误提示 ★              -->
<!--                                             -->
// 对比 Spring MVC 版：
//   Spring MVC 版：没有任何表单校验
//   Spring Boot 版：@Valid + BindingResult
//                   自动校验并显示错误消息
// ========================================== -->
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>${empty student.id ? '添加' : '编辑'}学生 - Spring Boot 版</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <div class="form-container">
            <h1>${empty student.id ? '📝 添加学生' : '✏️ 编辑学生'}</h1>

            <!-- ========================================== -->
            <!-- 显示校验错误信息                             -->
            <!-- errors = BindingResult（从 Controller 传入） -->
            <!-- 这是 Spring Boot 版新增的功能                -->
            <!-- ========================================== -->
            <c:if test="${not empty errors and errors.hasErrors()}">
                <div class="error-msg">
                    <ul>
                        <c:forEach items="${errors.allErrors}" var="err">
                            <li>${err.defaultMessage}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/student/save" method="post">
                <!-- 隐藏字段：编辑时传 id，新增时 id 为 null -->
                <input type="hidden" name="id" value="${student.id}">

                <div class="form-group">
                    <label for="studentNo">学号</label>
                    <input type="text" id="studentNo" name="studentNo"
                           value="${student.studentNo}"
                           class="${not empty errors and errors.hasFieldErrors('studentNo') ? 'field-error' : ''}"
                           placeholder="请输入学号，如 2024001" required>
                    <c:if test="${not empty errors and errors.hasFieldErrors('studentNo')}">
                        <div class="field-error-msg">
                            ${errors.getFieldError('studentNo').defaultMessage}
                        </div>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="name">姓名</label>
                    <input type="text" id="name" name="name"
                           value="${student.name}"
                           class="${not empty errors and errors.hasFieldErrors('name') ? 'field-error' : ''}"
                           placeholder="请输入姓名" required>
                    <c:if test="${not empty errors and errors.hasFieldErrors('name')}">
                        <div class="field-error-msg">
                            ${errors.getFieldError('name').defaultMessage}
                        </div>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="gender">性别</label>
                    <select id="gender" name="gender">
                        <option value="男" ${student.gender == '男' ? 'selected' : ''}>男</option>
                        <option value="女" ${student.gender == '女' ? 'selected' : ''}>女</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="enrollYear">入学年份</label>
                    <input type="number" id="enrollYear" name="enrollYear"
                           value="${student.enrollYear}"
                           class="${not empty errors and errors.hasFieldErrors('enrollYear') ? 'field-error' : ''}"
                           placeholder="如 2024" required>
                    <c:if test="${not empty errors and errors.hasFieldErrors('enrollYear')}">
                        <div class="field-error-msg">
                            ${errors.getFieldError('enrollYear').defaultMessage}
                        </div>
                    </c:if>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn">💾 保存</button>
                    <a href="${pageContext.request.contextPath}/student/list" class="btn btn-back">⬅ 返回列表</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
