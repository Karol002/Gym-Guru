package com.gymguru.service;

import com.gymguru.controller.exception.single.PlanForUserIdNotFoundException;
import com.gymguru.controller.exception.single.PlanNotFoundException;
import com.gymguru.domain.Plan;
import com.gymguru.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanService {
    private final PlanRepository planRepository;

    public List<Plan> getAllPlansByTrainerId(Long trainerId) {
        return planRepository.findAllByTrainerId(trainerId);
    }

    public Plan getPlanByUserId(Long userId) throws PlanForUserIdNotFoundException {
            return planRepository.findByUserId(userId).orElseThrow(PlanForUserIdNotFoundException::new);
    }

    public Plan getPlanById(Long id) throws PlanNotFoundException {
        return planRepository.findById(id).orElseThrow(PlanNotFoundException::new);
    }

    public Plan savePlan(final Plan plan) {
        return planRepository.save(plan);
    }

    @Transactional
    public void updatePlan(Plan plan) throws PlanNotFoundException {
        if (planRepository.existsById(plan.getId())) {
            Plan existPlan = getPlanById(plan.getId());
            existPlan.setDietDescription(plan.getDietDescription());
            existPlan.setExerciseDescription(plan.getExerciseDescription());
            planRepository.save(existPlan);
        }
    }
}