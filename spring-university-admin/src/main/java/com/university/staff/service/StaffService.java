package com.university.staff.service;

import com.university.staff.entity.Staff;
import java.util.List;

public interface StaffService {
    void addStaff(Staff staff);
    List<Staff> getAllStaff();
    Staff getStaff(Long id);
    void updateStaff(Staff staff);
    void deleteStaff(Long id);
}
