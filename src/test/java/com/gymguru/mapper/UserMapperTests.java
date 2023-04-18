package com.gymguru.mapper;

import com.gymguru.config.Deleter;
import com.gymguru.controller.exception.single.CredentialNotFoundException;
import com.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.gymguru.domain.Credential;
import com.gymguru.domain.User;
import com.gymguru.domain.dto.UserDto;
import com.gymguru.domain.enums.CredentialType;
import com.gymguru.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserMapperTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    @Autowired
    private Deleter deleter;

    @AfterEach
    public void clean() {
        deleter.deleteAllFromEachEntity();
    }

    @Test
    void mapToUser_ShouldReturnUser() throws CredentialNotFoundException, EmailAlreadyExistException {
        //Given
        Credential credential = new Credential( "test@example.com", "password", CredentialType.ROLE_USER);
        User user = new User("john", "smith", credential);
        userService.saveUser(user);

        UserDto userDto = new UserDto(user.getId(), "John", "Doe", credential.getId());

        //When
        User result = userMapper.mapToUser(userDto);

        //Then
        assertNotNull(result);
        assertEquals(userDto.getId(), result.getId());
        assertEquals(userDto.getFirstName(), result.getFirstName());
        assertEquals(userDto.getLastName(), result.getLastName());
        assertEquals(userDto.getCredentialId(), result.getCredential().getId());
    }

    @Test
    void mapToUser_ShouldThrowCredentialNotFoundException() {
        //Given
        UserDto userDto = new UserDto(1L, "John", "Doe", 1L);

        //When & Then
        assertThrows(CredentialNotFoundException.class, () -> userMapper.mapToUser(userDto));
    }

    @Test
    void mapToUserDto_ShouldReturnUserDto() {
        //Given
        Credential credential = new Credential(1L, "test@example.com", "password", CredentialType.ROLE_USER);
        User user = new User(1L, "John", "Doe", credential);

        //When
        UserDto result = userMapper.mapTotUseDto(user);

        //Then
        assertNotNull(result);
        assertEquals(user.getId(), result.getId());
        assertEquals(user.getFirstName(), result.getFirstName());
        assertEquals(user.getLastName(), result.getLastName());
        assertEquals(user.getCredential().getId(), result.getCredentialId());
    }
}
