package com.university.student.session;

import com.university.student.entity.Student;
import javax.annotation.sql.DataSourceDefinition;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// H2 DataSource: server.xml 中的 H2JDBCLib 提供 JDBC 驱动
@DataSourceDefinition(
    name = "java:app/env/jdbc/UniversityDS",
    className = "org.h2.jdbcx.JdbcDataSource",
    url = "jdbc:h2:file:./universitydb;DB_CLOSE_DELAY=-1",
    user = "sa",
    password = "sa"
)

@Stateless
@Local(StudentService.class)
public class StudentServiceBean implements StudentService {

    // ==========================================
    // @PersistenceContext = 注入 JPA 的 EntityManager
    // EntityManager = "数据库操作手柄"
    // 通过它执行 CRUD（增删改查）
    //
    // unitName = "UniversityPU" 对应
    // persistence.xml 中的
    // <persistence-unit name="UniversityPU">
    // ==========================================
    @PersistenceContext(unitName = "UniversityPU")
    private EntityManager em;

    // ==========================================
    // 添加学生
    // em.persist(对象) = 把对象保存到数据库
    // 相当于 INSERT INTO STUDENTS ...
    // ==========================================
    @Override
    public void addStudent(Student student) {
        em.persist(student);
    }

    // ==========================================
    // 查询所有学生
    // em.createQuery("JPQL", 类型) = 执行 JPQL 查询
    //
    // JPQL = Java Persistence Query Language
    // 类似 SQL，但操作的是 Java 对象而不是表
    // "SELECT s FROM Student s" = "查询所有 Student 对象"
    // ORDER BY s.id DESC = 按 ID 降序（最新的在前面）
    // ==========================================
    @Override
    public List<Student> getAllStudents() {
        return em.createQuery(
            "SELECT s FROM Student s ORDER BY s.id DESC", Student.class)
            .getResultList();
    }

    // ==========================================
    // 根据 ID 查找学生
    // em.find(类.class, id) = 按主键查找
    // ==========================================
    @Override
    public Student getStudent(Long id) {
        return em.find(Student.class, id);
    }

    // ==========================================
    // 更新学生信息
    // em.merge(对象) = 更新已有记录
    // 相当于 UPDATE STUDENTS SET ... WHERE id=?
    // ==========================================
    @Override
    public void updateStudent(Student student) {
        em.merge(student);
    }

    // ==========================================
    // 删除学生
    // 1. em.find() 先找到要删除的对象
    // 2. em.remove() 删除对象
    // 相当于 DELETE FROM STUDENTS WHERE id=?
    // ==========================================
    @Override
    public void deleteStudent(Long id) {
        Student student = em.find(Student.class, id);
        if (student != null) {
            em.remove(student);
        }
    }
}
