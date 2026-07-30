export const mockStudents = [
  { id: 1, studentNo: '2024001', name: '張三', gender: '男', enrollYear: 2024 },
  { id: 2, studentNo: '2024002', name: '李四', gender: '女', enrollYear: 2024 },
  { id: 3, studentNo: '2023001', name: '王五', gender: '男', enrollYear: 2023 },
];

export function fetchStudents() {
  return new Promise((resolve, reject) => {
    const delay = 1000 + Math.random() * 2000;
    setTimeout(() => {
      // 30% chance of fail
      if (Math.random() < 0.4) {
        reject(new Error('網路錯誤：取得學生列表失敗'));
      } else {
        resolve([...mockStudents]);
      }
    }, delay);
  });
}