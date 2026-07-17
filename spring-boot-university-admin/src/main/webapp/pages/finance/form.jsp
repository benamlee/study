<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- ========================================== -->
<!-- finance/form.jsp - 缴费登记表单            -->
<!--                                             -->
<!-- 注意：金额输入的是"元"，但数据库中存储       -->
<!-- 的是"分"。Controller 中做转换。              -->
<!-- ========================================== -->
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>登记缴费 - Spring Boot 版</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
    <div class="container">
        <div class="form-container">
            <h1>💰 登记缴费</h1>

            <form action="${pageContext.request.contextPath}/finance/save" method="post">
                <div class="form-group">
                    <label for="studentSelect">选择学生</label>
                    <select id="studentSelect" onchange="updateStudentInfo()" required>
                        <option value="">-- 请选择学生 --</option>
                        <c:forEach items="${students}" var="s">
                            <option value="${s.id}" data-no="${s.studentNo}" data-name="${s.name}">
                                ${s.studentNo} - ${s.name}
                            </option>
                        </c:forEach>
                    </select>
                </div>

                <!-- 隐藏字段，由 JavaScript 自动填充 -->
                <input type="hidden" name="studentId" id="studentId">
                <input type="hidden" name="studentNo" id="studentNo">
                <input type="hidden" name="studentName" id="studentName">

                <div class="form-group">
                    <label for="amount">缴费金额（元）</label>
                    <input type="number" id="amount" name="amount"
                           step="0.01" min="0" placeholder="请输入金额，如 5000.00" required>
                    <small style="color:#999;">单位：元（自动转换为分存储）</small>
                </div>

                <div class="form-group">
                    <label for="semester">学期</label>
                    <input type="text" id="semester" name="semester"
                           placeholder="如 2024-2025-1" required>
                </div>

                <div class="form-actions">
                    <button type="submit" class="btn">💾 保存</button>
                    <a href="${pageContext.request.contextPath}/finance/list" class="btn btn-back">⬅ 返回列表</a>
                </div>
            </form>
        </div>
    </div>

    <script>
    // ==========================================
    // 当用户选择学生时，自动填充隐藏字段
    // 这样 Controller 就能通过 @RequestParam
    // 获取 studentId、studentNo、studentName
    // ==========================================
    function updateStudentInfo() {
        var select = document.getElementById('studentSelect');
        var option = select.options[select.selectedIndex];
        document.getElementById('studentId').value = option.value;
        document.getElementById('studentNo').value = option.getAttribute('data-no');
        document.getElementById('studentName').value = option.getAttribute('data-name');
    }
    </script>
</body>
</html>
