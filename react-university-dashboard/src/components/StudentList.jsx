// ==========================================
// StudentList.jsx - 学生列表组件
//
// 【学习目标】
//   Session 3: 列表渲染 + 条件渲染 + 搜索过滤
//
// 核心概念：
//   1. .map() = 数组遍历，每项生成一个 JSX 元素
//   2. .filter() = 数组过滤
//   3. key 属性 = React 识别列表项的唯一标识
//
// 对比 Java：
//   JSP: <c:forEach items="${students}" var="s">
//         <tr><td>${s.name}</td></tr>
//        </c:forEach>
//   React: students.map(s => <tr key={s.id}><td>{s.name}</td></tr>)
//
// 区别：
//   JSP：模板引擎在服务器运行，生成 HTML 字符串发给浏览器
//   React：JSX 在浏览器运行，直接用 JS 操作 DOM
// ==========================================

import { useState } from 'react';
import StudentCard from './StudentCard';

function StudentList({ students, onDelete, onEdit }) {
  // ==========================================
  // ★ 搜索关键词状态 ★
  //
  // 每当用户在搜索框输入文字，
  // setSearchTerm 更新关键词，
  // 组件重新渲染，列表自动过滤。
  // ==========================================
  const [searchTerm, setSearchTerm] = useState('');

  // ==========================================
  // ★ 过滤逻辑 ★
  //
  // .filter() 方法：对数组的每个元素执行判断函数
  // 返回 true 的保留，false 的去掉
  //
  // 这里我们用 filter + map 的链式操作：
  //   students
  //     .filter(s => 条件)  ← 先过滤
  //     .map(s => <JSX>)    ← 再映射
  //
  // 对比 Java Stream API：
  //   students.stream()
  //     .filter(s -> s.getName().contains(keyword))
  //     .collect(Collectors.toList());
  // JavaScript 的 .filter().map() 和 Java Stream 很像！
  // ==========================================
  const filteredStudents = students.filter((student) => {
    // 如果没有搜索关键词，显示全部
    if (!searchTerm.trim()) return true;

    const keyword = searchTerm.toLowerCase().trim();
    // toLowerCase() 转为小写，实现大小写不敏感搜索

    return (
      // 在学生姓名、学号、性别中搜索
      student.name.toLowerCase().includes(keyword) ||
      student.studentNo.toLowerCase().includes(keyword) ||
      (student.gender && student.gender.includes(keyword))
    );
  });

  // ==========================================
  // ★ 条件渲染：三种状态 ★
  //
  // 1. 加载中（这里是模拟）
  // 2. 数据为空
  // 3. 搜索无结果
  // 4. 正常列表
  //
  // 一个组件可以有多个 return，根据条件返回不同 JSX。
  // 这比 Java 的 JSTL <c:choose> 更灵活。
  // ==========================================

  // 情况 1：数据为空（students 数组长度为 0）
  if (!students || students.length === 0) {
    return (
      <div className="text-center py-16 text-gray-400">
        <div className="text-5xl mb-4">📭</div>
        <p className="text-lg">暂无学生数据</p>
        <p className="text-sm mt-1">请在顶部添加第一个学生</p>
      </div>
    );
  }

  return (
    <div>
      {/* ========================================== */}
      {/* 搜索栏 */}
      {/* ========================================== */}
      <div className="mb-4">
        <input
          type="text"
          placeholder="🔍 搜索学生（姓名 / 学号 / 性别）..."
          value={searchTerm}
          // value = 输入框的当前值（受控组件）
          onChange={(e) => setSearchTerm(e.target.value)}
          // onChange = 输入内容变化时触发
          // e.target.value = 输入框的当前文本
          className="w-full px-4 py-2.5 border border-gray-200 rounded-lg
                     focus:outline-none focus:ring-2 focus:ring-blue-400
                     focus:border-transparent text-sm"
        />
      </div>

      {/* ========================================== */}
      {/* 情况 2：搜索无结果 */}
      {/* ========================================== */}
      {filteredStudents.length === 0 ? (
        <div className="text-center py-12 text-gray-400">
          <p>未找到匹配 "{searchTerm}" 的学生</p>
        </div>
      ) : (
        <>
          {/* ========================================== */}
          {/* ★ .map() 列表渲染 ★ */}
          {/* ========================================== */}

          {/* 统计信息 */}
          <div className="text-xs text-gray-400 mb-3">
            共 {filteredStudents.length} 名学生
            {searchTerm && `（搜索 "${searchTerm}"）`}
          </div>

          {/* ========================================== */}
          {/* 学生卡片网格 */}
          {/* grid-cols-1 md:grid-cols-2 lg:grid-cols-3 */}
          {/* = 响应式网格：手机上 1 列，平板上 2 列，桌面上 3 列 */}
          {/* ========================================== */}
          <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
            {filteredStudents.map((student) => (
              // ==========================================
              // ★ key 属性（非常重要）★
              //
              // 为什么需要 key？
              //   React 用 key 来识别每个列表项。
              //   当列表变化（增/删/排序）时，
              //   React 通过 key 知道哪些项是新增的，
              //   哪些是删除的，避免重新渲染全部。
              //
              // 规则：
              //   1. key 必须在兄弟元素中唯一
              //   2. 最好用数据的 ID（稳定不变）
              //   3. 不要用数组索引 index（会导致问题）
              //
              // 对比：
              //   JSP/Vue 不需要 key（因为它们是模板引擎）
              //   React 需要 key（因为它用 Virtual DOM diff）
              // ==========================================
              <StudentCard
                key={student.id}
                student={student}
                onDelete={onDelete}
                onEdit={onEdit}
              />
            ))}
          </div>
        </>
      )}
    </div>
  );
}

export default StudentList;
