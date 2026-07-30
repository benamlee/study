import Header from "./components/Header";
// import StudentCard from "./components/StudentCard";
import StudentList from "./components/StudentList";
// import { mockStudents } from "./data/mockData";
import { fetchStudents } from "./data/mockData";
import { useFetch } from "./hooks/useFetch";
import LoadingSpinner from './components/LoadingSpinner';
import ErrorMessage from "./components/ErrorMessage";
import { useEffect, useState } from "react";
import StudentForm from "./components/StudentForm";


function App() {
  const { data: students, loading, error } = useFetch(fetchStudents, []);

  const [showForm, setShowForm] = useState(false);
  const [editingStudent, setEditingStudent] = useState(null);

  const [studentList, setStudentList] = useState([]);

  const handleAddStudent = (studentData) => {
    const newId = Math.max(...studentList.map((s) => s.id), 0) + 1;
    setStudentList((prev) => [...prev, { ...studentData, id: newId }]);
    setShowForm(false);
  };

  const handleUpdateStudent = (studentData) => {
    setStudentList((prev) => 
      prev.map((s) => (s.id === studentData.id ? { ...s, ...studentData } : s))
    );
    setShowForm(false);
    setEditingStudent(null);
  };

  const handleEditStudent = (student) => {
    setEditingStudent(student);
    setShowForm(true);
  };

  const handleDeleteStudent = (id) => {
    if (window.confirm('確定要刪除嗎？')) {
      setStudentList((prev) => prev.filter((s) => s.id !== id));
    }
  };

  const handleCancelForm = () => {
    setShowForm(false);
    setEditingStudent(null);
  }

  useEffect(() => {
    if (students) {
      setStudentList(students);
    }
  }, [students]);

  return (
    <div>
      <Header
        title="React 大學管理系統"
        subtitle="學習 React 核心概念"
      />
      <div className="max-w-5xl mx-auto mt-8 px-4">
        {showForm ? (
          <div className="max-w-lg mx-auto">
            <StudentForm
              initialData={editingStudent}
              onSubmit={editingStudent ? handleUpdateStudent : handleAddStudent}
              onCancel={handleCancelForm}
            />
          </div>
        ) : loading ? (
          <LoadingSpinner message="正在載入學生資料" />
        ) : error ? (
          <ErrorMessage
            message={error}
            onRetry={() => window.location.reload()}
          />
        ) : (
          <>
            <div className="flex justify-end mb-4">
              <button onClick={() => { setEditingStudent(null); setShowForm(true); }}
                className="bg-blue-500 text-white px-4 py-2 rounded-lg text-sm
                            hover:bg-blue-600 transition-colors">
                ＋ 新增學生
              </button>
            </div>
            <StudentList
              students={studentList}
              onDelete={handleDeleteStudent}
              onEdit={handleEditStudent}
            />
          </>
        )}
      </div>
    </div>
  );
}

export default App;
