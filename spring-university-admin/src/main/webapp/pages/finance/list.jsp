<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>缴费记录 - Spring 版</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <h1>💰 缴费记录</h1>
        <div class="toolbar">
            <a href="${pageContext.request.contextPath}/finance/add" class="btn btn-add">＋ 登记缴费</a>
            <a href="${pageContext.request.contextPath}/" class="btn btn-back">⬅ 返回首页</a>
        </div>
        <table class="data-table">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>学号</th>
                    <th>学生姓名</th>
                    <th>金额（元）</th>
                    <th>学期</th>
                    <th>缴费日期</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:if test="${empty tuitions}">
                    <tr><td colspan="7" class="empty-msg">暂无缴费记录</td></tr>
                </c:if>
                <c:forEach items="${tuitions}" var="t">
                    <tr>
                        <td>${t.id}</td>
                        <td>${t.studentNo}</td>
                        <td>${t.studentName}</td>
                        <td class="amount">¥ <fmt:formatNumber value="${t.amountYuan}" pattern="#,##0.00"/></td>
                        <td>${t.semester}</td>
                        <td><fmt:formatDate value="${t.paymentDate}" pattern="yyyy-MM-dd"/></td>
                        <td class="action-cell">
                            <a href="${pageContext.request.contextPath}/finance/delete?id=${t.id}"
                               class="btn-sm btn-delete"
                               onclick="return confirm('确定要删除这笔记录吗？')">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
        <p class="stats">共 ${tuitions.size()} 条缴费记录</p>
    </div>
</body>
</html>
