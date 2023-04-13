package com.v1.gymguru.controller.internal;

import com.v1.gymguru.adapter.account.AccountAdapter;
import com.v1.gymguru.adapter.account.UserAccountDto;
import com.v1.gymguru.controller.exception.single.CredentialNotFoundException;
import com.v1.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.v1.gymguru.controller.exception.single.InvalidCredentialException;
import com.v1.gymguru.controller.exception.single.UserNotFoundException;
import com.v1.gymguru.domain.Credential;
import com.v1.gymguru.domain.User;
import com.v1.gymguru.domain.dto.UserDto;
import com.v1.gymguru.mapper.UserMapper;
import com.v1.gymguru.security.PasswordChanger;
import com.v1.gymguru.service.UserService;
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
        Credential credential = accountAdapter.toCredential(trainerAccountDto);
        userService.saveUser(user, credential);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/password")
    public ResponseEntity<Void> changePassword(@RequestBody PasswordChanger passwordChanger) throws CredentialNotFoundException, EmailAlreadyExistException, InvalidCredentialException {
        userService.changePassword(passwordChanger);
        return ResponseEntity.ok().build();
    }
}
