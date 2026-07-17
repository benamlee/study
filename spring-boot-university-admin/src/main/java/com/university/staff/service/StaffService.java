package com.university.staff.service;

import com.university.staff.entity.Staff;
import java.util.List;

public interface StaffService {
    List<Staff> getAllStaff();
    Staff getStaff(Long id);
    Staff addStaff(Staff staff);
    Staff updateStaff(Staff staff);
    void deleteStaff(Long id);
}
