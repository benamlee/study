<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- ========================================== -->
<!-- index.jsp - 系统首页（Spring Boot 版）    -->
<!--                                             -->
<!-- 功能与 Spring MVC 版完全相同               -->
<!-- 主要变化：                                 -->
<!--   1. 标题改为 "Spring Boot 版"              -->
<!--   2. 底部技术栈更新                         -->
<!--   3. URL 地址带 context-path                -->
<!-- ========================================== -->
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>大学行政管理系统 - Spring Boot 版</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <header>
            <h1>🎓 大学行政管理系统</h1>
            <p class="subtitle">Spring Boot + Spring Data JPA + Hibernate + Tomcat 版本</p>
        </header>

        <div class="card-grid">
            <div class="card card-student">
                <h2>📚 学生管理</h2>
                <p>管理学生信息：注册、查询、编辑、删除</p>
                <ul>
                    <li>学生注册登记</li>
                    <li>学籍信息管理</li>
                    <li>学生档案列表</li>
                </ul>
                <a href="${pageContext.request.contextPath}/student/list" class="btn">进入学生管理 →</a>
            </div>

            <div class="card card-staff">
                <h2>👨‍🏫 教职工管理</h2>
                <p>管理教职工信息：添加、查询、编辑、删除</p>
                <ul>
                    <li>教职工信息登记</li>
                    <li>部门信息管理</li>
                    <li>教职工档案列表</li>
                </ul>
                <a href="${pageContext.request.contextPath}/staff/list" class="btn">进入教职工管理 →</a>
            </div>

            <div class="card card-finance">
                <h2>💰 财务管理</h2>
                <p>管理学费缴费记录：登记缴费、查询缴费情况</p>
                <ul>
                    <li>学费缴费登记</li>
                    <li>缴费记录查询</li>
                    <li>学生缴费明细</li>
                </ul>
                <a href="${pageContext.request.contextPath}/finance/list" class="btn">进入财务管理 →</a>
            </div>
        </div>

        <footer>
            <p>
                对比三个版本：
                <a href="#">Spring Boot 版（当前）</a> |
                <a href="http://localhost:8080/spring-university-admin/">Spring MVC 版</a> |
                <a href="http://localhost:9080/university-admin/">Java EE 版</a>
            </p>
            <p>技术栈：Spring Boot 2.7 + Spring Data JPA + Hibernate 5.6 + JPA 2.2 + Lombok + H2</p>
        </footer>
    </div>
</body>
</html>
