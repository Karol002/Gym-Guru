package com.v1.gymguru.repository;

import com.v1.gymguru.domain.Plan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface PlanRepository extends CrudRepository<Plan, Long> {

    Optional<Plan> findByUserId(Long userId);

    Plan save(Plan plan);
}
