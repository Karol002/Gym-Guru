package com.v1.gymguru.security.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CredentialRepository extends CrudRepository<Credential, Long> {
    Optional<Credential> findByEmail(String email);
}
