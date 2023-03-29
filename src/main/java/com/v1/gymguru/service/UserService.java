package com.v1.gymguru.service;

import com.v1.gymguru.controller.exception.single.TrainerNotFoundException;
import com.v1.gymguru.controller.exception.single.UserNotFoundException;
import com.v1.gymguru.domain.Trainer;
import com.v1.gymguru.domain.User;
import com.v1.gymguru.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public User getUserById(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User saveUser(final User user) {
        return userRepository.save(user);
    }

    public User updateUser(final User user) throws UserNotFoundException {
        if (userRepository.existsById(user.getId())) {
            return userRepository.save(user);
        } else throw  new UserNotFoundException();
    }
}
