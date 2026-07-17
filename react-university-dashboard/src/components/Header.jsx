// ==========================================
// Header.jsx - 应用导航栏
//
// 【学习目标】JSX 基础 + Props
//
// JSX = JavaScript + XML
// 在 JS 中直接写 HTML 标签的语法扩展。
// 不是模板引擎（如 JSP），而是编译为 JS 函数调用。
//
// 对比 Java：
//   JSP：<h1>标题</h1> + <%=变量%>
//   React JSX：<h1>{变量}</h1>
//   JSX 的 {大括号} 中可以写任意 JavaScript 表达式。
// ==========================================

// ==========================================
// 什么是 Props？
// Props = Properties（属性）
// 相当于 Java 构造函数的参数：
//   Java: new StudentCard("张三", "2024001")
//   React: <StudentCard name="张三" studentNo="2024001" />
//
// Props 是组件接收外部数据的"管道"。
// 组件不能修改自己的 props（只读）。
// ==========================================

function Header({ title, subtitle }) {
  // ==========================================
  // 解构赋值（Destructuring）
  //
  // function Header(props)  ← props 是一个对象
  // 写成 { title, subtitle } = 直接从 props 对象中
  // 提取 title 和 subtitle 两个属性。
  //
  // 相当于：
  //   const title = props.title;
  //   const subtitle = props.subtitle;
  //
  // 这是 React 的常见写法（ES6 语法）。
  // ==========================================

  // ==========================================
  // JSX 的返回规则：
  //   1. 必须有一个根元素（或 Fragment <>...</>）
  //   2. {变量名} 在 JSX 中渲染变量
  //   3. className = HTML 的 class（因为 class 是 JS 保留字）
  //   4. Tailwind 类名直接写在 className 中
  // ==========================================

  return (
    <header className="bg-gradient-to-r from-blue-500 to-purple-600 text-white py-6 px-8 shadow-lg">
      <div className="max-w-5xl mx-auto">
        <h1 className="text-3xl font-bold">{title}</h1>
        {/* JSX 注释写法（在花括号里写 JS 注释） */}
        {subtitle && (
          <p className="text-blue-100 mt-1 text-sm">{subtitle}</p>
        )}
        {/* 条件渲染 && 运算符：
            如果 subtitle 有值，显示 <p> 标签；
            如果 subtitle 是 undefined/null，不渲染。
            相当于 Java 的 if (subtitle != null)  */}
      </div>
    </header>
  );
}

// ==========================================
// export default = 导出组件
// 其他文件用 import Header from './Header' 导入
// ==========================================
export default Header;
