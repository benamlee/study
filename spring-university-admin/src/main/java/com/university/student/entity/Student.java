package com.university.student.entity;

// ==========================================
// Student.java - JPA 实体类（学生表）
//
// 这个类和 Java EE 版本完全一样！
// 因为 JPA 是标准规范（javax.persistence），
// 不管是 WebSphere + EJB 还是 Tomcat + Spring，
// @Entity @Id @Column 等注解的用法完全相同。
//
// 唯一的变化：Spring 版本用 Hibernate 作为
// JPA 实现（而不是 EclipseLink），
// 但对实体类来说没有任何区别。
// ==========================================

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "STUDENTS")
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_no", length = 20, nullable = false, unique = true)
    private String studentNo;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 10)
    private String gender;

    @Column(name = "enroll_year")
    private Integer enrollYear;

    public Student() {}

    public Student(String studentNo, String name, String gender, Integer enrollYear) {
        this.studentNo = studentNo;
        this.name = name;
        this.gender = gender;
        this.enrollYear = enrollYear;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getGender() { return gender; }
    public void setGender(String gender) { this.gender = gender; }

    public Integer getEnrollYear() { return enrollYear; }
    public void setEnrollYear(Integer enrollYear) { this.enrollYear = enrollYear; }

    @Override
    public String toString() {
        return "Student{id=" + id + ", studentNo='" + studentNo + "', name='" + name + "'}";
    }
}
