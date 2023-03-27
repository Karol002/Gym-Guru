package com.v1.gymguru.service;

import com.v1.gymguru.controller.exception.single.UserNotFoundException;
import com.v1.gymguru.domain.User;
import com.v1.gymguru.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUser(Long id) throws UserNotFoundException {
        return userRepository.findById(id).orElseThrow(UserNotFoundException::new);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public User saveUser(final User user) {
        return userRepository.save(user);
    }
}
