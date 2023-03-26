package com.gymguru.service;

import com.gymguru.controller.exception.single.PlanNotFoundException;
import com.gymguru.domain.Plan;
import com.gymguru.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanService {
    private final PlanRepository planRepository;

    public List<Plan> getAllPlans() {
        return planRepository.findAll();
    }

    public  Plan getPlan(Long id) throws PlanNotFoundException {
        return planRepository.findById(id).orElseThrow(PlanNotFoundException::new);
    }

    public void deletePlan(Long id) {
        planRepository.deleteById(id);
    }

    public Plan savePlan(final Plan plan) {
        return planRepository.save(plan);
    }
}
