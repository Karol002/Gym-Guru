package com.gymguru.controller.internal;

import com.gymguru.controller.exception.single.PlanNotFoundException;
import com.gymguru.domain.Exercise;
import com.gymguru.domain.Trainer;
import com.gymguru.domain.dto.internal.exist.ExistTrainerDto;
import com.gymguru.domain.dto.internal.insert.InsertExerciseDto;
import com.gymguru.domain.dto.internal.insert.InsertTrainerDto;
import com.gymguru.mapper.TrainerMapper;
import com.gymguru.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/gymguru/trainer")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TrainerController {
    private final TrainerService trainerService;
    private final TrainerMapper trainerMapper;

    @GetMapping
    public ResponseEntity<List<ExistTrainerDto>> getAllTrainers() {
        List<Trainer> trainers = trainerService.getAllTrainers();
        return ResponseEntity.ok(trainerMapper.mapToTrainerDtoList(trainers));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addTrainer(@RequestBody InsertTrainerDto insertTrainerDto) {
        Trainer trainer = trainerMapper.mapToTrainer(insertTrainerDto);
        trainerService.saveTrainer(trainer);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Trainer> updateTrainer(@RequestBody ExistTrainerDto existTrainerDto) {
        Trainer trainer = trainerMapper.mapToTrainer(existTrainerDto);
        return ResponseEntity.ok(trainerService.saveTrainer(trainer));
    }
}
