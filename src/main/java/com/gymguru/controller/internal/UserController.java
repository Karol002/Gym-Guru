package com.gymguru.controller.internal;

import com.gymguru.domain.Trainer;
import com.gymguru.domain.User;
import com.gymguru.domain.dto.internal.exist.ExistTrainerDto;
import com.gymguru.domain.dto.internal.exist.ExistUserDto;
import com.gymguru.domain.dto.internal.insert.InsertTrainerDto;
import com.gymguru.domain.dto.internal.insert.InsertUserDto;
import com.gymguru.mapper.UserMapper;
import com.gymguru.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/gymguru/user")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addTrainer(@RequestBody InsertUserDto insertUserDto) {
        User user = userMapper.mapToUser(insertUserDto);
        userService.saveUser(user);
        return ResponseEntity.ok().build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> updateUser(@RequestBody ExistUserDto existUserDto) {
        User user = userMapper.mapToUser(existUserDto);
        return ResponseEntity.ok(userService.saveUser(user));
    }
}
