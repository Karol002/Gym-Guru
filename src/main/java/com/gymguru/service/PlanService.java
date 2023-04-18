package com.gymguru.service;

import com.gymguru.controller.exception.single.PlanNotFoundException;
import com.gymguru.domain.Plan;
import com.gymguru.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PlanService {
    private final PlanRepository planRepository;

    public List<Plan> getAllPlansByTrainerId(Long trainerId) {
        return planRepository.findAllByTrainerId(trainerId);
    }

    public Plan getPlanByUserId(Long userId) throws PlanNotFoundException {
            return planRepository.findByUserId(userId).orElseThrow(PlanNotFoundException::new);
    }

    public Plan getPlanById(Long id) throws PlanNotFoundException {
        return planRepository.findById(id).orElseThrow(PlanNotFoundException::new);
    }

    @Transactional
    public Plan savePlan(final Plan plan) {
        return planRepository.save(plan);
    }

    @Transactional
    public Plan updatePlan(Plan plan) throws PlanNotFoundException {
        if (planRepository.existsById(plan.getId())) {
            Plan existPlan = getPlanById(plan.getId());
            existPlan.setDietDescription(plan.getDietDescription());
            existPlan.setExerciseDescription(plan.getExerciseDescription());
            return planRepository.save(existPlan);
        } else throw new PlanNotFoundException();
    }
}
