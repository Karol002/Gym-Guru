package com.gymguru.service;

import com.gymguru.controller.exception.single.CredentialNotFoundException;
import com.gymguru.controller.exception.single.InvalidCredentialException;
import com.gymguru.domain.Credential;
import com.gymguru.repository.CredentialRepository;
import com.gymguru.security.PasswordChanger;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CredentialService {
    private final CredentialRepository credentialRepository;
    private final PasswordEncoder passwordEncoder;

    public List<Credential> getAll() {
        return credentialRepository.findAll();
    }

    public Credential getById(Long id) throws CredentialNotFoundException {
        return credentialRepository.findById(id).orElseThrow(CredentialNotFoundException::new);
    }

    public Credential getByEmail(String email) throws CredentialNotFoundException {
        return credentialRepository.findByEmail(email).orElseThrow(CredentialNotFoundException::new);
    }

    public void changePassword(PasswordChanger passwordChanger) throws CredentialNotFoundException, InvalidCredentialException {
        Credential credential = getByEmail(passwordChanger.getEmail());
        if(passwordEncoder.matches(passwordChanger.getOldPassword(), credential.getPassword())) {
            credential.setPassword(passwordEncoder.encode(passwordChanger.getNewPassword()));
            credentialRepository.save(credential);
        } else throw new InvalidCredentialException();
    }

    public void encodePassword(Credential credential) {
        credential.setPassword(passwordEncoder.encode(credential.getPassword()));
    }

    public Long getCredentialIdByEmail(String email) throws CredentialNotFoundException {
        return getByEmail(email).getId();

    }

    public boolean isEmailAvailable(String email) {
        return !credentialRepository.existsByEmail(email);
    }
}
