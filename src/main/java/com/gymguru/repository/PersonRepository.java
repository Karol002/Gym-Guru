package com.gymguru.repository;

import com.gymguru.domain.Person;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface PersonRepository extends CrudRepository<Person, Long> {

    List<Person> findAll();

    Person save(Person person);
}
