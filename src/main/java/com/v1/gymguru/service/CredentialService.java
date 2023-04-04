package com.v1.gymguru.service;

import com.v1.gymguru.controller.exception.single.CredentialNotFoundException;
import com.v1.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.v1.gymguru.controller.exception.single.InvalidEmailException;
import com.v1.gymguru.domain.Credential;
import com.v1.gymguru.repository.CredentialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CredentialService {
    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;

    public Credential getByEmail(String email) throws CredentialNotFoundException {
        return credentialRepository.findByEmail(email).orElseThrow(CredentialNotFoundException::new);
    }

    public Credential getById(Long id) throws CredentialNotFoundException {
        return credentialRepository.findById(id).orElseThrow(CredentialNotFoundException::new);
    }

    public Credential saveCredential(Credential credential) throws EmailAlreadyExistException {
        if (!credentialRepository.existsByEmail(credential.getEmail())) {
            return credentialRepository.save(new Credential(credential.getEmail(), passwordEncoder.encode(credential.getPassword()), credential.getRole()));
        } else throw new EmailAlreadyExistException();
    }
}
