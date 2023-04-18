package com.gymguru.mapper;

import com.gymguru.controller.exception.single.CredentialNotFoundException;
import com.gymguru.domain.User;
import com.gymguru.domain.dto.UserDto;
import com.gymguru.service.CredentialService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserMapper {
    private final CredentialService credentialService;

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
}
