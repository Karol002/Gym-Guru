package com.v1.gymguru.controller.internal;

import com.v1.gymguru.controller.exception.single.PlanNotFoundException;
import com.v1.gymguru.controller.exception.single.UserNotFoundException;
import com.v1.gymguru.domain.Plan;
import com.v1.gymguru.domain.dto.internal.exist.ExistPlanDto;
import com.v1.gymguru.domain.dto.internal.insert.InsertPlanDto;
import com.v1.gymguru.mapper.PlanMapper;
import com.v1.gymguru.service.PlanService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
