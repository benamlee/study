package com.university.staff.entity;

import javax.persistence.*;
import java.io.Serializable;

// ==========================================
// Staff.java - JPA 实体类（教职工表）
//
// 每个实体类对应一张数据库表
// 每个属性对应表中的一个列
// ==========================================

@Entity
@Table(name = "STAFF")
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键：自动递增
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "STAFF_SEQ")
    @SequenceGenerator(name = "STAFF_SEQ", sequenceName = "STAFF_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    // 工号（唯一且不能为空）
    @Column(name = "staff_no", length = 20, nullable = false, unique = true)
    private String staffNo;

    // 姓名
    @Column(nullable = false, length = 50)
    private String name;

    // 部门（如"计算机学院"、"财务处"）
    @Column(length = 50)
    private String department;

    // 职位（如"教授"、"行政主管"）
    @Column(length = 50)
    private String position;

    // ==========================================
    // 构造方法
    // ==========================================
    public Staff() {
    }

    public Staff(String staffNo, String name, String department, String position) {
        this.staffNo = staffNo;
        this.name = name;
        this.department = department;
        this.position = position;
    }

    // ==========================================
    // Getter / Setter
    // ==========================================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getStaffNo() { return staffNo; }
    public void setStaffNo(String staffNo) { this.staffNo = staffNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }

    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }

    @Override
    public String toString() {
        return "Staff{id=" + id + ", staffNo='" + staffNo + "', name='" + name + "'}";
    }
}
