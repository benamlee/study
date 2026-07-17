<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- ========================================== -->
<!-- staff/form.jsp - 教职工表单（添加/编辑）   -->
<!-- ========================================== -->
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>${empty staff ? '添加' : '编辑'}教职工</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h1>${empty staff ? '📝 添加教职工' : '✏️ 编辑教职工'}</h1>

        <form action="${pageContext.request.contextPath}/staff" method="post" class="data-form">
            
            <input type="hidden" name="action" value="${empty staff ? 'create' : 'update'}">
            
            <c:if test="${not empty staff}">
                <input type="hidden" name="id" value="${staff.id}">
            </c:if>

            <div class="form-group">
                <label for="staffNo">工号 *：</label>
                <input type="text" id="staffNo" name="staffNo"
                       value="${staff.staffNo}" required maxlength="20"
                       placeholder="请输入工号">
            </div>

            <div class="form-group">
                <label for="name">姓名 *：</label>
                <input type="text" id="name" name="name"
                       value="${staff.name}" required maxlength="50"
                       placeholder="请输入姓名">
            </div>

            <div class="form-group">
                <label for="department">部门：</label>
                <input type="text" id="department" name="department"
                       value="${staff.department}" maxlength="50"
                       placeholder="如：计算机学院">
            </div>

            <div class="form-group">
                <label for="position">职位：</label>
                <input type="text" id="position" name="position"
                       value="${staff.position}" maxlength="50"
                       placeholder="如：教授">
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-save">💾 保存</button>
                <a href="${pageContext.request.contextPath}/staff/list" class="btn btn-cancel">取消</a>
            </div>
        </form>
    </div>
</body>
</html>
