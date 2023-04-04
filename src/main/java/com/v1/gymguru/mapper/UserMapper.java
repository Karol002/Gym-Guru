package com.v1.gymguru.mapper;

import com.v1.gymguru.controller.exception.single.CredentialNotFoundException;
import com.v1.gymguru.domain.User;
import com.v1.gymguru.domain.dto.UserDto;
import com.v1.gymguru.domain.dto.save.SaveUserDto;
import com.v1.gymguru.service.CredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final CredentialService credentialService;

    public User mapToUser(final SaveUserDto saveUserDto) throws CredentialNotFoundException {
        return new User(
                saveUserDto.getFirstName(),
                saveUserDto.getLastName(),
                credentialService.getById(saveUserDto.getCredentialId())
        );
    }

    public User mapToUser(final UserDto userDto) throws CredentialNotFoundException {
        return new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                credentialService.getById(userDto.getCredentialId())
        );
    }

    public UserDto mapTotUseDto(final User user) {
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getCredential().getId()
        );
    }

    public List<UserDto> mapToExistUserDtoList(List<User> users) {
        return users.stream()
                .map(this::mapTotUseDto)
                .toList();
    }
}
