package com.gymguru.repository;

import com.gymguru.domain.Trainer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface TrainerRepository extends CrudRepository<Trainer, Long> {

    List<Trainer> findAll();

    Trainer save(Trainer trainer);
}
