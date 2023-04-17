package com.gymguru.controller;

import com.gymguru.adapter.account.AccountAdapter;
import com.gymguru.adapter.account.TrainerAccountDto;
import com.gymguru.controller.exception.single.TrainerNotFoundException;
import com.gymguru.controller.exception.single.CredentialNotFoundException;
import com.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.gymguru.domain.Credential;
import com.gymguru.domain.enums.Specialization;
import com.gymguru.domain.Trainer;
import com.gymguru.domain.dto.TrainerDto;
import com.gymguru.mapper.TrainerMapper;
import com.gymguru.service.TrainerService;
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
    private final AccountAdapter accountAdapter;

    @GetMapping
    public ResponseEntity<List<TrainerDto>> getAllTrainers() {
        List<Trainer> trainers = trainerService.getAllTrainers();
        return ResponseEntity.ok(trainerMapper.mapToExistTrainerDtoList(trainers));
    }

    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<TrainerDto>> getAllTrainersBySpecialization(@PathVariable Specialization specialization) {
        List<Trainer> trainers = trainerService.getAllBySpecialization(specialization);
        return ResponseEntity.ok(trainerMapper.mapToExistTrainerDtoList(trainers));
    }

    @GetMapping(value = "single/{id}")
    public ResponseEntity<TrainerDto> getTrainer(@PathVariable Long id) throws TrainerNotFoundException {
        Trainer trainer = trainerService.getTrainerById(id);
        return ResponseEntity.ok(trainerMapper.mapToTrainerDto(trainer));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createTrainer(@RequestBody TrainerAccountDto trainerAccountDto) throws CredentialNotFoundException, EmailAlreadyExistException {
        Trainer trainer = accountAdapter.toTrainer(trainerAccountDto);
        trainerService.saveTrainer(trainer);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Trainer> updateTrainer(@RequestBody TrainerDto trainerDto) throws TrainerNotFoundException, CredentialNotFoundException {
        Trainer trainer = trainerMapper.mapToTrainer(trainerDto);
        return ResponseEntity.ok(trainerService.updateTrainer(trainer));
    }
}