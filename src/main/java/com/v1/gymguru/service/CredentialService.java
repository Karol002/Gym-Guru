package com.v1.gymguru.service;

import com.v1.gymguru.controller.exception.single.CredentialNotFoundException;
import com.v1.gymguru.controller.exception.single.InvalidCredentialException;
import com.v1.gymguru.domain.Credential;
import com.v1.gymguru.repository.CredentialRepository;
import com.v1.gymguru.security.PasswordChanger;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

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

    private void saveCredential(Credential credential) {
        credentialRepository.save(credential);
    }

    public void changePassword(PasswordChanger passwordChanger) throws CredentialNotFoundException, InvalidCredentialException {
        Credential credential = getByEmail(passwordChanger.getEmail());
        if(passwordEncoder.matches(passwordChanger.getOldPassword(), credential.getPassword())) {
            credential.setPassword(passwordEncoder.encode(passwordChanger.getNewPassword()));
            saveCredential(credential);

        } else throw new InvalidCredentialException();
    }

    public Long getCredentialIdByEmail(String email) throws CredentialNotFoundException {
        return getByEmail(email).getId();

    }

    public List<Credential> getAll() {
        return credentialRepository.findAll();
    }

    public boolean isEmailAvailable(String email) {
        return !credentialRepository.existsByEmail(email);
    }
}
