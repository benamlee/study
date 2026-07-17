package com.university.student.entity;

// ==========================================
// Student.java - JPA 实体类（学生表）
//
// 【Lombok 版】对比 Spring MVC 版：
//
//   Spring MVC 版：手动写 getter/setter
//                  手动写 2 个构造函数
//                  手动写 toString()
//                  共约 70 行代码
//
//   Spring Boot 版（本文件）：
//    @Data = 自动生成所有 getter + setter + toString + equals + hashCode
//    @NoArgsConstructor = 自动生成无参构造（JPA 需要）
//    @AllArgsConstructor = 自动生成全参构造
//    @Builder = 自动生成 Builder 模式
//
//    Lombok 在编译时自动生成这些代码，
//    反编译 .class 文件可以看到和手动写的完全一样。
//
// 注意：@Builder 需要 @AllArgsConstructor 配合使用。
// ==========================================

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "STUDENTS")
// @Entity = JPA 实体类（和 Java EE / Spring MVC 版完全一样）
// @Table = 数据库表名

@Data
// @Data = Lombok 注解，编译时自动生成：
//   - getter（所有字段的 getXxx() 方法）
//   - setter（所有字段的 setXxx() 方法）
//   - toString()
//   - equals() + hashCode()
//   相当于以前手动写的 40~50 行代码。

@NoArgsConstructor
// @NoArgsConstructor = 生成无参构造函数
// JPA 规范要求实体类必须有午餐构造器。

@AllArgsConstructor
// @AllArgsConstructor = 生成全参构造函数
// 配合 @Builder 使用。

@Builder
// @Builder = 生成 Builder 模式
// 创建对象时可以这样写：
//   Student s = Student.builder()
//                  .studentNo("2024001")
//                  .name("张三")
//                  .build();
// 比 new + setter 更简洁优雅。
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    // @Id = 主键
    // @GeneratedValue = 主键自动生成（自增）
    private Long id;

    @Column(name = "student_no", length = 20, nullable = false, unique = true)
    @NotBlank(message = "学号不能为空")
    // @NotBlank = Bean Validation 校验
    // Spring Boot 自动集成 Hibernate Validator
    // 如果学号为空，自动返回错误信息到表单
    private String studentNo;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "姓名不能为空")
    private String name;

    @Column(length = 10)
    private String gender;

    @Column(name = "enroll_year")
    @NotNull(message = "入学年份不能为空")
    private Integer enrollYear;

    // 注意：没有手动写任何 getter/setter/constructor！
    // @Data 和 @NoArgsConstructor @AllArgsConstructor @Builder
    // 在编译时自动生成。
    //
    // 如果你在 IDE 中看到报错（找不到 getter/setter），
    // 说明没有安装 Lombok 插件。
    // VSCode 默认支持；IDEA 需要安装 Lombok 插件。
}
