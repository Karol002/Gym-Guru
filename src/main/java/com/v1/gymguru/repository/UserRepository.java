package com.v1.gymguru.repository;


import com.v1.gymguru.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    List<User> findAll();

    User save(User user);
}
