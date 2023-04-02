package com.v1.gymguru.security.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;

@Service
@RequiredArgsConstructor
public class CredentialService {
    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;

    public Credential getByEmail(String email) throws Exception {
        return credentialRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("User not found by email!"));
    }

    @Bean
    public void save() {
        Credential credential = new Credential(3L, "karolUser", passwordEncoder.encode( "karol2"), CredentialType.ROLE_USER);
        credentialRepository.save(credential);
    }
}
