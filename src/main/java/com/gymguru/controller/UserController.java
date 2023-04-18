package com.gymguru.controller;

import com.gymguru.adapter.account.AccountAdapter;
import com.gymguru.adapter.account.UserAccountDto;
import com.gymguru.controller.exception.single.CredentialNotFoundException;
import com.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.gymguru.controller.exception.single.InvalidCredentialException;
import com.gymguru.controller.exception.single.UserNotFoundException;
import com.gymguru.domain.User;
import com.gymguru.domain.dto.UserDto;
import com.gymguru.mapper.UserMapper;
import com.gymguru.security.PasswordChanger;
import com.gymguru.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/gymguru/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private final AccountAdapter accountAdapter;

    @GetMapping(value = "id/{id}")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long id) throws UserNotFoundException {
        return ResponseEntity.ok(userMapper.mapTotUseDto(userService.getUserById(id)));
    }

    @GetMapping(value = "emails")
    public ResponseEntity<List<String>> getUserByEmail() {
        return ResponseEntity.ok(userService.getAllEmails());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody UserAccountDto trainerAccountDto) throws CredentialNotFoundException, EmailAlreadyExistException {
        User user = accountAdapter.toUser(trainerAccountDto);
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/password")
    public ResponseEntity<Void> changePassword(@RequestBody PasswordChanger passwordChanger) throws CredentialNotFoundException, EmailAlreadyExistException, InvalidCredentialException {
        userService.changePassword(passwordChanger);
        return ResponseEntity.ok().build();
    }

    @PutMapping()
    public ResponseEntity<Void> changePassword(@RequestBody UserDto userDto) throws CredentialNotFoundException, UserNotFoundException {
        User user = userMapper.mapToUser(userDto);
        userService.updateUser(user);
        return ResponseEntity.ok().build();
    }
}
