package com.university.finance.service;

import com.university.finance.entity.Tuition;
import java.util.List;

public interface FinanceService {
    void addTuition(Tuition tuition);
    List<Tuition> getAllTuitions();
    Tuition getTuition(Long id);
    List<Tuition> getTuitionsByStudent(Long studentId);
    void deleteTuition(Long id);
}
