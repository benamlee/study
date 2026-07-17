package com.university.finance.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

// ==========================================
// Tuition.java - JPA 实体类（学费缴费记录表）
//
// 记录每个学生的缴费情况
// 关联学生 ID，记录缴费金额和日期
// ==========================================

@Entity
@Table(name = "TUITION")
public class Tuition implements Serializable {

    private static final long serialVersionUID = 1L;

    // 主键：自动递增
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "TUITION_SEQ")
    @SequenceGenerator(name = "TUITION_SEQ", sequenceName = "TUITION_SEQ", initialValue = 1, allocationSize = 1)
    private Long id;

    // ==========================================
    // 关联学生：用 studentId 字段关联到 STUDENTS 表
    // 这是一种"手动关联"方式（记录学生ID，而不是JPA外键）
    // 为了保持简单易懂，我们没有用 @ManyToOne 关联映射
    // ==========================================
    @Column(name = "student_id", nullable = false)
    private Long studentId;     // 学生ID（关联到 Student 的 id）

    @Column(length = 20)
    private String studentNo;   // 学号（冗余字段，方便显示）

    @Column(length = 50)
    private String studentName; // 学生姓名（冗余字段，方便显示）

    // 缴费金额（单位：分，避免浮点数精度问题）
    // 实际显示时除以 100 得到元
    // 例如：50000 分 = 500.00 元
    @Column(nullable = false)
    private Integer amount;     // 缴费金额（分）

    // 缴费学期（如 "2024-2025-1" 表示 2024-2025 学年第一学期）
    @Column(length = 20)
    private String semester;

    // 缴费日期：记录何时缴费
    @Temporal(TemporalType.DATE)
    @Column(name = "payment_date")
    private Date paymentDate;

    // ==========================================
    // 构造方法
    // ==========================================
    public Tuition() {
    }

    public Tuition(Long studentId, String studentNo, String studentName,
                   Integer amount, String semester, Date paymentDate) {
        this.studentId = studentId;
        this.studentNo = studentNo;
        this.studentName = studentName;
        this.amount = amount;
        this.semester = semester;
        this.paymentDate = paymentDate;
    }

    // ==========================================
    // Getter / Setter
    // ==========================================
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }

    public String getStudentNo() { return studentNo; }
    public void setStudentNo(String studentNo) { this.studentNo = studentNo; }

    public String getStudentName() { return studentName; }
    public void setStudentName(String studentName) { this.studentName = studentName; }

    public Integer getAmount() { return amount; }
    public void setAmount(Integer amount) { this.amount = amount; }

    // 为了方便显示，加一个获取"元"金额的方法
    // 注意：方法名 getAmountYuan 在 JSP 中用 ${tuition.amountYuan} 访问
    public double getAmountYuan() {
        return amount != null ? amount / 100.0 : 0.0;
    }

    public String getSemester() { return semester; }
    public void setSemester(String semester) { this.semester = semester; }

    public Date getPaymentDate() { return paymentDate; }
    public void setPaymentDate(Date paymentDate) { this.paymentDate = paymentDate; }

    @Override
    public String toString() {
        return "Tuition{id=" + id + ", studentNo='" + studentNo + "', amount=" + amount + "}";
    }
}
