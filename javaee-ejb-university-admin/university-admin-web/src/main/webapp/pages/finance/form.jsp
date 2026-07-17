<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<!-- ========================================== -->
<!-- finance/form.jsp - 登记缴费表单            -->
<!-- 用户选择学生、输入金额、选择学期，提交后   -->
<!-- 生成一条缴费记录                           -->
<!-- ========================================== -->
<html lang="zh-CN">
<head>
    <meta charset="UTF-8">
    <title>登记缴费</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <script>
        // JavaScript：当用户选择学生时，自动填充学号和姓名
        function onStudentSelect() {
            var select = document.getElementById("studentSelect");
            var selectedOption = select.options[select.selectedIndex];

            // 获取选中学生的 data-* 属性
            var studentNo = selectedOption.getAttribute("data-studentNo");
            var studentName = selectedOption.getAttribute("data-name");

            // 填充到隐藏字段
            if (studentNo) {
                document.getElementById("studentNo").value = studentNo;
                document.getElementById("studentName").value = studentName;
            }
        }
    </script>
</head>
<body>
    <div class="container">
        <h1>📝 登记缴费</h1>

        <form action="${pageContext.request.contextPath}/finance" method="post" class="data-form">
            
            <!-- 固定 action=create（缴费只有添加功能） -->
            <input type="hidden" name="action" value="create">

            <!-- ========================================== -->
            <!-- 选择学生：从所有学生列表中选择              -->
            <!-- 学生在 Servlet 中通过                      -->
            <!-- request.setAttribute("students", ...) 传入 -->
            <!-- ========================================== -->
            <div class="form-group">
                <label for="studentSelect">选择学生 *：</label>
                <select id="studentSelect" onchange="onStudentSelect()" required>
                    <option value="">— 请选择学生 —</option>
                    <!--
                      遍历所有学生
                      data-studentNo="..." 是 HTML5 自定义属性
                      用于在 JavaScript 中获取数据
                    -->
                    <c:forEach items="${students}" var="s">
                        <option value="${s.id}"
                                data-studentNo="${s.studentNo}"
                                data-name="${s.name}">
                            ${s.studentNo} - ${s.name}
                        </option>
                    </c:forEach>
                </select>
            </div>

            <!-- 隐藏字段：存储学生ID、学号、姓名 -->
            <input type="hidden" id="studentId" name="studentId">
            <input type="hidden" id="studentNo" name="studentNo">
            <input type="hidden" id="studentName" name="studentName">

            <script>
                // 更新学生ID和隐藏字段
                document.getElementById("studentSelect").addEventListener("change", function() {
                    document.getElementById("studentId").value = this.value;
                });
            </script>

            <!-- 缴费金额（元） -->
            <div class="form-group">
                <label for="amount">缴费金额（元）*：</label>
                <input type="number" id="amount" name="amount" required
                       min="0" step="0.01" placeholder="如：5000.00">
                <small>请输入金额，单位：元</small>
            </div>

            <!-- 学期 -->
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
