// ==========================================
// StudentForm.jsx - 学生表单组件
//
// 【学习目标】Session 5: 受控组件 + 表单校验
//
// 受控组件（Controlled Component）：
//   React 控制表单元素的值和行为。
//   表单的值存储在 state 中，而不是 DOM 中。
//
// 对比：
//   JSP 表单：用户输入 → 提交 → Servlet 获取参数
//   React 受控组件：用户输入 → onChange → setState
//                   → 重新渲染 → 显示新值
//   React 的"数据流"是单向的：state → UI
//   用户输入触发 onChange → state 更新 → UI 更新
//
// 非受控组件（Uncontrolled Component）：
//   用 ref 直接操作 DOM（类似 JS 的 document.getElementById）
//   不推荐，除非特殊情况。
// ==========================================

import { useState } from 'react';

// ==========================================
// initialStudent = 默认值
// 新增时使用空对象，编辑时使用传入的学生数据
// ==========================================
const emptyStudent = {
  studentNo: '',
  name: '',
  gender: '男',
  enrollYear: new Date().getFullYear(),
};

function StudentForm({ initialData, onSubmit, onCancel }) {
  // ==========================================
  // ★ 表单数据 state ★
  //
  // 每个表单字段对应 state 中的一个属性。
  // 当用户在输入框中输入时，onChange 更新对应的属性。
  // ==========================================
  const [formData, setFormData] = useState({
    ...emptyStudent,
    ...initialData,
    // 如果 initialData 有值，覆盖空对象的默认值
    // 例如编辑时传入 { id: 3, name: '张三', ... }
  });

  // ==========================================
  // ★ 校验错误 state ★
  //
  // { 字段名: 错误消息 }
  // 如果字段没有错误，值为 undefined
  // ==========================================
  const [errors, setErrors] = useState({});

  // ==========================================
  // ★ 表单已提交标记 ★
  //
  // 只有用户点击过"保存"后才显示错误提示
  // 初次打开表单时不想看到满屏红色
  // ==========================================
  const [submitted, setSubmitted] = useState(false);

  // ==========================================
  // 判断是"新增"还是"编辑"
  // ==========================================
  const isEditing = !!initialData?.id;
  // !! = 强制转为 boolean
  // initialData?.id = 可选链，如果 initialData 是 null/undefined
  //                   不会报错，返回 undefined

  // ==========================================
  // ★ onChange 事件处理器 ★
  //
  // 所有输入框共用这一个处理函数！
  // name = 输入框的 name 属性（对应 state 中的属性名）
  // value = 输入框的当前值
  //
  // [name]: value = 计算属性名（Computed Property Name）
  // 相当于：
  //   if (name === 'studentNo') newData.studentNo = value;
  //   if (name === 'name') newData.name = value;
  // 用计算属性名一行搞定！
  // ==========================================
  const handleChange = (e) => {
    const { name, value } = e.target;
    // e.target = 触发事件的 DOM 元素（input/select）
    // 解构赋值：从 e.target 提取 name 和 value

    setFormData((prev) => ({
      ...prev,
      // ... 展开运算符：复制所有原有字段
      [name]: value,
      // 只更新变化的字段
    }));

    // ==========================================
    // 实时清除校验错误
    // 当用户开始修改某个字段时，清除该字段的错误提示
    // ==========================================
    if (errors[name]) {
      setErrors((prev) => {
        const newErrors = { ...prev };
        delete newErrors[name];
        return newErrors;
      });
    }
  };

  // ==========================================
  // ★ 表单校验 ★
  //
  // 返回 errors 对象
  // 如果所有字段校验通过，返回空对象 {}
  //
  // 对比 Java：
  //   Java: @NotBlank @NotNull + BindingResult
  //   React: 手动校验（或使用 Formik / React Hook Form 等库）
  //   在 Phase 1 中我们手动实现校验，理解原理。
  // ==========================================
  const validate = () => {
    const newErrors = {};

    if (!formData.studentNo.trim()) {
      newErrors.studentNo = '学号不能为空';
    } else if (formData.studentNo.trim().length < 4) {
      newErrors.studentNo = '学号至少 4 位字符';
    }

    if (!formData.name.trim()) {
      newErrors.name = '姓名不能为空';
    } else if (formData.name.trim().length < 2) {
      newErrors.name = '姓名至少 2 个字符';
    }

    if (!formData.gender) {
      newErrors.gender = '请选择性别';
    }

    if (!formData.enrollYear) {
      newErrors.enrollYear = '请输入入学年份';
    } else if (
      isNaN(formData.enrollYear) ||
      formData.enrollYear < 1900 ||
      formData.enrollYear > 2100
    ) {
      newErrors.enrollYear = '请输入有效的年份（1900-2100）';
    }

    return newErrors;
  };

  // ==========================================
  // ★ 表单提交处理 ★
  //
  // 1. 阻止表单默认提交行为（页面刷新）
  // 2. 校验表单数据
  // 3. 如果有错误，显示错误提示
  // 4. 如果通过校验，调用父组件的 onSubmit
  // ==========================================
  const handleSubmit = (e) => {
    e.preventDefault();
    // ==========================================
    // ★ e.preventDefault() ★
    //
    // 在 HTML 中，表单提交默认会刷新页面。
    // React 是 SPA（单页应用），不希望页面刷新。
    // 所以需要阻止默认行为。
    //
    // 对比 JSP：
    //   JSP 表单：<form action="servlet/student/save" method="post">
    //   提交后页面跳转到 Servlet 的响应页面。
    //
    //   React 表单：<form onSubmit={handleSubmit}>
    //   提交后阻止刷新，用 AJAX 请求后端。
    //   页面无需跳转，数据更新后自动刷新 UI。
    // ==========================================

    setSubmitted(true);
    const validationErrors = validate();
    setErrors(validationErrors);

    // ==========================================
    // Object.keys(obj) 返回对象的所有属性名组成的数组
    // 如果 errors 为空，说明校验通过
    // ==========================================
    if (Object.keys(validationErrors).length === 0) {
      // 构建提交数据
      const submitData = {
        ...formData,
        enrollYear: Number(formData.enrollYear),
        // 确保年份是数字（表单输入默认是字符串）
      };

      onSubmit(submitData);
      // 调用父组件传入的 onSave 回调
    }
  };

  // ==========================================
  // ★ 辅助函数：获取字段的错误样式 ★
  //
  // 如果字段有错误且已经提交过，显示红色边框
  // ==========================================
  const fieldClass = (fieldName) => {
    const hasError = submitted && errors[fieldName];
    return `w-full px-3 py-2 border rounded-lg text-sm
            focus:outline-none focus:ring-2 focus:ring-blue-400
            transition-colors
            ${hasError
              ? 'border-red-300 bg-red-50'
              : 'border-gray-200 bg-white'
            }`;
  };

  // ==========================================
  // JSX 返回：表单 UI
  // ==========================================
  return (
    <form onSubmit={handleSubmit} className="bg-white rounded-lg p-6 shadow-md">
      {/* 标题 */}
      <h2 className="text-lg font-semibold text-gray-800 mb-4">
        {isEditing ? '✏️ 编辑学生' : '📝 添加学生'}
      </h2>

      {/* ========================================== */}
      {/* 表单字段：学号 */}
      {/* ========================================== */}
      <div className="mb-4">
        <label className="block text-sm font-medium text-gray-600 mb-1">
          学号
        </label>
        <input
          type="text"
          name="studentNo"
          // name 属性 = 表单字段名
          // 对应 formData.studentNo
          value={formData.studentNo}
          onChange={handleChange}
          placeholder="请输入学号，如 2024001"
          className={fieldClass('studentNo')}
        />
        {/* ========================================== */}
        {/* ★ 条件渲染：显示校验错误 ★
        //
        // 只有 submitted 为 true 且该字段有错误时才显示。
        // 相当于 Java 的：
        //   <c:if test="${errors.hasFieldErrors('studentNo')}">
        //     ${errors.getFieldError('studentNo').defaultMessage}
        //   </c:if>
        // ========================================== */}
        {submitted && errors.studentNo && (
          <p className="text-red-500 text-xs mt-1">⚠ {errors.studentNo}</p>
        )}
      </div>

      {/* ========================================== */}
      {/* 表单字段：姓名 */}
      {/* ========================================== */}
      <div className="mb-4">
        <label className="block text-sm font-medium text-gray-600 mb-1">
          姓名
        </label>
        <input
          type="text"
          name="name"
          value={formData.name}
          onChange={handleChange}
          placeholder="请输入姓名"
          className={fieldClass('name')}
        />
        {submitted && errors.name && (
          <p className="text-red-500 text-xs mt-1">⚠ {errors.name}</p>
        )}
      </div>

      {/* ========================================== */}
      {/* 表单字段：性别（select 下拉框） */}
      {/* ========================================== */}
      <div className="mb-4">
        <label className="block text-sm font-medium text-gray-600 mb-1">
          性别
        </label>
        <select
          name="gender"
          value={formData.gender}
          onChange={handleChange}
          className={fieldClass('gender')}
        >
          <option value="男">男</option>
          <option value="女">女</option>
        </select>
        {submitted && errors.gender && (
          <p className="text-red-500 text-xs mt-1">⚠ {errors.gender}</p>
        )}
      </div>

      {/* ========================================== */}
      {/* 表单字段：入学年份 */}
      {/* ========================================== */}
      <div className="mb-6">
        <label className="block text-sm font-medium text-gray-600 mb-1">
          入学年份
        </label>
        <input
          type="number"
          name="enrollYear"
          value={formData.enrollYear}
          onChange={handleChange}
          placeholder="如 2024"
          className={fieldClass('enrollYear')}
        />
        {submitted && errors.enrollYear && (
          <p className="text-red-500 text-xs mt-1">⚠ {errors.enrollYear}</p>
        )}
      </div>

      {/* ========================================== */}
      {/* 提交/取消 按钮 */}
      {/* ========================================== */}
      <div className="flex gap-3">
        <button
          type="submit"
          className="flex-1 bg-blue-500 text-white px-4 py-2 rounded-lg
                     text-sm font-medium hover:bg-blue-600
                     transition-colors"
        >
          💾 保存
        </button>
        {onCancel && (
          <button
            type="button"
            onClick={onCancel}
            className="flex-1 bg-gray-100 text-gray-600 px-4 py-2 rounded-lg
                       text-sm hover:bg-gray-200 transition-colors"
          >
            ↩ 取消
          </button>
        )}
      </div>
    </form>
  );
}

export default StudentForm;
