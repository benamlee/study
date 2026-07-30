import { useState } from "react";

const emptyStudent = {
    studentNo: '',
    name: '',
    gender: '男',
    enrollYear: new Date().getFullYear(),
};

function StudentForm({ initialData, onSubmit, onCancel }) {
    const [formData, setFormData] = useState({
        ...emptyStudent,
        ...initialData,
    });
    const [errors, setErrors] = useState({});
    const [submitted, setSubmitted] = useState(false);

    const isEditing = !!initialData?.id;

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData((prev) => ({ ...prev, [name]: value }));

        if (errors[name]) {
            setErrors((prev) => {
                const newErrors = { ...prev };
                delete newErrors[name];
                return newErrors;
            });
        }
    };

    const validate = () => {
        const newErrors = {};
        if (!formData.studentNo.trim()) newErrors.studentNo = '學號不能為空';
        else if (formData.studentNo.trim().length < 4)
            newErrors.studentNo = '學號至少 4 碼';

        if (!formData.name.trim()) newErrors.name = '姓名不能為空';
        else if (formData.name.trim().length < 2)
            newErrors.name = '姓名至少 2 個字';

        if (!formData.gender) newErrors.gender = '請選擇性別';

        if (!formData.enrollYear) newErrors.enrollYear = '請輸入入學年份';
        else if (isNaN(formData.enrollYear) || formData.enrollYear < 1900 || formData.enrollYear > 2100)
            newErrors.enrollYear = '請輸入有效年份（1900-2100）';

        return newErrors;
    };

    const handleSubmit = (e) => {
        e.preventDefault();
        setSubmitted(true);
        const validationErrors = validate();
        setErrors(validationErrors);

        if (Object.keys(validationErrors).length === 0) {
            onSubmit({ ...formData, enrollYear: Number(formData.enrollYear) });
        }
    };

    const fieldClass = (fieldName) => {
        const hasError = submitted && errors[fieldName];
        return `w-full px-3 py-2 border rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-400 transition-colors ${
            hasError ? 'border-red-300 bg-red-50' : 'border-gray-200 bg-white'
        }`;
    };

    return (
        <form onSubmit={handleSubmit} className="bg-white rounded-lg p-6 shadow-md">
            <h2 className="text-lg font-semibold text-gray-800 mb-4">
                {isEditing ? '編輯學生' : '新增學生'}
            </h2>

            <div className="mb-4">
                <label className="block text-sm font-medium text-gray-600 mb-1">學號</label>
                <input type="text" name="studentNo" value={formData.studentNo}
                    onChange={handleChange} placeholder="請輸入學號"
                    className={fieldClass('studentNo')} />
                {submitted && errors.studentNo && (
                    <p className="text-red-500 text-xs mt-1">{errors.studentNo}</p>
                )}
            </div>


            <div className="mb-4">
                <label className="block text-sm font-medium text-gray-600 mb-1">姓名</label>
                <input type="text" name="name" value={formData.name}
                    onChange={handleChange} placeholder="請輸入姓名"
                    className={fieldClass('name')} />
                {submitted && errors.name && (
                    <p className="text-red-500 text-xs mt-1">{errors.name}</p>
                )}
            </div>

            <div className="mb-4">
                <label className="block text-sm font-medium text-gray-600 mb-1">性別</label>
                <select name="gender" value={formData.gender}
                    onChange={handleChange} className={fieldClass('gender')}>
                    <option value="男">男</option>
                    <option value="女">女</option>
                </select>
                {submitted && errors.gender && (
                    <p className="text-red-500 text-xs mt-1">{errors.gender}</p>
                )}
            </div>

            <div className="mb-6">
                <label className="block text-sm font-medium text-gray-600 mb-1">入學年份</label>
                <input type="number" name="enrollYear" value={formData.enrollYear}
                    onChange={handleChange} placeholder="如 2024"
                    className={fieldClass('enrollYear')} />
                {submitted && errors.enrollYear && (
                    <p className="text-red-500 text-xs mt-1">{errors.enrollYear}</p>
                )}
            </div>

            <div className="flex gap-3">
                <button type="submit"
                className="flex-1 bg-blue-500 text-white px-4 py-2 rounded-lg text-sm font-medium
                            hover:bg-blue-600 transition-colors">
                    儲存
                </button>
                {onCancel && (
                    <button type="button" onClick={onCancel}
                        className="flex-1 bg-gray-100 text-gray-600 px-4 py-2 rounded-lg text-sm
                                hover:bg-gray-200 transition-colors">
                        取消
                    </button>
                )}
            </div>
        </form>
    );
}

export default StudentForm;