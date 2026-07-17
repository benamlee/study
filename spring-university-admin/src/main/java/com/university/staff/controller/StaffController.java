package com.university.staff.controller;

import com.university.staff.entity.Staff;
import com.university.staff.service.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    private StaffService staffService;

    @GetMapping("/list")
    public String list(Model model) {
        model.addAttribute("staffList", staffService.getAllStaff());
        return "staff/list";
    }

    @GetMapping("/add")
    public String addForm() {
        return "staff/form";
    }

    @GetMapping("/edit")
    public String editForm(@RequestParam Long id, Model model) {
        model.addAttribute("staff", staffService.getStaff(id));
        return "staff/form";
    }

    @GetMapping("/delete")
    public String delete(@RequestParam Long id) {
        staffService.deleteStaff(id);
        return "redirect:/staff/list";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute Staff staff) {
        if (staff.getId() == null) {
            staffService.addStaff(staff);
        } else {
            staffService.updateStaff(staff);
        }
        return "redirect:/staff/list";
    }
}
