import { useState } from "react";

function StudentCard({ student, onDelete, onEdit }) {
    const [expanded, setExpanded] = useState(false);
    const [liked, setLiked] = useState(false);

    const toggleExpand = (e) => {
        e.stopPropagation();
        setExpanded(!expanded);
    };

    const toggleLike = (e) => {
        e.stopPropagation(); // 阻止冒泡到上層 → 觸發 toggleExpand
        setLiked(!liked);
    }

    return (
        <div
            className={`bg-white rounded-lg shadow-md p-4 cursor-pointer hover:shadow-lg transition-all duration-200 border-l-4 ${liked ? 'border-red-400' : 'border-blue-400'}`}
            onClick={toggleExpand}
        >
            <div className="flex justify-between items-center mb-2">
                <span className="text-xs text-gray-400 bg-gray-100 px-2 py-1 rounded">
                    {student.studentNo}
                </span>
                <button
                    onClick={toggleLike}
                    className={`text-sm px-3 py-1 rounded-full transition-colors ${liked ? 'bg-red-100 text-red-500' : 'bg-gray-100 text-gray-400 hover:bg-red-50'}`}
                >
                    {liked ? '❤️ 已讚' : '🤍 讚'}
                </button>
            </div>

            <h3 className="text-lg font-semibold text-gray-800 mt-1">{student.name}</h3>
            <div className="flex gap-3 text-sm text-gray-500 mt-1">
                <span>{student.enrollYear}級</span>
                <span>{student.gender}</span>
            </div>

            {expanded && (
                <>
                    <div className="mt-3 pt-3 border-t border-gray-100 text-sm text-gray-700">
                        <p>ID: {student.id}</p>
                        <p>學號: {student.studentNo}</p>
                        <p>性別: {student.gender}</p>
                        <p>入學年份: {student.enrollYear}</p>
                    </div>
                    <div className="flex gap-2 mt-3">
                        <button onClick={(e) => { e.stopPropagation(); onEdit(student); }}
                            className="flex-1 bg-blue-50 text-blue-600 px-3 py-1.5 rounded text-sm hover:bg-blue-100 transition-colors">
                                編輯
                        </button>
                        <button onClick={(e) => { e.stopPropagation(); onDelete(student.id); }}
                            className="flex-1 bg-red-50 text-red-500 px-3 py-1.5 rounded text-sm hover:bg-red-100 transition-colors">
                                刪除
                        </button>
                    </div>
                </>
            )}

            <div className="text-center mt-2 text-xs text-gray-300">
                {expanded ? '▲ 點擊收起' : '▼ 點擊展開'}
            </div>
        </div>
    );
}

export default StudentCard;