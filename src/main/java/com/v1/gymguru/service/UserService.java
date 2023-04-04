package com.v1.gymguru.service;

import com.v1.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.v1.gymguru.controller.exception.single.UserNotFoundException;
import com.v1.gymguru.domain.Credential;
import com.v1.gymguru.domain.User;
import com.v1.gymguru.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final CredentialService credentialService;

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    @Transactional
    public void saveUser(final User user, Credential credential) throws EmailAlreadyExistException {
        user.setCredential(credentialService.saveCredential(credential));
        userRepository.save(user);
    }

    public User updateUser(final User user) throws UserNotFoundException {
        if (userRepository.existsById(user.getId())) {
            return userRepository.save(user);
        } else throw  new UserNotFoundException();
    }
}
