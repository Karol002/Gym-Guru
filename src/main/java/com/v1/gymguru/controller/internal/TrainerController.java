package com.v1.gymguru.controller.internal;

import com.v1.gymguru.controller.exception.single.TrainerNotFoundException;
import com.v1.gymguru.domain.Trainer;
import com.v1.gymguru.domain.dto.TrainerDto;
import com.v1.gymguru.domain.dto.save.SaveTrainerDto;
import com.v1.gymguru.mapper.TrainerMapper;
import com.v1.gymguru.service.TrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/gymguru/trainers")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TrainerController {
    private final TrainerService trainerService;
    private final TrainerMapper trainerMapper;

    @GetMapping
    public ResponseEntity<List<TrainerDto>> getAllTrainers() {
        List<Trainer> trainers = trainerService.getAllTrainers();
        return ResponseEntity.ok(trainerMapper.mapToExistTrainerDtoList(trainers));
    }

    @GetMapping(value = "{id}")
    public ResponseEntity<TrainerDto> getTrainer(@PathVariable Long id) throws TrainerNotFoundException {
        Trainer trainer = trainerService.getTrainerById(id);
        return ResponseEntity.ok(trainerMapper.mapToExistTrainerDto(trainer));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTrainer(@RequestBody SaveTrainerDto saveTrainerDto) {
        Trainer trainer = trainerMapper.mapToTrainer(saveTrainerDto);
        trainerService.saveTrainer(trainer);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Trainer> updateTrainer(@RequestBody TrainerDto trainerDto) throws TrainerNotFoundException {
        Trainer trainer = trainerMapper.mapToTrainer(trainerDto);
        return ResponseEntity.ok(trainerService.updateTrainer(trainer));
    }
}
