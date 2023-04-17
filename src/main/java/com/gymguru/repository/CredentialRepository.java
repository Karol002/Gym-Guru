package com.gymguru.repository;

import com.gymguru.domain.Credential;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CredentialRepository extends CrudRepository<Credential, Long> {
    Optional<Credential> findByEmail(String email);
    boolean existsByEmail(String email);
    List<Credential> findAll();
}
