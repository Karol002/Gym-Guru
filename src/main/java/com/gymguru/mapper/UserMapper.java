package com.gymguru.mapper;

import com.gymguru.controller.exception.single.PersonNotFoundException;
import com.gymguru.controller.exception.single.PlanNotFoundException;
import com.gymguru.domain.User;
import com.gymguru.domain.dto.internal.exist.ExistUserDto;
import com.gymguru.domain.dto.internal.insert.InsertUserDto;
import com.gymguru.service.PersonService;
import com.gymguru.service.PlanService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Getter
@RequiredArgsConstructor
public class UserMapper {
    private final PersonService personService;
    private final PlanService planService;

    public User mapToUser(final InsertUserDto insertUserDto) throws PersonNotFoundException, PlanNotFoundException {
        return new User(
                personService.getPerson(insertUserDto.getPersonId()),
                planService.getPlan(insertUserDto.getPlanId())
        );
    }

    public User mapToUser(final ExistUserDto existUserDto) throws PersonNotFoundException, PlanNotFoundException {
        return new User(
                existUserDto.getId(),
                personService.getPerson(existUserDto.getPersonId()),
                planService.getPlan(existUserDto.getPlanId())
        );
    }

    public ExistUserDto mapToExistUseDto(final User user) {
        return new ExistUserDto(
                user.getId(),
                user.getPerson().getId(),
                user.getPlan().getId()
        );
    }

    public List<ExistUserDto> mapToExistUserDtoList(List<User> users) {
        return users.stream()
                .map(this::mapToExistUseDto)
                .toList();
    }
}
