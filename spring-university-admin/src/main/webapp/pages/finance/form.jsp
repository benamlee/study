<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>登记缴费 - Spring 版</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script>
        function onStudentSelect() {
            var select = document.getElementById("studentSelect");
            var opt = select.options[select.selectedIndex];
            document.getElementById("studentNo").value = opt.getAttribute("data-no");
            document.getElementById("studentName").value = opt.getAttribute("data-name");
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>📝 登记缴费</h1>

        <form action="${pageContext.request.contextPath}/finance/save" method="post" class="data-form">

            <div class="form-group">
                <label for="studentSelect">选择学生 *：</label>
                <select id="studentSelect" name="studentId" onchange="onStudentSelect()" required>
                    <option value="">— 请选择 —</option>
                    <c:forEach items="${students}" var="s">
                        <option value="${s.id}"
                                data-no="${s.studentNo}"
                                data-name="${s.name}">
                            ${s.studentNo} - ${s.name}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <input type="hidden" id="studentNo" name="studentNo">
            <input type="hidden" id="studentName" name="studentName">

            <div class="form-group">
                <label for="amount">金额（元）*：</label>
                <input type="number" id="amount" name="amount" required min="0" step="0.01">
            </div>

            <div class="form-group">
                <label for="semester">学期：</label>
                <select id="semester" name="semester">
                    <option value="2024-2025-1">2024-2025 第一学期</option>
                    <option value="2024-2025-2">2024-2025 第二学期</option>
                    <option value="2025-2026-1" selected>2025-2026 第一学期</option>
                    <option value="2025-2026-2">2025-2026 第二学期</option>
                </select>
            </div>

            <div class="form-actions">
                <button type="submit" class="btn btn-save">💾 保存缴费记录</button>
                <a href="${pageContext.request.contextPath}/finance/list" class="btn btn-cancel">取消</a>
            </div>
        </form>
    </div>
</body>
</html>
