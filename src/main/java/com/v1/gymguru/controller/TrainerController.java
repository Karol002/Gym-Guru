package com.v1.gymguru.controller;

import com.v1.gymguru.adapter.account.AccountAdapter;
import com.v1.gymguru.adapter.account.TrainerAccountDto;
import com.v1.gymguru.controller.exception.single.CredentialNotFoundException;
import com.v1.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.v1.gymguru.controller.exception.single.TrainerNotFoundException;
import com.v1.gymguru.domain.Credential;
import com.v1.gymguru.domain.enums.Specialization;
import com.v1.gymguru.domain.Trainer;
import com.v1.gymguru.domain.dto.TrainerDto;
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
        Credential credential = accountAdapter.toCredential(trainerAccountDto);
        trainerService.saveTrainer(trainer, credential);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Trainer> updateTrainer(@RequestBody TrainerDto trainerDto) throws TrainerNotFoundException, CredentialNotFoundException {
        Trainer trainer = trainerMapper.mapToTrainer(trainerDto);
        return ResponseEntity.ok(trainerService.updateTrainer(trainer));
    }
}
