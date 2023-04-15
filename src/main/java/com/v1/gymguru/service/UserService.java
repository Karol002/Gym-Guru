package com.v1.gymguru.service;

import com.v1.gymguru.controller.exception.single.CredentialNotFoundException;
import com.v1.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.v1.gymguru.controller.exception.single.InvalidCredentialException;
import com.v1.gymguru.controller.exception.single.UserNotFoundException;
import com.v1.gymguru.domain.Credential;
import com.v1.gymguru.domain.User;
import com.v1.gymguru.repository.UserRepository;
import com.v1.gymguru.security.PasswordChanger;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CredentialService credentialService;

    public List<String> getAllEmails() {
        List<Credential> allCredential = credentialService.getAll();
        return allCredential.stream()
                .map(Credential::getEmail)
                .collect(Collectors.toList());
    }

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public User getUserByEmail(String email) throws CredentialNotFoundException, UserNotFoundException {
        Long credentialId = credentialService.getCredentialIdByEmail(email);
        return userRepository.findByCredentialId(credentialId).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void saveUser(final User user, Credential credential) throws EmailAlreadyExistException {
        if (credentialService.isEmailAvailable(credential.getEmail())) {
            userRepository.save(user);
        } else  throw new EmailAlreadyExistException();
    }

    public void updateUser(User user) throws UserNotFoundException {
        if (userRepository.existsById(user.getId())) {
            userRepository.save(user);
        } else throw new UserNotFoundException();
    }

    public void changePassword(PasswordChanger passwordChanger) throws InvalidCredentialException, CredentialNotFoundException {
        credentialService.changePassword(passwordChanger);
    }
}
