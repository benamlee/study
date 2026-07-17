<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>教职工列表 - Spring Boot 版</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h1>👨‍🏫 教职工列表</h1>

        <div class="toolbar">
            <a href="${pageContext.request.contextPath}/staff/add" class="btn btn-add">＋ 添加教职工</a>
            <a href="${pageContext.request.contextPath}/" class="btn btn-back">⬅ 返回首页</a>
        </div>

        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>工号</th>
                    <th>姓名</th>
                    <th>部门</th>
                    <th>职位</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty staffList}">
                    <tr>
                        <td colspan="6" class="empty-msg">暂无教职工数据，请添加教职工</td>
                    </tr>
                </c:if>
                <c:forEach items="${staffList}" var="s">
                    <tr>
                        <td>${s.id}</td>
                        <td>${s.staffNo}</td>
                        <td>${s.name}</td>
                        <td>${s.department}</td>
                        <td>${s.position}</td>
                        <td class="action-cell">
                            <a href="${pageContext.request.contextPath}/staff/edit?id=${s.id}" class="btn-sm btn-edit">编辑</a>
                            <a href="${pageContext.request.contextPath}/staff/delete?id=${s.id}"
                               class="btn-sm btn-delete"
                               onclick="return confirm('确定要删除教职工「${s.name}」吗？')">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <p class="stats">共 ${staffList.size()} 名教职工</p>
    </div>
</body>
</html>
