package com.v1.gymguru.service;

import com.v1.gymguru.controller.exception.single.PlanForUserIdNotFoundException;
import com.v1.gymguru.controller.exception.single.PlanNotFoundException;
import com.v1.gymguru.controller.exception.single.SubscriptionExpiredException;
import com.v1.gymguru.controller.exception.single.SubscriptionNotFoundException;
import com.v1.gymguru.domain.Plan;
import com.v1.gymguru.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PlanService {
    private final PlanRepository planRepository;

    public Plan getPlanByUserId(Long userId) throws PlanForUserIdNotFoundException {
            return planRepository.findByUserId(userId).orElseThrow(PlanForUserIdNotFoundException::new);
    }

    public Plan getPlan(Long id) throws PlanNotFoundException {
        return planRepository.findById(id).orElseThrow(PlanNotFoundException::new);
    }

    public void deletePlan(Long id) throws PlanNotFoundException {
        if (planRepository.existsById(id)) {
            planRepository.deleteById(id);
        } else throw new PlanNotFoundException();
    }

    public Plan savePlan(final Plan plan) {
        return planRepository.save(plan);
    }

    public Plan updatePlan(final Plan plan) throws PlanNotFoundException {
        if (planRepository.existsById(plan.getId())) {
            return planRepository.save(plan);
        } else throw new PlanNotFoundException();
    }

    public List<Plan> getAllPlansByTrainerId(Long trainerId) {
        return planRepository.findAllByTrainerId(trainerId);
    }
}
