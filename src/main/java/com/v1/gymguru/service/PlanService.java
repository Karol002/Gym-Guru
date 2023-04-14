package com.v1.gymguru.service;

import com.v1.gymguru.controller.exception.single.PlanForUserIdNotFoundException;
import com.v1.gymguru.controller.exception.single.PlanNotFoundException;
import com.v1.gymguru.domain.Plan;
import com.v1.gymguru.repository.PlanRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
