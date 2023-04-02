package com.v1.gymguru.controller.internal;

import com.v1.gymguru.controller.exception.single.UserNotFoundException;
import com.v1.gymguru.domain.User;
import com.v1.gymguru.domain.dto.UserDto;
import com.v1.gymguru.domain.dto.save.SaveUserDto;
import com.v1.gymguru.mapper.UserMapper;
import com.v1.gymguru.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/gymguru/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping(value = "{id}")
    public ResponseEntity<UserDto> createUser(@PathVariable Long id) throws UserNotFoundException {
        return ResponseEntity.ok(userMapper.mapToExistUseDto(userService.getUserById(id)));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> createUser(@RequestBody SaveUserDto saveUserDto) {
        User user = userMapper.mapToUser(saveUserDto);
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody UserDto userDto) throws UserNotFoundException {
        User user = userMapper.mapToUser(userDto);
        return ResponseEntity.ok(userService.updateUser(user));
    }
}
