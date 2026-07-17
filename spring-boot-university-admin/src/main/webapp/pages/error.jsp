<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- ========================================== -->
<!-- error.jsp - 全局错误页面                    -->
<!--                                             -->
<!-- 当 GlobalExceptionHandler 捕获异常时，      -->
<!-- 返回这个页面显示错误信息。                  -->
<!--                                             -->
<!-- 这是 Spring Boot 版新增的页面。             -->
<!-- Java EE 版和 Spring MVC 版没有统一的        -->
<!-- 错误处理页面。                              -->
<!-- ========================================== -->
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>系统错误 - Spring Boot 版</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container" style="text-align:center; padding-top:60px;">
        <h1 style="font-size:4em; color:#e53e3e;">⚠️</h1>
        <h2 style="color:#e53e3e; margin-bottom:20px;">系统发生错误</h2>

        <div style="background:#fff5f5; border:1px solid #fed7d7; border-radius:12px; padding:20px; max-width:600px; margin:0 auto;">
            <p style="color:#c53030; font-size:1.1em;">
                ${errorMessage}
            </p>
            <c:if test="${not empty errorDetail}">
                <p style="color:#888; font-size:0.85em; margin-top:10px;">
                    错误类型：${errorDetail}
                </p>
            </c:if>
        </div>

        <div style="margin-top:30px;">
            <a href="${pageContext.request.contextPath}/" class="btn">⬅ 返回首页</a>
            <button onclick="history.back()" class="btn btn-back">⬅ 返回上一页</button>
        </div>

        <p style="color:#aaa; margin-top:30px; font-size:0.85em;">
            对比学习：这是三个版本中唯一一个拥有全局错误处理页面的版本。
            Java EE 版和 Spring MVC 版在出现错误时会直接显示 HTTP 500 错误页面。
        </p>
    </div>
</body>
</html>
