package com.university.staff.session;

import com.university.staff.entity.Staff;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// ==========================================
// StaffServiceBean.java - 教职工 EJB 业务实现
//
// @Stateless：无状态会话 Bean
// 和 StudentServiceBean 结构完全一样
// 只是操作的是 Staff 实体
// ==========================================

@Stateless
@Local(StaffService.class)
public class StaffServiceBean implements StaffService {

    @PersistenceContext(unitName = "UniversityPU")
    private EntityManager em;

    @Override
    public void addStaff(Staff staff) {
        em.persist(staff);
    }

    @Override
    public List<Staff> getAllStaff() {
        return em.createQuery(
            "SELECT s FROM Staff s ORDER BY s.id DESC", Staff.class)
            .getResultList();
    }

    @Override
    public Staff getStaff(Long id) {
        return em.find(Staff.class, id);
    }

    @Override
    public void updateStaff(Staff staff) {
        em.merge(staff);
    }

    @Override
    public void deleteStaff(Long id) {
        Staff staff = em.find(Staff.class, id);
        if (staff != null) {
            em.remove(staff);
        }
    }
}
