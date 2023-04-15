package com.v1.gymguru.repository;

import com.v1.gymguru.domain.enums.Specialization;
import com.v1.gymguru.domain.Trainer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface TrainerRepository extends CrudRepository<Trainer, Long> {

    List<Trainer> findAll();
    List<Trainer> findAllBySpecialization(Specialization specialization);
    Trainer save(Trainer trainer);

    Optional<Trainer> findByCredentialId(Long credentialId);
}
