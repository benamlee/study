package com.university.staff.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Entity
@Table(name = "STAFF")
// @Entity = JPA 实体类
// @Table = 对应数据库 STAFF 表

@Data
// @Data = Lombok，自动生成 getter/setter/toString/equals/hashCode
// 替代 Spring MVC 版中手动写的 35 行样板代码

@NoArgsConstructor
// 无参构造（JPA 要求）

@AllArgsConstructor
// 全参构造（配合 @Builder）

@Builder
// 建造者模式：Staff.builder().staffNo("T001").name("李四").build()
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "staff_no", length = 20, nullable = false, unique = true)
    @NotBlank(message = "工号不能为空")
    private String staffNo;

    @Column(nullable = false, length = 50)
    @NotBlank(message = "姓名不能为空")
    private String name;

    @Column(length = 50)
    private String department;

    @Column(length = 50)
    private String position;

    // 没有手动写的代码！
    // Lombok 自动生成所有 getter/setter/constructor/builder
}
