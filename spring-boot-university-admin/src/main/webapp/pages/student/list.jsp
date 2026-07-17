<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- ========================================== -->
<!-- student/list.jsp - 学生列表                 -->
<!-- 和 Spring MVC 版完全一样                   -->
<!-- 因为 JSP 在哪种架构下都是通过 EL 表达式     -->
<!-- ${} 获取 Model 中的数据                    -->
<!-- ========================================== -->
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>学生列表 - Spring Boot 版</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h1>📚 学生列表</h1>

        <div class="toolbar">
            <a href="${pageContext.request.contextPath}/student/add" class="btn btn-add">＋ 添加学生</a>
            <a href="${pageContext.request.contextPath}/" class="btn btn-back">⬅ 返回首页</a>
        </div>

        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>学号</th>
                    <th>姓名</th>
                    <th>性别</th>
                    <th>入学年份</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty students}">
                    <tr>
                        <td colspan="6" class="empty-msg">暂无学生数据，请添加学生</td>
                    </tr>
                </c:if>
                <c:forEach items="${students}" var="s">
                    <tr>
                        <td>${s.id}</td>
                        <td>${s.studentNo}</td>
                        <td>${s.name}</td>
                        <td>${s.gender}</td>
                        <td>${s.enrollYear}</td>
                        <td class="action-cell">
                            <a href="${pageContext.request.contextPath}/student/edit?id=${s.id}" class="btn-sm btn-edit">编辑</a>
                            <a href="${pageContext.request.contextPath}/student/delete?id=${s.id}"
                               class="btn-sm btn-delete"
                               onclick="return confirm('确定要删除学生「${s.name}」吗？')">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <p class="stats">共 ${students.size()} 名学生</p>
    </div>
</body>
</html>
