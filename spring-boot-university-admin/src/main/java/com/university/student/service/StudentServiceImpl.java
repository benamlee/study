package com.university.student.service;

// ==========================================
// StudentServiceImpl.java - 学生业务实现
//
// ★ 对比三个版本的服务层 ★
//
//   Java EE 版 (StudentServiceBean.java)：
//     @Stateless                       ← EJB 标注
//     @PersistenceContext EntityManager em  ← 容器注入
//     em.persist() / em.createQuery()  ← 手写 JPQL
//
//   Spring MVC 版 (StudentServiceImpl.java)：
//     @Service                         ← Spring 标注
//     @Transactional                    ← 声明式事务
//     @PersistenceContext EntityManager em  ← Spring 注入
//     em.persist() / em.createQuery()  ← 手写 JPQL（同上！）
//
//   Spring Boot 版（本文件）：
//     @Service                         ← 同 Spring MVC
//     @Transactional                    ← 同 Spring MVC
//     private final StudentRepository repo  ← 注入 Repository！
//     repo.save() / repo.findAll()     ← 一行代码完成 CRUD！
//     不需要写任何 JPQL！
//
//  总结：Java EE 和 Spring MVC 都要手写 JPQL，
//  而 Spring Boot（Spring Data JPA）自动生成！
// ==========================================

import com.university.student.entity.Student;
import com.university.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
// @Service = Spring 的业务层注解
// 和 Java EE 版的 @Stateless 作用类似

@Transactional
// @Transactional = 声明式事务管理
// 类级别：所有方法都在事务中执行
// 方法成功 → 自动提交；抛异常 → 自动回滚
// 相当于 Java EE 中 EJB 的容器管理事务（CMT）

@RequiredArgsConstructor
// @RequiredArgsConstructor = Lombok 注解
// 自动生成构造函数，参数为所有 final 字段
// 这就实现了"构造器注入"！
//
// 对比 Spring MVC 版：
//   @Autowired
//   private StudentService studentService;  ← 字段注入
//
// 对比 Spring Boot 版（本文件）：
//   private final StudentRepository studentRepo;  ← 构造器注入
//
// 构造器注入 vs 字段注入：
//   1. 构造器注入：Bean 初始化时必须提供所有依赖
//      不可能出现 null，更安全
//   2. 构造器注入：方便编写单元测试（直接 new + 传参）
//   3. 字段注入：依赖隐藏在类的内部，不直观
//
// 结论：Spring 官方推荐构造器注入！

@Slf4j
// @Slf4j = Lombok 注解
// 自动生成 log 对象（private static final Logger log）
// 相当于：
//   private static final Logger log = LoggerFactory.getLogger(StudentServiceImpl.class);
// 比 System.out.println() 更专业
public class StudentServiceImpl implements StudentService {

    // ==========================================
    // ★ 关键变化：Repository 替代 EntityManager ★
    //
    // Spring MVC 版：
    //   @PersistenceContext private EntityManager em;
    //   em.persist(student);
    //
    // Spring Boot 版：
    //   private final StudentRepository studentRepo;
    //   studentRepo.save(student);
    //
    // Repository 是 Spring Data JPA 的核心概念，
    // 它是一个接口，Spring 在运行时自动生成实现。
    // ==========================================
    private final StudentRepository studentRepo;

    // 注意：不需要写构造函数！
    // @RequiredArgsConstructor 自动生成：
    //   public StudentServiceImpl(StudentRepository studentRepo) {
    //       this.studentRepo = studentRepo;
    //   }

    @Override
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() {
        log.debug("查询所有学生");
        // findAll() 由 JpaRepository 提供
        // 自动生成：SELECT s FROM Student s
        return studentRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Student getStudent(Long id) {
        log.debug("查询学生 ID: {}", id);
        // findById() 返回 Optional<Student>
        // orElse(null) 表示找不到时返回 null
        return studentRepo.findById(id).orElse(null);
    }

    @Override
    public Student addStudent(Student student) {
        log.info("新增学生: {}", student.getStudentNo());
        // save() = 新增（id 为 null 时）或 更新（id 不为 null 时）
        return studentRepo.save(student);
    }

    @Override
    public Student updateStudent(Student student) {
        log.info("更新学生 ID: {}", student.getId());
        return studentRepo.save(student);
    }

    @Override
    public void deleteStudent(Long id) {
        log.info("删除学生 ID: {}", id);
        studentRepo.deleteById(id);
    }
}
