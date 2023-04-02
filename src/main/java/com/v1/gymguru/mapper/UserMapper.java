package com.v1.gymguru.mapper;

import com.v1.gymguru.domain.User;
import com.v1.gymguru.domain.dto.UserDto;
import com.v1.gymguru.domain.dto.save.SaveUserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMapper {

    public User mapToUser(final SaveUserDto saveUserDto) {
        return new User(
                saveUserDto.getEmail(),
                saveUserDto.getPassword(),
                saveUserDto.getFirstName(),
                saveUserDto.getLastName()
        );
    }

    public User mapToUser(final UserDto userDto) {
        return new User(
                userDto.getId(),
                userDto.getEmail(),
                userDto.getPassword(),
                userDto.getFirstName(),
                userDto.getLastName()
        );
    }

    public UserDto mapToExistUseDto(final User user) {
        return new UserDto(
                user.getId(),
                user.getEmail(),
                "PASSWORD IS HIDDEN",
                user.getFirstName(),
                user.getLastName()
        );
    }

    public List<UserDto> mapToExistUserDtoList(List<User> users) {
        return users.stream()
                .map(this::mapToExistUseDto)
                .toList();
    }
}
