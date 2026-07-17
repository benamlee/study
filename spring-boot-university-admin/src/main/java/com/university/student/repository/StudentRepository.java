package com.university.student.repository;

// ==========================================
// StudentRepository.java - Spring Data JPA 仓库
//
// ★ 这是 Spring Boot 版本最大的亮点 ★
//
// 对比三个版本的数据访问层：
//
//   Java EE 版：StudentServiceBean.java
//     @PersistenceContext EntityManager em
//     em.persist(student)              ← 手动写 JPQL
//     em.createQuery("SELECT s FROM Student s", Student.class)
//     em.find(Student.class, id)
//     em.merge(student)
//     em.remove(student)
//
//   Spring MVC 版：StudentServiceImpl.java
//     @PersistenceContext EntityManager em  ← 同上！
//     代码与 Java EE 版几乎一样
//
//   Spring Boot 版（本文件）：零实现！
//     只需要 extends JpaRepository<Student, Long>
//     Spring Data JPA 在运行时自动生成实现类
//     StudentRepository 自动获得：
//       findAll()    → 查询所有
//       findById()   → 按 ID 查询
//       save()       → 新增 / 更新
//       deleteById() → 删除
//       count()      → 计数
//       ... 等等
//
// 这就是 "Spring Data JPA" 的威力：
// 只需要写接口，不需要写实现！
// ==========================================

import com.university.student.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// @Repository = Spring 的数据访问层注解
// Spring Data JPA 会在运行时自动生成
// StudentRepository 的实现类（动态代理）
//
// 即使不写 @Repository 也能工作
// （Spring Data JPA 自动注册），
// 但加上更明确，且启用 JPA 异常转换。
public interface StudentRepository extends JpaRepository<Student, Long> {
    // ==========================================
    // JpaRepository<Student, Long> 的两个泛型参数：
    //   Student = 要操作的实体类型
    //   Long    = 主键类型
    //
    // 这个接口现在就有了 20+ 个方法：
    //   findAll(), findById(), save(), deleteById()...
    //
    // 不需要写任何实现代码！
    //
    // 如果需要自定义查询，可以在这里声明方法：
    //
    //    List<Student> findByNameContaining(String keyword);
    //    // Spring Data JPA 根据方法名自动生成 JPQL：
    //    // SELECT s FROM Student s WHERE s.name LIKE %keyword%
    //
    // 不需要写 JPQL！方法名就是查询语句！
    // 这就是 "查询方法命名规范"（Query Methods）。
    // ==========================================
}
