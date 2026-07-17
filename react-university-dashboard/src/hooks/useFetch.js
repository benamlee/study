// ==========================================
// useFetch.js - 自定义 Hook
//
// 【学习目标】Session 4: useEffect + 自定义 Hook
//
// 什么是 Hook？
//   Hook 是以 use 开头的函数，可以调用其他 Hook。
//   自定义 Hook 让组件间共享有状态的逻辑。
//
// 什么是 useEffect？
//   useEffect = 副作用处理函数
//   "副作用" = 组件渲染之外的额外操作：
//     - 数据请求（API 调用）
//     - 定时器（setTimeout / setInterval）
//     - DOM 操作
//     - 日志记录
//
//   对比 Java：
//     Java：Service 方法直接调用 DAO/Repository
//           不需要"副作用"的概念
//     React：组件的"主要工作"是渲染 UI
//            其他都是"副作用"
//            副作用需要用 useEffect 包裹
//
// useEffect 语法：
//   useEffect(() => {
//     // 副作用代码
//     return () => { /* 清理代码（可选）*/ };
//   }, [依赖数组]);
//
//   依赖数组 = 当这些值变化时才重新执行副作用
//   如果依赖数组为空 []：只在组件挂载时执行一次
//   如果不传依赖数组：每次渲染都执行（危险！）
// ==========================================

import { useState, useEffect } from 'react';

// ==========================================
// 自定义 Hook: useFetch
//
// 作用：模拟从 API 获取数据
// 输入：fetchFn — 返回 Promise 的函数（模拟网络请求）
//       deps — 依赖数组（当依赖变化时重新获取）
// 返回：{ data, loading, error }
//
// 这个 Hook 封装了"请求数据"的通用逻辑：
//   1. 初始状态：loading = true
//   2. 请求完成：data = 结果, loading = false
//   3. 请求失败：error = 错误, loading = false
//
// 在 Phase 2 连接 Spring Boot 时，
// 这个 Hook 将直接调用 fetch('/api/students')，
// 其他代码都不需要改！
// ==========================================
export function useFetch(fetchFn, deps = []) {
  const [data, setData] = useState(null);
  // data = 请求返回的数据
  // 初始为 null，请求完成后设为实际数据

  const [loading, setLoading] = useState(true);
  // loading = 是否正在加载
  // 初始为 true，请求完成后设为 false

  const [error, setError] = useState(null);
  // error = 错误信息
  // 初始为 null，请求失败时设为错误原因

  // ==========================================
  // ★ useEffect ★
  //
  // 这个 useEffect 在组件"挂载"时执行一次
  // （因为依赖数组是 []，注意这里是 deps 参数，
  //  调用方传入的依赖）
  //
  // useEffect 不能返回 Promise，
  // 所以需要在内部定义 async 函数再调用。
  // ==========================================
  useEffect(() => {
    // 每次重新请求前，重置状态
    setLoading(true);
    setError(null);
    setData(null);

    let cancelled = false;
    // ==========================================
    // 竞态条件处理（Race Condition）
    //
    // 如果组件在请求完成前卸载了，
    // 不应该再 setData/setError。
    // cancelled 标记用于防止这种情况。
    //
    // 注意：这里是为了演示"竞态条件"概念。
    // 在真实项目中，可以用 AbortController。
    // ==========================================

    // ==========================================
    // 模拟网络请求
    //
    // 在真实项目中，这里会是：
    //   const res = await fetch('/api/students');
    //   const data = await res.json();
    //   setData(data);
    //
    // 但现在（Phase 1）我们用 setTimeout 模拟延迟，
    // 然后调用 fetchFn（从参数传入）获取数据。
    // ==========================================
    const loadData = async () => {
      try {
        // 模拟网络延迟（0.5 ~ 1 秒）
        const delay = 500 + Math.random() * 500;
        await new Promise((resolve) => setTimeout(resolve, delay));

        if (cancelled) return;

        // 调用传入的 fetchFn 获取数据
        const result = await fetchFn();
        if (!cancelled) {
          setData(result);
          setLoading(false);
        }
      } catch (err) {
        if (!cancelled) {
          setError(err.message || '获取数据失败');
          setLoading(false);
        }
      }
    };

    loadData();

    // ==========================================
    // useEffect 的清理函数（Cleanup）
    //
    // 当组件卸载时，或者依赖变化重新执行 useEffect 前，
    // 会执行这个清理函数。
    //
    // 这里设置 cancelled = true 来防止
    // 在已卸载的组件上 setState。
    // ==========================================
    return () => {
      cancelled = true;
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, deps);
  // 依赖数组：当 deps 中的值变化时，重新执行 useEffect

  return { data, loading, error };
  // 返回三个状态，供组件使用
}

// ==========================================
// 模拟数据获取函数
//
// 这些函数模拟从 API 获取数据。
// 在 Phase 1，它们只是返回 mock 数据。
// 在 Phase 2，它们会改成真正的 fetch() 调用。
// ==========================================

import { mockStudents, mockStaff, mockTuitions } from '../data/mockData';

// 获取所有学生（模拟 10% 概率失败，展示错误处理）
export function fetchStudents() {
  return new Promise((resolve, reject) => {
    // 10% 概率模拟网络错误
    if (Math.random() < 0.1) {
      reject(new Error('网络错误：获取学生列表失败'));
    } else {
      resolve([...mockStudents]);
    }
  });
}

// 获取所有教职工
export function fetchStaff() {
  return Promise.resolve([...mockStaff]);
}

// 获取所有缴费记录
export function fetchTuitions() {
  return Promise.resolve([...mockTuitions]);
}
