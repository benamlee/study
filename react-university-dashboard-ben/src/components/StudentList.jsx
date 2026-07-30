import StudentCard from "./StudentCard";
import { useState } from "react";

function StudentList({ students, onDelete, onEdit }) {
    const [searchTerm, setSearchTerm] = useState('');

    if (!students || students.length === 0) {
        return (
            <div className="text-center py-16 text-gray-400">
                <p className="text-lg">暫無學生資料</p>
            </div>
        );
    }

    const filteredStudents = students.filter((student) => {
        if (!searchTerm.trim()) return true;
        const keyword = searchTerm.toLowerCase().trim();
        return (
            student.name.toLowerCase().includes(keyword) ||
            student.studentNo.toLowerCase().includes(keyword)
        );
    });

    return (
        <>
        <div className="mb-4">
            <input
                type="text"
                placeholder="搜尋學生姓名或學號"
                value={searchTerm}
                onChange={(e) => setSearchTerm(e.target.value)}
                className="w-full px-4 py-2.5 border border-gray-200 rounded-lg
                        focus:outline-none focus:ring-2 focus:ring-blue-400
                        focus:border-transparent text-sm"
            />
        </div>
        {filteredStudents.length === 0 ? (
            <div className="text-center py-12 text-gray-400">
                <p>未找到符合「{searchTerm}」的學生</p>
            </div>
        ) : (
            <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
                {filteredStudents.map((student) => (
                    <StudentCard key={student.id} student={student}
                        onDelete={onDelete} onEdit={onEdit} />
                ))}
            </div>
        )}
        </>
    );
}

export default StudentList;