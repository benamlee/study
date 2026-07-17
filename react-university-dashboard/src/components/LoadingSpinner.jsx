// ==========================================
// LoadingSpinner.jsx - 加载动画组件
//
// 【学习目标】
//   1. 纯展示组件（无状态，仅 Props）
//   2. Tailwind 动画类
//
// 这个组件没有任何 useState 或事件逻辑，
// 只根据 props 显示不同的加载状态。
// 这是"展示组件"（Presentational Component）的典型例子。
// ==========================================

function LoadingSpinner({ message = '加载中...' }) {
  // ==========================================
  // 默认参数（Default Parameters）
  //
  // { message = '加载中...' }
  // 如果父组件没有传 message 属性，
  // 则使用默认值 '加载中...'
  //
  // 相当于：
  //   const msg = message || '加载中...';
  // ==========================================

  return (
    <div className="flex flex-col items-center justify-center py-20">
      {/* Tailwind 动画：旋转的圆圈 */}
      <div className="w-12 h-12 border-4 border-blue-200 border-t-blue-500 rounded-full animate-spin" />

      <p className="mt-4 text-gray-500 text-sm">{message}</p>
    </div>
  );
}

export default LoadingSpinner;
