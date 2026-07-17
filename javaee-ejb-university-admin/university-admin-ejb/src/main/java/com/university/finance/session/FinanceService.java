package com.university.finance.session;

import com.university.finance.entity.Tuition;
import java.util.List;

// ==========================================
// FinanceService.java - 财务 EJB 业务接口
// ==========================================
public interface FinanceService {

    /** 添加一条缴费记录 */
    void addTuition(Tuition tuition);

    /** 查询所有缴费记录 */
    List<Tuition> getAllTuitions();

    /** 根据 ID 查找缴费记录 */
    Tuition getTuition(Long id);

    /** 根据学生 ID 查询该学生的缴费记录 */
    List<Tuition> getTuitionsByStudent(Long studentId);

    /** 删除缴费记录 */
    void deleteTuition(Long id);
}
