package com.v1.gymguru.repository;

import com.v1.gymguru.domain.Subscription;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface SubscriptionRepository extends CrudRepository<Subscription, Long> {

    List<Subscription> findAll();

    Subscription save(Subscription subscription);
}
