<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<!-- ========================================== -->
<!-- index.jsp - 系统首页                       -->
<!-- 显示三个管理模块的导航链接                  -->
<!-- ========================================== -->
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>大学行政管理系统 - 首页</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <!-- 页面标题 -->
        <header>
            <h1>🎓 大学行政管理系统</h1>
            <p class="subtitle">Java EE + EJB + WebSphere 综合示例</p>
        </header>

        <!-- ========================================== -->
        <!-- 三个管理模块的导航卡片                       -->
        <!-- ========================================== -->
        <div class="card-grid">

            <!-- 学生管理卡片 -->
            <div class="card card-student">
                <h2>📚 学生管理</h2>
                <p>管理学生信息：注册、查询、编辑、删除学生档案</p>
                <ul>
                    <li>学生注册登记</li>
                    <li>学籍信息管理</li>
                    <li>学生档案列表</li>
                </ul>
                <a href="${pageContext.request.contextPath}/student/list" class="btn">进入学生管理 →</a>
            </div>

            <!-- 教职工管理卡片 -->
            <div class="card card-staff">
                <h2>👨‍🏫 教职工管理</h2>
                <p>管理教职工信息：添加、查询、编辑、删除教职工档案</p>
                <ul>
                    <li>教职工信息登记</li>
                    <li>部门信息管理</li>
                    <li>教职工档案列表</li>
                </ul>
                <a href="${pageContext.request.contextPath}/staff/list" class="btn">进入教职工管理 →</a>
            </div>

            <!-- 财务管理卡片 -->
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

        <!-- 系统信息 -->
        <footer>
            <p>技术栈：Java EE 8 + EJB 3.2 + JPA 2.2 + Servlet 4.0 + JSP 2.3</p>
            <p>服务器：IBM WebSphere Liberty 26.0.0.6 | 数据库：H2 Memory</p>
        </footer>
    </div>
</body>
</html>
