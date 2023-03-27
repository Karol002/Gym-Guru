package com.v1.gymguru.service;

import com.v1.gymguru.controller.exception.single.PersonNotFoundException;
import com.v1.gymguru.domain.Person;
import com.v1.gymguru.repository.PersonRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class PersonService {
    private final PersonRepository personRepository;

    public List<Person> getAllPersons() {
        return personRepository.findAll();
    }

    public Person getPerson(Long id) throws PersonNotFoundException {
        return personRepository.findById(id).orElseThrow(PersonNotFoundException::new);
    }

    public void deletePerson(Long id) {
        personRepository.deleteById(id);
    }

    public Person savePerson(final Person person) {
        return personRepository.save(person);
    }
}
