package com.gymguru.service;

import com.gymguru.controller.exception.single.CredentialNotFoundException;
import com.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.gymguru.controller.exception.single.InvalidCredentialException;
import com.gymguru.controller.exception.single.UserNotFoundException;
import com.gymguru.domain.Credential;
import com.gymguru.domain.User;
import com.gymguru.repository.UserRepository;
import com.gymguru.security.PasswordChanger;
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

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

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
    public void saveUser(User user) throws EmailAlreadyExistException {
        if (credentialService.isEmailAvailable(user.getCredential().getEmail())) {
            credentialService.encodePassword(user.getCredential());
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
