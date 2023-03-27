package com.gymguru.controller.internal;

import com.gymguru.controller.exception.single.PlanNotFoundException;
import com.gymguru.controller.exception.single.UserNotFoundException;
import com.gymguru.domain.Meal;
import com.gymguru.domain.Plan;
import com.gymguru.domain.dto.internal.exist.ExistMealDto;
import com.gymguru.domain.dto.internal.exist.ExistPlanDto;
import com.gymguru.domain.dto.internal.insert.InsertMealDto;
import com.gymguru.domain.dto.internal.insert.InsertPlanDto;
import com.gymguru.mapper.PlanMapper;
import com.gymguru.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/gymguru/plan")
@RequiredArgsConstructor
@CrossOrigin("*")
public class PlanController {
    private final PlanService planService;
    private final PlanMapper planMapper;

    @GetMapping(value = "{id}")
    public ResponseEntity<ExistPlanDto> getPlan(@PathVariable Long id) throws PlanNotFoundException {
        Plan plan = planService.getPlan(id);
        return ResponseEntity.ok(planMapper.mapToExistPlanDto(plan));
    }//Get Plan By user Id

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addPlan(@RequestBody InsertPlanDto insertPlanDtoDto) throws UserNotFoundException {
        Plan plan = planMapper.mapToPlan(insertPlanDtoDto);
        planService.savePlan(plan);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping(value = "{id}")
    public ResponseEntity<Void> deletePlan(@PathVariable Long id) {
        planService.deletePlan(id);
        return ResponseEntity.ok().build();
    }
}
