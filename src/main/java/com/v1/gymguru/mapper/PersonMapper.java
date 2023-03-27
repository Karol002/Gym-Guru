package com.v1.gymguru.mapper;

import com.v1.gymguru.domain.Person;
import com.v1.gymguru.domain.dto.internal.exist.ExistPersonDto;
import com.v1.gymguru.domain.dto.internal.insert.InsertPersonDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonMapper {

    public Person mapToPerson(final InsertPersonDto insertPersonDto) {
        return new Person(
                insertPersonDto.getEmail(),
                insertPersonDto.getPassword(),
                insertPersonDto.getFirstName(),
                insertPersonDto.getLastName()
        );
    }

    public Person mapToPerson(ExistPersonDto existPersonDto) {
        return new Person(
                existPersonDto.getId(),
                existPersonDto.getEmail(),
                existPersonDto.getPassword(),
                existPersonDto.getFirstName(),
                existPersonDto.getLastName()
        );
    }

    public ExistPersonDto mapToExistPersonDto(final Person person) {
        return new ExistPersonDto(
                person.getId(),
                person.getEmail(),
                person.getPassword(),
                person.getFirstName(),
                person.getLastName()
        );
    }

    public List<ExistPersonDto> mapToExistPersonDtoList(final List<Person> persons) {
        return persons.stream()
                .map(this::mapToExistPersonDto)
                .toList();
    }
}
