package com.university.staff.service;

import com.university.staff.entity.Staff;
import com.university.staff.repository.StaffRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
// @RequiredArgsConstructor = 自动生成构造函数
// 参数为 private final StaffRepository staffRepo
// 等同于：
//   public StaffServiceImpl(StaffRepository staffRepo) {
//       this.staffRepo = staffRepo;
//   }

@Slf4j
// @Slf4j = 自动生成 log 日志对象
// 用法：log.info("xxx") / log.debug("xxx") / log.error("xxx")
public class StaffServiceImpl implements StaffService {

    private final StaffRepository staffRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Staff> getAllStaff() {
        log.debug("查询所有教职工");
        return staffRepo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Staff getStaff(Long id) {
        return staffRepo.findById(id).orElse(null);
    }

    @Override
    public Staff addStaff(Staff staff) {
        log.info("新增教职工: {}", staff.getStaffNo());
        return staffRepo.save(staff);
    }

    @Override
    public Staff updateStaff(Staff staff) {
        log.info("更新教职工 ID: {}", staff.getId());
        return staffRepo.save(staff);
    }

    @Override
    public void deleteStaff(Long id) {
        log.info("删除教职工 ID: {}", id);
        staffRepo.deleteById(id);
    }
}
