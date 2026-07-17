// ==========================================
// mockData.js - 模拟数据
//
// 在真实项目中，这些数据来自后端 API。
// 在 Phase 1（Standalone 阶段），
// 我们先用假数据学习 React，不依赖后端。
//
// Phase 2 会改成从 Spring Boot 后端获取。
// ==========================================

// ==========================================
// 模拟学生数据
//
// 和 Java 版 Student 实体完全对应：
//   id, studentNo, name, gender, enrollYear
// 但这里是 JavaScript 对象，不是 Java 类。
// ==========================================
export const mockStudents = [
  { id: 1, studentNo: '2024001', name: '张三', gender: '男', enrollYear: 2024 },
  { id: 2, studentNo: '2024002', name: '李四', gender: '女', enrollYear: 2024 },
  { id: 3, studentNo: '2023001', name: '王五', gender: '男', enrollYear: 2023 },
  { id: 4, studentNo: '2023002', name: '赵六', gender: '女', enrollYear: 2023 },
  { id: 5, studentNo: '2022001', name: '孙七', gender: '男', enrollYear: 2022 },
];

// ==========================================
// 模拟教职工数据
// ==========================================
export const mockStaff = [
  { id: 1, staffNo: 'T001', name: '周教授', department: '计算机系', position: '教授' },
  { id: 2, staffNo: 'T002', name: '吴老师', department: '数学系', position: '副教授' },
  { id: 3, staffNo: 'T003', name: '郑老师', department: '计算机系', position: '讲师' },
];

// ==========================================
// 模拟缴费记录数据
// ==========================================
export const mockTuitions = [
  { id: 1, studentId: 1, studentNo: '2024001', studentName: '张三', amount: 500000, semester: '2024-2025-1', paymentDate: '2024-09-01' },
  { id: 2, studentId: 2, studentNo: '2024002', studentName: '李四', amount: 500000, semester: '2024-2025-1', paymentDate: '2024-09-05' },
];
