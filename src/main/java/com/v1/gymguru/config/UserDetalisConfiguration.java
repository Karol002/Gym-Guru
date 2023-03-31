/*
package com.v1.gymguru.config;

import com.v1.gymguru.domain.Trainer;
import com.v1.gymguru.service.TrainerService;
import com.v1.gymguru.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

@RequiredArgsConstructor
@Configuration
public class UserDetalisConfiguration implements UserDetailsService {
    private final UserService userService;
    private final TrainerService trainerService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<Trainer> trainer = trainerService.getTrainerByEmail(email);
        Optional <com.v1.gymguru.domain.User> user = userService.getUserByEmail(email);

        if (trainer.isPresent()) {

            return User.withDefaultPasswordEncoder()
                    .username(trainer.get().getEmail())
                    .password(trainer.get().getPassword())
                    .roles("TRAINER")
                    .build();

        } else if (user.isPresent()) {

            return User.withDefaultPasswordEncoder()
                    .username(user.get().getEmail())
                    .password(user.get().getPassword())
                    .roles("USER")
                    .build();

        } else {
            throw new UsernameNotFoundException("Mail or password incorrect");
        }
    }
}
*/
