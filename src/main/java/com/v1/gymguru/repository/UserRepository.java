package com.v1.gymguru.repository;


import com.v1.gymguru.domain.Credential;
import com.v1.gymguru.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll();
    Optional<User> findByCredentialId(Long credentialId);

    User save(User user);
}
