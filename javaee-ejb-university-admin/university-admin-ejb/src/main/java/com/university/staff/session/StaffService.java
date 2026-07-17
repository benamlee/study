package com.university.staff.session;

import com.university.staff.entity.Staff;
import java.util.List;

// ==========================================
// StaffService.java - 教职工 EJB 业务接口
// ==========================================
public interface StaffService {

    /** 添加一名教职工 */
    void addStaff(Staff staff);

    /** 查询所有教职工 */
    List<Staff> getAllStaff();

    /** 根据 ID 查找单个教职工 */
    Staff getStaff(Long id);

    /** 更新教职工信息 */
    void updateStaff(Staff staff);

    /** 根据 ID 删除教职工 */
    void deleteStaff(Long id);
}
