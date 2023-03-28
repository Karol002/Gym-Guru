package com.v1.gymguru.repository;

import com.v1.gymguru.domain.Trainer;
import com.v1.gymguru.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface TrainerRepository extends CrudRepository<Trainer, Long> {

    List<Trainer> findAll();

    Optional<Trainer> findByEmail(String email);

    Trainer save(Trainer trainer);
}
