import Header from "./components/Header";
import StudentCard from "./components/StudentCard";

function App() {
  const demoStudent = {
    id: 1,
    studentNo: '2024001',
    name: '張三',
    gender: '男',
    enrollYear: 2024,
  }

  return (
    <div>
      <Header
        title="React 大學管理系統"
        subtitle="學習 React 核心概念"
      />
      <div className="max-w-md mx-auto mt-8 px-4">
        <StudentCard student={demoStudent} />
      </div>
    </div>
  );
}

export default App;
