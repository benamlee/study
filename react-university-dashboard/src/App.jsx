// ==========================================
// App.jsx - 根组件（应用入口）
//
// 这是整个 React 应用的"大脑"，
// 管理所有顶层状态和导航逻辑。
//
// 对比 Java：
//   Java 入口：UniversityApplication.java（@SpringBootApplication）
//   React 入口：main.jsx → App.jsx
//
//   Java 的 main(): 启动服务器，加载配置
//   React 的 App: 渲染 UI，管理状态
//
// 【本文件涵盖 Session 1~5 全部知识点】
// ==========================================

// ==========================================
// 引入 React 的 useState Hook
// useState 是 React 最基础的 Hook
// ==========================================
import { useState } from 'react';

// ==========================================
// 引入自定义 Hook
// useFetch 是我们自己写的 Hook
// fetchStudents 是模拟的 API 请求函数
// ==========================================
import { useFetch, fetchStudents, fetchStaff, fetchTuitions } from './hooks/useFetch';

// ==========================================
// 引入所有组件
// 每个组件对应一个 .jsx 文件
// 就像 Java 的 import 类
// ==========================================
import Header from './components/Header';
import StudentList from './components/StudentList';
import StudentForm from './components/StudentForm';
import LoadingSpinner from './components/LoadingSpinner';
import ErrorMessage from './components/ErrorMessage';

