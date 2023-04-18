package com.gymguru.repository;

import com.gymguru.domain.Exercise;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface ExerciseRepository extends CrudRepository<Exercise, Long> {

    List<Exercise> findAll();

    List<Exercise> findAllByPlanId(Long planId);
    Exercise save(Exercise exercise);
}
