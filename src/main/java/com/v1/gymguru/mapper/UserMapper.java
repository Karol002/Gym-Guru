package com.v1.gymguru.mapper;

import com.v1.gymguru.domain.User;
import com.v1.gymguru.domain.dto.internal.exist.ExistUserDto;
import com.v1.gymguru.domain.dto.internal.insert.InsertUserDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserMapper {

    public User mapToUser(final InsertUserDto insertUserDto) {
        return new User(
                insertUserDto.getEmail(),
                insertUserDto.getPassword(),
                insertUserDto.getFirstName(),
                insertUserDto.getLastName()
        );
    }

    public User mapToUser(final ExistUserDto existUserDto) {
        return new User(
                existUserDto.getId(),
                existUserDto.getEmail(),
                existUserDto.getPassword(),
                existUserDto.getFirstName(),
                existUserDto.getLastName()
        );
    }

    public ExistUserDto mapToExistUseDto(final User user) {
        return new ExistUserDto(
                user.getId(),
                user.getEmail(),
                user.getFirstName(),
                user.getLastName()
        );
    }

    public List<ExistUserDto> mapToExistUserDtoList(List<User> users) {
        return users.stream()
                .map(this::mapToExistUseDto)
                .toList();
    }
}
