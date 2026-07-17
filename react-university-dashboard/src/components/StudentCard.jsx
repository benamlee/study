// ==========================================
// StudentCard.jsx - 学生卡片组件
//
// 【学习目标】
//   Session 1: Props + 组件组合
//   Session 2: useState + 事件处理
//
// 这是 React 中最核心的概念展示：
//   1. Props = 从父组件传入的数据（只读）
//   2. State = 组件内部管理的数据（可变）
//   3. Event = 用户交互触发的函数
//
// 对比 Java：
//   Java 类：字段（数据）+ 方法（行为）
//   React 组件：State（数据）+ 事件函数（行为）
//   Props 相当于方法参数
// ==========================================

// 引入 useState Hook
// Hook 是 React 的函数式"钩子"，让函数组件拥有状态。
// useState 是最基础的 Hook，用来管理组件的本地状态。
import { useState } from 'react';

// ==========================================
// 组件定义 = 一个返回 JSX 的函数
//
// 参数 props = 父组件传入的所有属性
// 例如 <StudentCard student={s} onDelete={fn} />
// props = { student: s, onDelete: fn }
// ==========================================
function StudentCard({ student, onDelete, onEdit }) {
  // ==========================================
  // ★ useState Hook ★
  //
  // useState(initialValue) 返回一个数组：
  //   [当前值, 更新函数]
  //
  // const [expanded, setExpanded] = useState(false)
  //   expanded    = 当前值（初始为 false）
  //   setExpanded = 更新值的函数
  //
  // 调用 setExpanded(true) 时：
  //   1. expanded 变为 true
  //   2. 组件自动重新渲染（类似 setter 触发页面刷新）
  //
  // 对比 Java：
  //   private boolean expanded = false;
  //   public void setExpanded(boolean v) { this.expanded = v; }
  //   但 Java 不会自动刷新页面，React 会！
  //
  // 这就是 React 的"响应式"（Reactive）核心：
  // State 变化 → 自动重新渲染 UI
  // ==========================================
  const [expanded, setExpanded] = useState(false);

  // ==========================================
  // 状态：是否点赞（like）
  // 注意：一个组件可以有多个 useState
  // ==========================================
  const [liked, setLiked] = useState(false);

  // ==========================================
  // 事件处理函数
  //
  // 对比 Java:
  //   Java: onclick="handleClick()"
  //   React: onClick={handleClick}
  //
  // 区别：
  //   Java: 字符串的 "方法名"
  //   React: 真正的 JavaScript 函数引用
  //
  // 这里我们定义了一个箭头函数：
  //   const toggleExpand = () => { setExpanded(!expanded); }
  // 箭头函数是 ES6 的新语法：
  //   (参数) => { 函数体 }
  // ==========================================
  const toggleExpand = () => {
    setExpanded(!expanded);
    // !expanded = 取反
    // 如果 expanded 是 true → 变成 false
    // 如果 expanded 是 false → 变成 true
  };

  const toggleLike = (e) => {
    // e.stopPropagation() 阻止事件冒泡
    // 如果不阻止，点击"点赞"按钮也会触发卡片的点击事件
    e.stopPropagation();
    setLiked(!liked);
  };

  // ==========================================
  // 格式化入学年份为字符串（如有需要可以加工数据）
  // ==========================================
  const enrollYearStr = student.enrollYear
    ? `${student.enrollYear}级`
    : '未知';

  // ==========================================
  // JSX 返回（组件的 UI）
  //
  // Tailwind CSS 类名说明：
  //   bg-white        = 白色背景
  //   rounded-lg      = 大圆角
  //   shadow-md       = 中等阴影
  //   p-4             = padding: 1rem
  //   cursor-pointer  = 鼠标变为手指
  //   hover:shadow-lg = 悬停时变大阴影
  //   transition      = 过渡动画
  //   text-lg         = 大号文字
  //   font-semibold   = 半粗体
  //   px-3 py-1       = 左右 0.75rem 上下 0.25rem padding
  //   rounded-full    = 完全圆角（胶囊形）
  // ==========================================
  return (
    <div
      className={`
        bg-white rounded-lg shadow-md p-4 cursor-pointer
        hover:shadow-lg transition-all duration-200
        border-l-4 ${liked ? 'border-red-400' : 'border-blue-400'}
      `}
      onClick={toggleExpand}
      // onClick = 点击事件
      // 点击整张卡片时触发 toggleExpand
    >
      {/* ========================================== */}
      {/* 第 1 行：学号 + 点赞按钮 */}
      {/* ========================================== */}
      <div className="flex justify-between items-center mb-2">
        <span className="text-xs text-gray-400 bg-gray-100 px-2 py-1 rounded">
          {student.studentNo}
          {/* JSX 中 {变量} 可以渲染数字、字符串、JSX 元素 */}
        </span>
        <button
          onClick={toggleLike}
          // onClick = 点击事件（注意：不是 onclick）
          // 这里不需要调用 toggleLike()，只需要引用它
          className={`
            text-sm px-3 py-1 rounded-full transition-colors
            ${liked
              ? 'bg-red-100 text-red-500'
              : 'bg-gray-100 text-gray-400 hover:bg-red-50'
            }
          `}
        >
          {liked ? '❤️ 已点赞' : '🤍 点赞'}
          {/* 条件渲染：三元运算符 condition ? trueValue : falseValue */}
        </button>
      </div>

      {/* ========================================== */}
      {/* 第 2 行：姓名（大头） */}
      {/* ========================================== */}
      <h3 className="text-lg font-semibold text-gray-800">
        {student.name}
      </h3>

      {/* ========================================== */}
      {/* 第 3 行：基本信息（始终显示） */}
      {/* ========================================== */}
      <div className="flex gap-3 text-sm text-gray-500 mt-1">
        <span>🎓 {enrollYearStr}</span>
        <span>👤 {student.gender}</span>
      </div>

      {/* ========================================== */}
      {/* ★ 条件渲染：只有 expanded 为 true 时才显示 ★
      //
      // 语法：{条件 && JSX}
      //   如果条件为 true，渲染 JSX；
      //   如果条件为 false，什么都不渲染。
      //   相当于 Java 的 if (条件) { 渲染 }
      //
      // 这是 React 中最常用的条件渲染方式。
      // 此外还有：
      //   三元: {条件 ? <A/> : <B/>}
      //   if-else: 在 JSX 外写 if/return
      // ========================================== */}
      {expanded && (
        <div className="mt-3 pt-3 border-t border-gray-100">
          {/* 展开的详细信息 */}
          <div className="grid grid-cols-2 gap-2 text-sm">
            <div>
              <span className="text-gray-400">ID</span>
              <p className="text-gray-700">{student.id}</p>
            </div>
            <div>
              <span className="text-gray-400">学号</span>
              <p className="text-gray-700">{student.studentNo}</p>
            </div>
            <div>
              <span className="text-gray-400">性别</span>
              <p className="text-gray-700">{student.gender}</p>
            </div>
            <div>
              <span className="text-gray-400">入学年份</span>
              <p className="text-gray-700">{student.enrollYear}</p>
            </div>
          </div>

          {/* 操作按钮 */}
          <div className="flex gap-2 mt-3">
            <button
              onClick={(e) => {
                // 这里用了箭头函数，不是直接引用
                // 因为我们需要传参数 student
                e.stopPropagation();
                // 调用父组件传入的 onEdit 函数
                onEdit(student);
              }}
              className="flex-1 bg-blue-50 text-blue-600 px-3 py-1.5 rounded text-sm hover:bg-blue-100 transition-colors"
            >
              ✏️ 编辑
            </button>
            <button
              onClick={(e) => {
                e.stopPropagation();
                // 调用父组件传入的 onDelete 函数
                onDelete(student.id);
              }}
              className="flex-1 bg-red-50 text-red-500 px-3 py-1.5 rounded text-sm hover:bg-red-100 transition-colors"
            >
              🗑️ 删除
            </button>
          </div>
        </div>
      )}

      {/* ========================================== */}
      {/* 展开/收起 提示 */}
      {/* ========================================== */}
      <div className="text-center mt-2 text-xs text-gray-300">
        {expanded ? '▲ 点击收起' : '▼ 点击展开'}
      </div>
    </div>
  );
}

export default StudentCard;
