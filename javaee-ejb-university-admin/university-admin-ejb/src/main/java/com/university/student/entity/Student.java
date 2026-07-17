package com.university.student.entity;

import javax.persistence.*;
import java.io.Serializable;

// ==========================================
// Student.java - JPA 实体类（学生表）
//
// @Entity = 这是一个"数据库表"的映射类
// 程序启动时，JPA 会自动在数据库中创建
// 名为 STUDENTS 的表
//
// 一个实体类 = 一张数据库表
// 一个实体对象 = 表中的一行数据
// ==========================================

@Entity
// @Table：指定对应的数据库表名
@Table(name = "STUDENTS")
public class Student implements Serializable {

    // Serializable：Java EE 实体必须实现序列化接口
    private static final long serialVersionUID = 1L;

    // ==========================================
    // @Id：主键（Primary Key），每条记录的唯一标识
    // @GeneratedValue：主键自动生成策略
    //   IDENTITY = 数据库自动递增（1, 2, 3...）
    // ==========================================
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STUDENT_SEQ")
    @SequenceGenerator(name = "STUDENT_SEQ", sequenceName = "STUDENT_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    // @Column：映射到数据库表的列
    // name="student_no"      → 列名（默认是字段名）
    // length=20              → 最大长度 20 个字符
    // nullable=false         → 不能为空（NOT NULL）
    // unique=true            → 值必须唯一（UNIQUE）
    @Column(name = "student_no", length = 20, nullable = false, unique = true)
    private String studentNo;   // 学号

    @Column(nullable = false, length = 50)
    private String name;        // 姓名

    @Column(length = 10)
    private String gender;      // 性别（"男"或"女"）

    @Column(name = "enroll_year")
    private Integer enrollYear; // 入学年份（如 2024）

    // ==========================================
    // 构造方法
    // ==========================================
    public Student() {
        // JPA 要求实体类必须有一个无参构造函数
    }

    // 带参构造函数：方便快速创建对象
    public Student(String studentNo, String name, String gender, Integer enrollYear) {
        this.studentNo = studentNo;
        this.name = name;
        this.gender = gender;
        this.enrollYear = enrollYear;
    }

    // ==========================================
    // Getter 和 Setter 方法
    // Java Bean 规范要求每个属性都有
    // getXxx() 和 setXxx() 方法
    // JPA 和 JSP EL 表达式都依赖 getter
    // ==========================================

    // getter：获取属性值
    public Long getId() { return id; }
    // setter：设置属性值
    public void setId(Long id) { this.id = id; }

    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Integer getEnrollYear() { return enrollYear; }
    public void setEnrollYear(Integer enrollYear) { this.enrollYear = enrollYear; }

    // ==========================================
    // toString：打印对象时显示内容，方便调试
    // ==========================================
    @Override
    public String toString() {
        return "Student{id=" + id + ", studentNo='" + studentNo + "', name='" + name + "'}";
    }
}
