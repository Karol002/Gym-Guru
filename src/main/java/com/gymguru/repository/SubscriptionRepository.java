package com.gymguru.repository;

import com.gymguru.domain.Subscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    List<Subscription> findAllByTrainerId(Long trainerId);
    List<Subscription> findAll();

    Optional<Subscription> findByUserId(Long userId);

    Subscription save(Subscription subscription);
}
