package com.v1.gymguru.repository;

import com.v1.gymguru.domain.Meal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface MealRepository extends CrudRepository<Meal, Long> {

    List<Meal> findAll();

    Meal save(Meal meal);
}
