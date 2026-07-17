package com.university.finance.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "TUITION")
// 缴费记录表
// 记录每个学生的学费缴纳情况

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Tuition implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_id", nullable = false)
    @NotNull(message = "学生不能为空")
    private Long studentId;

    @Column(length = 20)
    private String studentNo;

    @Column(length = 50)
    private String studentName;

    @Column(nullable = false)
    @NotNull(message = "金额不能为空")
    private Integer amount;
    // 金额以"分"为单位存储（避免浮点数精度问题）
    // 例：1000 = 10.00 元
    // 这是金融系统的标准做法

    @Column(length = 20)
    @NotBlank(message = "学期不能为空")
    private String semester;

    @Temporal(TemporalType.DATE)
    @Column(name = "payment_date")
    private Date paymentDate;

    // ==========================================
    // getAmountYuan() - 将金额从"分"转换为"元"
    //
    // 注意：这个方法不是 Lombok 自动生成的，
    // 而是我们手动加的"计算属性"。
    // 因为 amount 是分（int），
    // 但 JSP 页面需要显示元（double）。
    //
    // @Data 不会覆盖手动写的方法，
    // 所以 getAmountYuan() 会正常生效。
    // JSP 中可以直接用 ${t.amountYuan} 访问。
    // ==========================================
    public double getAmountYuan() {
        return amount != null ? amount / 100.0 : 0.0;
    }

    // toString() 由 @Data 生成
    // 但我们可以覆盖它来自定义格式
    @Override
    public String toString() {
        return "Tuition{id=" + id + ", studentNo='" + studentNo +
               "', amount=" + amount + "}";
    }
}