function App() {
  // ==========================================
  // ★ 导航状态 ★
  //
  // 当前的"页面"：'home' | 'students' | 'staff' | 'finance'
  // 通过 useState 管理当前显示哪个页面
  // 这就是"客户端路由"的雏形
  // ==========================================
  const [currentPage, setCurrentPage] = useState('home');
  // 初始页面：home（首页）

  // ==========================================
  // ★ 表单状态 ★
  //
  // editingStudent = 正在编辑的学生（null = 新增模式）
  // showForm = 是否显示表单
  // ==========================================
  const [editingStudent, setEditingStudent] = useState(null);
  const [showForm, setShowForm] = useState(false);

  // ==========================================
  // ★ 学生数据状态（本地状态）★
  //
  // 在 Phase 1（standalone），
  // 学生数据存在 state 中，增删改直接操作 state。
  //
  // 在 Phase 2（连接后端），
  // 增删改会调用 Spring Boot API 来持久化数据。
  // 但 state 的操作逻辑不变！
  // ==========================================
  const [students, setStudents] = useState([]);

  // ==========================================
  // ★ 使用自定义 Hook 获取数据 ★
  //
  // useFetch(fetchStudents, [])
  //   fetchStudents = 获取数据的函数
  //   [] = 依赖数组（空 = 只在组件挂载时执行一次）
  //
  // 返回：
  //   data    = fetchStudents() 返回的学生数据
  //   loading = true（加载中）/ false（加载完成）
  //   error   = null（无错误）/ 错误消息
  //
  // 解构赋值：
  //   从 useFetch 返回的对象中提取 data, loading, error
  //   并重命名为 studentsData, studentsLoading, studentsError
  // ==========================================
  const {
    data: studentsData,
    loading: studentsLoading,
    error: studentsError,
  } = useFetch(fetchStudents, []);

  const { data: staffData, loading: staffLoading } = useFetch(fetchStaff, []);
  const { data: tuitionData, loading: tuitionLoading } = useFetch(fetchTuitions, []);

  // ==========================================
  // ★ useEffect 同步数据 ★
  //
  // 当 useFetch 返回数据时，同步到本地 state
  //
  // 为什么需要 2 个状态？
  //   useFetch 的数据 = 从"API"获取的原始数据
  //   useState 的数据 = 经过本地 CRUD 操作的最新数据
  //
  // 当 useFetch 返回新数据时，同步到 useState。
  // 这样我们可以本地增删改，而不会因为重新 fetch 丢失修改。
  // ==========================================
  if (studentsData && students.length === 0) {
    // 注意：不要直接 setStudents(studentsData)，
    // 这会导致无限循环（setState → 渲染 → 又走到这里）
    // 所以用条件 students.length === 0 限制只执行一次
    setStudents(studentsData);
  }

  // ==========================================
  // ★ CRUD 操作函数 ★
  //
  // 这些函数修改本地 state 中的学生数据。
  // 在 Phase 2，它们还会调用后端 API。
  // ==========================================

  // 新增学生
  const handleAddStudent = (studentData) => {
    // 生成新 ID（模拟数据库自增）
    const newId = Math.max(...students.map((s) => s.id), 0) + 1;
    const newStudent = { ...studentData, id: newId };

    // setStudents((prev) => [...prev, newStudent])
    // 函数式更新：prev 参数是更新前的状态
    // [...prev, newStudent] = 创建新数组，保留原有数据，追加新学生
    //
    // 为什么不用 students.push(newStudent)？
    //   React 强调不可变性（Immutability）：
    //   不能直接修改 state，必须用 setState 替换。
    //   ...prev = 展开运算符，复制旧数组
    //   , newStudent = 追加新元素
    setStudents((prev) => [...prev, newStudent]);
    setShowForm(false);
  };

  // 更新学生
  const handleUpdateStudent = (studentData) => {
    setStudents((prev) =>
      prev.map((s) => (s.id === studentData.id ? { ...s, ...studentData } : s))
      // .map() 遍历数组
      // 如果 s.id === studentData.id（找到要更新的学生）
      //   返回 { ...s, ...studentData }（合并旧数据和新数据）
      // 否则返回原对象 s
    );
    setShowForm(false);
    setEditingStudent(null);
  };

  // 删除学生
  const handleDeleteStudent = (id) => {
    // window.confirm = 浏览器原生确认弹窗
    // 相当于 Java 中 JSP 的 onclick="return confirm(...)"
    if (window.confirm('确定要删除该学生吗？')) {
      setStudents((prev) =>
        prev.filter((s) => s.id !== id)
        // .filter() = 保留所有 id 不等于要删除 id 的学生
        // 相当于 SQL: DELETE FROM students WHERE id != 目标id
      );
    }
  };

  // 编辑学生：打开表单并填充数据
  const handleEditStudent = (student) => {
    setEditingStudent(student);
    setShowForm(true);
  };

  // 取消编辑/新增
  const handleCancelForm = () => {
    setShowForm(false);
    setEditingStudent(null);
  };

  // ==========================================
  // ★ 导航按钮渲染 ★
  //
  // 对比 Java/JSP 中的导航：
  //   <a href="/student/list">学生管理</a>
  //   每次点击 → 浏览器发送 HTTP 请求 → 服务器返回新页面
  //   = 多页应用（MPA）
  //
  // React 导航：
  //   <button onClick={() => setCurrentPage('students')}>
  //   点击 → 修改 state → React 重新渲染（不刷新页面）
  //   = 单页应用（SPA）
  // ==========================================

  // ==========================================
  // ★ 内容区域渲染 ★
  //
  // 根据 currentPage 渲染不同内容
  //
  // 这是"条件渲染"的另一种方式：
  // 用 if-else 在 JSX 之前决定返回什么。
  // ==========================================

  // ==========================================
  // ★ 首页内容 ★
  // ==========================================
  const renderHomePage = () => (
    <div className="max-w-4xl mx-auto px-4 py-12">
      <div className="text-center mb-12">
        <h2 className="text-2xl font-bold text-gray-800 mb-2">
          🏫 React 大学管理系统
        </h2>
        <p className="text-gray-500">
          学习 React 的完整示例项目
        </p>
      </div>

      {/* 功能卡片网格 */}
      <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
        {/* 学生管理卡片 */}
        <div className="bg-white rounded-xl shadow-md p-6 hover:shadow-lg transition-shadow border-t-4 border-blue-400">
          <div className="text-4xl mb-3">📚</div>
          <h3 className="text-lg font-semibold text-gray-800 mb-2">学生管理</h3>
          <p className="text-sm text-gray-500 mb-4">
            Session 1-5 核心内容：Props、State、列表、表单、请求
          </p>
          <button
            onClick={() => setCurrentPage('students')}
            className="bg-blue-500 text-white px-4 py-2 rounded-lg text-sm
                       hover:bg-blue-600 transition-colors w-full"
          >
            进入 →
          </button>
        </div>

        {/* 教职工管理卡片 */}
        <div className="bg-white rounded-xl shadow-md p-6 hover:shadow-lg transition-shadow border-t-4 border-green-400">
          <div className="text-4xl mb-3">👨‍🏫</div>
          <h3 className="text-lg font-semibold text-gray-800 mb-2">教职工管理</h3>
          <p className="text-sm text-gray-500 mb-4">
            使用相同的组件模式，展示列表渲染
          </p>
          <button
            onClick={() => setCurrentPage('staff')}
            className="bg-green-500 text-white px-4 py-2 rounded-lg text-sm
                       hover:bg-green-600 transition-colors w-full"
          >
            进入 →
          </button>
        </div>

        {/* 财务管理卡片 */}
        <div className="bg-white rounded-xl shadow-md p-6 hover:shadow-lg transition-shadow border-t-4 border-orange-400">
          <div className="text-4xl mb-3">💰</div>
          <h3 className="text-lg font-semibold text-gray-800 mb-2">财务管理</h3>
          <p className="text-sm text-gray-500 mb-4">
            展示自定义 Hook useFetch 的数据获取
          </p>
          <button
            onClick={() => setCurrentPage('finance')}
            className="bg-orange-500 text-white px-4 py-2 rounded-lg text-sm
                       hover:bg-orange-600 transition-colors w-full"
          >
            进入 →
          </button>
        </div>
      </div>

      {/* 学习进度说明 */}
      <div className="mt-12 bg-blue-50 border border-blue-100 rounded-lg p-6">
        <h4 className="font-semibold text-blue-800 mb-2">🎯 学习进度</h4>
        <ul className="text-sm text-blue-700 space-y-1">
          <li>✅ Session 1: JSX + Components + Props（Header + StudentCard）</li>
          <li>✅ Session 2: useState + Events（展开/收起 + 点赞）</li>
          <li>✅ Session 3: Lists + Conditional Rendering（StudentList + 搜索）</li>
          <li>✅ Session 4: useEffect + Custom Hook（useFetch + 加载/错误状态）</li>
          <li>✅ Session 5: Forms + Controlled Components（StudentForm + 校验）</li>
        </ul>
      </div>
    </div>
  );

  // ==========================================
  // ★ 学生管理页面 ★
  // ==========================================
  const renderStudentPage = () => (
    <div className="max-w-5xl mx-auto px-4 py-6">
      {/* 页面导航 */}
      <div className="flex items-center justify-between mb-6">
        <div>
          <button
            onClick={() => setCurrentPage('home')}
            className="text-sm text-blue-500 hover:text-blue-700"
          >
            ← 返回首页
          </button>
          <h2 className="text-xl font-bold text-gray-800 mt-1">📚 学生管理</h2>
        </div>

        {/* ========================================== */}
        {/* 条件渲染：添加按钮（只有在不显示表单时才显示） */}
        {/* ========================================== */}
        {!showForm && (
          <button
            onClick={() => {
              setEditingStudent(null);
              setShowForm(true);
            }}
            className="bg-blue-500 text-white px-4 py-2 rounded-lg text-sm
                       hover:bg-blue-600 transition-colors flex items-center gap-1"
          >
            ＋ 添加学生
          </button>
        )}
      </div>

      {/* ========================================== */}
      {/* ★ 条件渲染：表单 OR 列表 ★
      //
      // 如果 showForm 为 true → 显示表单
      // 否则 → 显示列表（可能显示加载/错误）
      // ========================================== */}
      {showForm ? (
        <div className="max-w-lg mx-auto">
          <StudentForm
            initialData={editingStudent}
            onSubmit={editingStudent ? handleUpdateStudent : handleAddStudent}
            onCancel={handleCancelForm}
          />
        </div>
      ) : (
        <>
          {/* ========================================== */}
          {/* ★ 条件渲染：加载 / 错误 / 列表 ★
          //
          // 这是 React 中最常见的三种状态处理：
          //   1. 加载中 → LoadingSpinner
          //   2. 错误 → ErrorMessage
          //   3. 正常 → StudentList
          // ========================================== */}

          {studentsLoading && <LoadingSpinner message="正在加载学生数据..." />}

          {studentsError && (
            <ErrorMessage
              message={studentsError}
              onRetry={() => window.location.reload()}
            />
          )}

          {!studentsLoading && !studentsError && (
            <StudentList
              students={students}
              onDelete={handleDeleteStudent}
              onEdit={handleEditStudent}
            />
          )}
        </>
      )}
    </div>
  );

  // ==========================================
  // ★ 教职工管理页面（简化版）★
  // ==========================================
  const renderStaffPage = () => (
    <div className="max-w-4xl mx-auto px-4 py-6">
      <button
        onClick={() => setCurrentPage('home')}
        className="text-sm text-blue-500 hover:text-blue-700"
      >
        ← 返回首页
      </button>
      <h2 className="text-xl font-bold text-gray-800 mt-1 mb-6">👨‍🏫 教职工管理</h2>

      {staffLoading && <LoadingSpinner message="正在加载教职工数据..." />}

      {!staffLoading && staffData && (
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
          {staffData.map((person) => (
            <div
              key={person.id}
              className="bg-white rounded-lg shadow-md p-4 border-l-4 border-green-400"
            >
              <h3 className="font-semibold text-gray-800">{person.name}</h3>
              <div className="text-sm text-gray-500 mt-2">
                <p>工号：{person.staffNo}</p>
                <p>部门：{person.department}</p>
                <p>职位：{person.position}</p>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );

  // ==========================================
  // ★ 财务管理页面（简化版）★
  // ==========================================
  const renderFinancePage = () => (
    <div className="max-w-4xl mx-auto px-4 py-6">
      <button
        onClick={() => setCurrentPage('home')}
        className="text-sm text-blue-500 hover:text-blue-700"
      >
        ← 返回首页
      </button>
      <h2 className="text-xl font-bold text-gray-800 mt-1 mb-6">💰 缴费记录</h2>

      {tuitionLoading && <LoadingSpinner message="正在加载缴费记录..." />}

      {!tuitionLoading && tuitionData && (
        <div className="bg-white rounded-lg shadow-md overflow-hidden">
          <table className="w-full text-sm">
            <thead className="bg-gray-50 text-gray-600">
              <tr>
                <th className="px-4 py-3 text-left">学号</th>
                <th className="px-4 py-3 text-left">姓名</th>
                <th className="px-4 py-3 text-left">金额</th>
                <th className="px-4 py-3 text-left">学期</th>
                <th className="px-4 py-3 text-left">日期</th>
              </tr>
            </thead>
            <tbody>
              {tuitionData.map((t) => (
                <tr key={t.id} className="border-t border-gray-100 hover:bg-gray-50">
                  <td className="px-4 py-3">{t.studentNo}</td>
                  <td className="px-4 py-3">{t.studentName}</td>
                  <td className="px-4 py-3">¥{(t.amount / 100).toFixed(2)}</td>
                  <td className="px-4 py-3">{t.semester}</td>
                  <td className="px-4 py-3 text-gray-400">{t.paymentDate}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      )}
    </div>
  );

  // ==========================================
  // ★ 主渲染逻辑 ★
  //
  // 根据 currentPage 的值，返回不同的内容
  // 这就是"条件渲染" + "简单路由"
  // ==========================================
  const renderPage = () => {
    switch (currentPage) {
      case 'students':
        return renderStudentPage();
      case 'staff':
        return renderStaffPage();
      case 'finance':
        return renderFinancePage();
      default:
        return renderHomePage();
    }
  };

  // ==========================================
  // ★ App 根组件的 JSX ★
  // ==========================================
  return (
    <div className="min-h-screen bg-gray-50">
      {/* Header 组件（始终显示） */}
      <Header
        title="🏫 React 大学管理系统"
        subtitle="学习 React 核心概念：组件 Props → State → 列表 → 请求 → 表单"
      />

      {/* 页面内容区域 */}
      {renderPage()}

      {/* 页脚 */}
      <footer className="text-center text-gray-400 text-xs py-8 mt-8 border-t border-gray-100">
        <p>React 学习项目 | Phase 1: Standalone（模拟数据，无后端）</p>
        <p className="mt-1">对比学习：Java EE → Spring MVC → Spring Boot → React</p>
      </footer>
    </div>
  );
}

export default App;
