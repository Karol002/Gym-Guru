package com.gymguru.repository;

import com.gymguru.domain.Meal;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface MealRepository extends CrudRepository<Meal, Long> {

    List<Meal> findAllByPlanId(Long planId);

    Meal save(Meal meal);
}
