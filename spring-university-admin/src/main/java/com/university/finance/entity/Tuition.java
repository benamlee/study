package com.university.finance.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TUITION")
public class Tuition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    private Long studentId;

    @Column(length = 20)
    private String studentNo;

    @Column(length = 50)
    private String studentName;

    @Column(nullable = false)
    private Integer amount;

    @Column(length = 20)
    private String semester;

    @Temporal(TemporalType.DATE)
    @Column(name = "payment_date")
    private Date paymentDate;

    public Tuition() {}

    public Tuition(Long studentId, String studentNo, String studentName,
                   Integer amount, String semester, Date paymentDate) {
        this.studentId = studentId;
        this.studentNo = studentNo;
        this.studentName = studentName;
        this.amount = amount;
        this.semester = semester;
        this.paymentDate = paymentDate;
    }

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
