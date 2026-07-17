package com.university.finance.session;

import com.university.finance.entity.Tuition;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

// ==========================================
// FinanceServiceBean.java - 财务 EJB 业务实现
// ==========================================

@Stateless
@Local(FinanceService.class)
public class FinanceServiceBean implements FinanceService {

    @PersistenceContext(unitName = "UniversityPU")
    private EntityManager em;

    @Override
    public void addTuition(Tuition tuition) {
        em.persist(tuition);
    }

    @Override
    public List<Tuition> getAllTuitions() {
        return em.createQuery(
            "SELECT t FROM Tuition t ORDER BY t.paymentDate DESC", Tuition.class)
            .getResultList();
    }

    @Override
    public Tuition getTuition(Long id) {
        return em.find(Tuition.class, id);
    }

    @Override
    public List<Tuition> getTuitionsByStudent(Long studentId) {
        return em.createQuery(
            "SELECT t FROM Tuition t WHERE t.studentId = :sid ORDER BY t.paymentDate DESC",
            Tuition.class)
            .setParameter("sid", studentId)
            .getResultList();
    }

    @Override
    public void deleteTuition(Long id) {
        Tuition tuition = em.find(Tuition.class, id);
        if (tuition != null) {
            em.remove(tuition);
        }
    }
}
