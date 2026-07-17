package com.university.finance.service;

import com.university.finance.entity.Tuition;
import java.util.List;

public interface FinanceService {
    List<Tuition> getAllTuitions();
    Tuition addTuition(Tuition tuition);
    void deleteTuition(Long id);
}
