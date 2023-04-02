package com.v1.gymguru.controller.internal;

import com.v1.gymguru.controller.exception.single.*;
import com.v1.gymguru.domain.Plan;
import com.v1.gymguru.domain.dto.PlanDto;
import com.v1.gymguru.domain.dto.save.SavePlanDto;
import com.v1.gymguru.mapper.PlanMapper;
import com.v1.gymguru.service.PlanService;
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

    @GetMapping(value = "{userId}")
    public ResponseEntity<PlanDto> getPlanByUserId(@PathVariable Long userId) throws SubscriptionNotFoundException, SubscriptionExpiredException, PlanForUserIdNotFoundException {
        Plan plan = planService.getPlanByUserId(userId);
        return ResponseEntity.ok(planMapper.mapToExistPlanDto(plan));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createPlan(@RequestBody SavePlanDto savePlanDtoDto) throws UserNotFoundException {
        Plan plan = planMapper.mapToPlan(savePlanDtoDto);
        planService.savePlan(plan);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updatePlan(@RequestBody PlanDto planDtoDto) throws UserNotFoundException, PlanNotFoundException {
        Plan plan = planMapper.mapToPlan(planDtoDto);
        planService.updatePlan(plan);
        return ResponseEntity.ok().build();
    }// Do usunięcia

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) throws PlanNotFoundException {
        planService.deletePlan(id);
        return ResponseEntity.ok().build();
    }
}
