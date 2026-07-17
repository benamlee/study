<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>学生列表</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h1>📚 学生列表</h1>

        <!-- 操作按钮：添加学生 + 返回首页 -->
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
                <!-- 如果列表为空 -->
                <c:if test="${empty students}">
                    <tr>
                        <td colspan="6" class="empty-msg">暂无学生数据，请添加学生</td>
                    </tr>
                </c:if>

                <!-- 循环显示每个学生 -->
                <c:forEach items="${students}" var="s">
                    <tr>
                        <td>${s.id}</td>
                        <td>${s.studentNo}</td>
                        <td>${s.name}</td>
                        <td>${s.gender}</td>
                        <td>${s.enrollYear}</td>
                        <td class="action-cell">
                            <!-- 编辑按钮：跳转到 /student/edit?id=xxx -->
                            <a href="${pageContext.request.contextPath}/student/edit?id=${s.id}" class="btn-sm btn-edit">编辑</a>
                            <!-- 删除按钮：点击后弹确认框 -->
                            <a href="${pageContext.request.contextPath}/student/delete?id=${s.id}"
                               class="btn-sm btn-delete"
                               onclick="return confirm('确定要删除学生「${s.name}」吗？')">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <!-- 数据统计 -->
        <p class="stats">共 ${students.size()} 名学生</p>
    </div>
</body>
</html>
