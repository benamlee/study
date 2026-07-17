package com.university.finance.service;

import com.university.finance.entity.Tuition;
import com.university.finance.repository.TuitionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class FinanceServiceImpl implements FinanceService {

    private final TuitionRepository tuitionRepo;

    @Override
    @Transactional(readOnly = true)
    public List<Tuition> getAllTuitions() {
        log.debug("查询所有缴费记录");
        return tuitionRepo.findAll();
    }

    @Override
    public Tuition addTuition(Tuition tuition) {
        log.info("新增缴费记录: 学生{} {}元",
                tuition.getStudentNo(), tuition.getAmount());
        return tuitionRepo.save(tuition);
    }

    @Override
    public void deleteTuition(Long id) {
        log.info("删除缴费记录 ID: {}", id);
        tuitionRepo.deleteById(id);
    }
}
