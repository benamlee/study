package com.university.staff.service;

import com.university.staff.entity.Staff;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
@Transactional
public class StaffServiceImpl implements StaffService {

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
