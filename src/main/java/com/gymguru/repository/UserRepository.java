package com.gymguru.repository;


import com.gymguru.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Repository
@Transactional
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll();
    Optional<User> findByCredentialId(Long credentialId);

    User save(User user);
}
