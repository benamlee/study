package com.university.finance.repository;

import com.university.finance.entity.Tuition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TuitionRepository extends JpaRepository<Tuition, Long> {

    // ==========================================
    // 自定义查询方法示例
    //
    // Spring Data JPA 的方法命名规范：
    //   findBy + 字段名 + 条件关键字
    //
    // 这个方法会自动生成 JPQL：
    //   SELECT t FROM Tuition t
    //   WHERE t.studentId = ?1
    //   ORDER BY t.id DESC
    //
    // 方法名就是查询语句！
    // 完全不需要写 JPQL。
    // ==========================================
    List<Tuition> findByStudentIdOrderByIdDesc(Long studentId);
    // ↑ 按学生 ID 查询缴费记录，按 ID 倒序排列

    // 还可以继续加：
    //   List<Tuition> findBySemester(String semester);
    //   List<Tuition> findByAmountGreaterThan(Integer minAmount);
    // 都是零实现！
}
