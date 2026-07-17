package com.university.finance.controller;

import com.university.finance.entity.Tuition;
import com.university.finance.service.FinanceService;
import com.university.student.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@Controller
@RequestMapping("/finance")
public class FinanceController {

    @Autowired
    private FinanceService financeService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("tuitions", financeService.getAllTuitions());
        return "finance/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        // 传入学生列表，供缴费表单选择
        model.addAttribute("students", studentService.getAllStudents());
        return "finance/form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        financeService.deleteTuition(id);
        return "redirect:/finance/list";
    }

    // ==========================================
    // 保存缴费记录
    //
    // @RequestParam = 获取单个表单参数
    // 因为 Tuition 对象的部分数据来自前端计算
    // （如 studentId 从 select 获取后填入）
    // 所以这里手动接收参数，而不是用 @ModelAttribute
    // ==========================================
    @PostMapping("/save")
    public String save(@RequestParam Long studentId,
                       @RequestParam String studentNo,
                       @RequestParam String studentName,
                       @RequestParam String amount,
                       @RequestParam String semester) {

        Tuition tuition = new Tuition();
        tuition.setStudentId(studentId);
        tuition.setStudentNo(studentNo);
        tuition.setStudentName(studentName);
        // 元 → 分转换
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
