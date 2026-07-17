// ==========================================
// ErrorMessage.jsx - 错误消息组件
//
// 【学习目标】
//   1. 条件渲染 + Props
//   2. React 中的错误处理
//
// 对比 Java：
//   Java: try-catch → 返回错误页面
//   React: try-catch → setError → 显示 ErrorMessage
//
// React 的哲学：
//   错误也是一种"状态"，用状态驱动 UI。
//   这比 Java 的 try-catch + 页面跳转更直接。
// ==========================================

function ErrorMessage({ message, onRetry }) {
  // ==========================================
  // 如果 message 为空/未定义，不渲染任何内容
  //
  // 这保证了：当没有错误时，页面不会显示
  // 空白的错误框。
  //
  // 父组件可以这样使用：
  //   {error && <ErrorMessage message={error} />}
  // 但在 ErrorMessage 内部再做一次安全检查更稳妥。
  // ==========================================
  if (!message) return null;

  return (
    <div className="bg-red-50 border border-red-200 rounded-lg p-4 mx-4 my-4">
      <div className="flex items-start gap-3">
        {/* 错误图标 */}
        <span className="text-2xl">⚠️</span>

        <div className="flex-1">
          {/* 错误标题 */}
          <h3 className="text-red-700 font-medium text-sm">发生错误</h3>

          {/* 错误详情 */}
          <p className="text-red-600 text-sm mt-1">{message}</p>
        </div>

        {/* onRetry = 重试函数（从父组件传入） */}
        {onRetry && (
          <button
            onClick={onRetry}
            className="bg-red-100 text-red-600 px-4 py-1.5 rounded text-sm
                       hover:bg-red-200 transition-colors whitespace-nowrap"
          >
            重试
          </button>
        )}
      </div>
    </div>
  );
}

export default ErrorMessage;
