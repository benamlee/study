package com.university.student.service;

import com.university.student.entity.Student;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// ==========================================
// StudentServiceImpl.java - 学生业务实现
//
// 对比 Java EE 版本 (StudentServiceBean.java):
//
//   Java EE: @Stateless                → 容器管理 EJB 生命周期
//   Spring:  @Service                  → Spring 管理 Bean 生命周期
//
//   Java EE: 默认就有容器管理事务（CMT）
//   Spring:  @Transactional            → Spring 管理事务
//
//   Java EE: @PersistenceContext       → WebSphere 注入 EntityManager
//   Spring:  @PersistenceContext       → Spring 注入 EntityManager
//
// 注意：虽然写法看起来很像，但底层实现不同！
// Java EE 的 EntityManager 由 WebSphere 的 JPA 容器管理
// Spring 的 EntityManager 由 Hibernate + Spring ORM 管理
// 但对开发者来说，使用方式完全一样。 
// ==========================================

@Service
// @Service = 标记为 Spring 的业务服务组件
// Spring 会自动创建这个类的实例（单例）
// 相当于 Java EE 的 @Stateless

@Transactional
// @Transactional = 声明式事务管理
// 这个类中的所有方法都会在事务中执行
// 方法成功 → 提交事务；抛异常 → 回滚事务
// 相当于 Java EE 中 EJB 的容器管理事务（CMT）
public class StudentServiceImpl implements StudentService {

    // @PersistenceContext = 注入 JPA 的 EntityManager
    // Spring 认识这个注解（通过 PersistenceAnnotationBeanPostProcessor）
    // 所以它和 Java EE 版本的效果一样
    @PersistenceContext(unitName = "UniversityPU")
    private EntityManager em;

    @Override
    public void addStudent(Student student) {
        em.persist(student);
    }

    @Override
    public List<Student> getAllStudents() {
        return em.createQuery(
            "SELECT s FROM Student s ORDER BY s.id DESC", Student.class)
            .getResultList();
    }

    @Override
    public Student getStudent(Long id) {
        return em.find(Student.class, id);
    }

    @Override
    public void updateStudent(Student student) {
        em.merge(student);
    }

    @Override
    public void deleteStudent(Long id) {
        Student student = em.find(Student.class, id);
        if (student != null) {
            em.remove(student);
        }
    }
}
