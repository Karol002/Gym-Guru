package com.gymguru.repository;

import com.gymguru.domain.Plan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface PlanRepository extends CrudRepository<Plan, Long> {

    List<Plan> findAll();

    Plan save(Plan plan);
}
