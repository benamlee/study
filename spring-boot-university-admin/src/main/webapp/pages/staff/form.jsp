<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>${empty staff.id ? '添加' : '编辑'}教职工 - Spring Boot 版</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <div class="form-container">
            <h1>${empty staff.id ? '📝 添加教职工' : '✏️ 编辑教职工'}</h1>

            <c:if test="${not empty errors and errors.hasErrors()}">
                <div class="error-msg">
                    <ul>
                        <c:forEach items="${errors.allErrors}" var="err">
                            <li>${err.defaultMessage}</li>
                        </c:forEach>
                    </ul>
                </div>
            </c:if>

            <form action="${pageContext.request.contextPath}/staff/save" method="post">
                <input type="hidden" name="id" value="${staff.id}">

                <div class="form-group">
                    <label for="staffNo">工号</label>
                    <input type="text" id="staffNo" name="staffNo"
                           value="${staff.staffNo}"
                           class="${not empty errors and errors.hasFieldErrors('staffNo') ? 'field-error' : ''}"
                           placeholder="请输入工号，如 T001" required>
                    <c:if test="${not empty errors and errors.hasFieldErrors('staffNo')}">
                        <div class="field-error-msg">${errors.getFieldError('staffNo').defaultMessage}</div>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="name">姓名</label>
                    <input type="text" id="name" name="name"
                           value="${staff.name}"
                           class="${not empty errors and errors.hasFieldErrors('name') ? 'field-error' : ''}"
                           placeholder="请输入姓名" required>
                    <c:if test="${not empty errors and errors.hasFieldErrors('name')}">
                        <div class="field-error-msg">${errors.getFieldError('name').defaultMessage}</div>
                    </c:if>
                </div>

                <div class="form-group">
                    <label for="department">部门</label>
                    <input type="text" id="department" name="department"
                           value="${staff.department}"
                           placeholder="请输入部门，如计算机系">
                </div>

                <div class="form-group">
                    <label for="position">职位</label>
                    <input type="text" id="position" name="position"
                           value="${staff.position}"
                           placeholder="请输入职位，如教授">
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn">💾 保存</button>
                    <a href="${pageContext.request.contextPath}/staff/list" class="btn btn-back">⬅ 返回列表</a>
                </div>
            </form>
        </div>
    </div>
</body>
</html>
