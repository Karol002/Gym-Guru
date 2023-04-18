package com.gymguru.controller;

import com.gymguru.adapter.plan.CompletePlanDto;
import com.gymguru.adapter.plan.PlanAdapter;
import com.gymguru.controller.exception.single.*;
import com.gymguru.domain.Plan;
import com.gymguru.domain.dto.PlanDto;
import com.gymguru.mapper.PlanMapper;
import com.gymguru.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/gymguru/plans")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PlanController {
    private final PlanService planService;
    private final PlanMapper planMapper;
    private final PlanAdapter planAdapter;

    @GetMapping(value = "{userId}")
    public ResponseEntity<PlanDto> getPlanByUserId(@PathVariable Long userId) throws SubscriptionNotFoundException, SubscriptionExpiredException, PlanForUserIdNotFoundException {
        Plan plan = planService.getPlanByUserId(userId);
        return ResponseEntity.ok(planMapper.mapToPlanDto(plan));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPlan(@RequestBody CompletePlanDto completePlanDto) throws UserNotFoundException, TrainerNotFoundException {
        Plan plan = planAdapter.toPlan(completePlanDto);
        planService.savePlan(plan);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updatePlan(@RequestBody PlanDto planDtoDto) throws UserNotFoundException, TrainerNotFoundException, PlanNotFoundException {
        Plan plan = planMapper.mapToPlan(planDtoDto);
        planService.updatePlan(plan);
        return ResponseEntity.ok().build();
    }
}
