package com.university.staff.controller;

import com.university.staff.entity.Staff;
import com.university.staff.service.StaffService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("/staff")
@RequiredArgsConstructor
@Slf4j
public class StaffController {

    private final StaffService staffService;

    @GetMapping("/list")
    public String list(Model model) {
        log.info("查询教职工列表");
        model.addAttribute("staffList", staffService.getAllStaff());
        return "staff/list";
    }

    @GetMapping("/add")
    public String addForm(Model model) {
        model.addAttribute("staff", new Staff());
        return "staff/form";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam Long id, Model model) {
        log.debug("编辑教职工 ID: {}", id);
        Staff staff = staffService.getStaff(id);
        if (staff == null) {
            log.warn("教职工不存在 ID: {}", id);
            return "redirect:/staff/list";
        }
        model.addAttribute("staff", staff);
        return "staff/form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        log.info("删除教职工 ID: {}", id);
        staffService.deleteStaff(id);
        return "redirect:/staff/list";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute Staff staff,
                       BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("errors", result);
            return "staff/form";
        }

        if (staff.getId() == null) {
            log.info("新增教职工: {}", staff.getStaffNo());
            staffService.addStaff(staff);
        } else {
            log.info("更新教职工 ID: {}", staff.getId());
            staffService.updateStaff(staff);
        }
        return "redirect:/staff/list";
    }
}
