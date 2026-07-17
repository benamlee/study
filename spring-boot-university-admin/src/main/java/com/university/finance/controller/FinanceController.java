package com.university.finance.controller;

import com.university.finance.entity.Tuition;
import com.university.finance.service.FinanceService;
import com.university.student.service.StudentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/finance")
@RequiredArgsConstructor
@Slf4j
public class FinanceController {

    private final FinanceService financeService;
    private final StudentService studentService;

    @GetMapping("/list")
    public String list(Model model) {
        log.info("查询缴费记录列表");
        model.addAttribute("tuitions", financeService.getAllTuitions());
        return "finance/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        model.addAttribute("tuition", new Tuition());
        return "finance/form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        log.info("删除缴费记录 ID: {}", id);
        financeService.deleteTuition(id);
        return "redirect:/finance/list";
    }

    @PostMapping("/save")
    public String save(@RequestParam Long studentId,
                       @RequestParam String studentNo,
                       @RequestParam String studentName,
                       @RequestParam String amount,
                       @RequestParam String semester,
                       Model model) {
        // ==========================================
        // 注意：这里没有使用 @ModelAttribute Tuition，
        // 而是用 @RequestParam 逐个接收参数。
        //
        // 原因：Tuition.amount 以"分"为单位存储，
        // 但页面输入的是"元"，需要手动转换。
        //
        // 对比 Spring MVC 版：完全相同的实现方式。
        // ==========================================

        log.info("新增缴费记录: 学生={}, 金额={}", studentNo, amount);

        Tuition tuition = new Tuition();
        tuition.setStudentId(studentId);
        tuition.setStudentNo(studentNo);
        tuition.setStudentName(studentName);
        // 元 → 分转换（避免浮点数精度问题）
        if (amount != null && !amount.isEmpty()) {
            double yuan = Double.parseDouble(amount);
            tuition.setAmount((int) (yuan * 100));
        }
        tuition.setSemester(semester);
        tuition.setPaymentDate(new Date());

        financeService.addTuition(tuition);
        return "redirect:/finance/list";
    }
}
