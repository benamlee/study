import Header from "./components/Header";
import StudentCard from "./components/StudentCard";
import { mockStudents } from "./data/mockData";

function App() {

  return (
    <div>
      <Header
        title="React 大學管理系統"
        subtitle="學習 React 核心概念"
      />
      <div className="max-w-md mx-auto mt-8 px-4 space-y-4">
        {mockStudents.map((student) => (
          <StudentCard key={student.id} student={student} />
        ))}
      </div>
    </div>
  );
}

export default App;
