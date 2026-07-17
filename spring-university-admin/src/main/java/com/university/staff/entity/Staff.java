package com.university.staff.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "STAFF")
public class Staff implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "staff_no", length = 20, nullable = false, unique = true)
    private String staffNo;

    @Column(nullable = false, length = 50)
    private String name;

    @Column(length = 50)
    private String department;

    @Column(length = 50)
    private String position;

    public Staff() {}

    public Staff(String staffNo, String name, String department, String position) {
        this.staffNo = staffNo;
        this.name = name;
        this.department = department;
        this.position = position;
    }

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
