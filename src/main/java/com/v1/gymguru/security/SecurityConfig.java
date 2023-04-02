/*
package com.v1.gymguru.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        //@formatter:off
        http
                .httpBasic().disable()
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/v1/gymguru/exercises/plan/{planId}").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/v1/gymguru/meals/plan/{planId}").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/v1/gymguru/plans/{userId}").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/v1/gymguru/trainers/{id}").hasRole("USER")
                .antMatchers(HttpMethod.PUT,"/v1/gymguru/users").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/v1/gymguru/users/{id}").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/v1/gymguru/subscriptions/user/{userId}").hasRole("USER")
                .antMatchers(HttpMethod.POST,"/v1/gymguru/subscriptions/user").hasRole("USER")
                .antMatchers(HttpMethod.PUT,"/v1/gymguru/subscriptions/user").hasRole("USER")

                .antMatchers(HttpMethod.POST,"/v1/gymguru/exercises").hasRole("TRAINER")
                .antMatchers(HttpMethod.POST,"/v1/gymguru/meals").hasRole("TRAINER")
                .antMatchers(HttpMethod.POST,"/v1/gymguru/plans").hasRole("TRAINER")
                .antMatchers(HttpMethod.PUT,"/v1/gymguru/plans").hasRole("TRAINER")
                .antMatchers(HttpMethod.PUT,"/v1/gymguru/trainers").hasRole("TRAINER")
                .antMatchers(HttpMethod.GET,"/v1/gymguru/subscriptions/trainer/{trainerId}").hasRole("TRAINER")
                .antMatchers(HttpMethod.GET,"/v1/gymguru/subscriptions/trainer/{trainerId}").hasRole("TRAINER")
                .antMatchers(HttpMethod.GET,"v1/gymguru/edamam/meals/{mealName}").hasRole("TRAINER")
                .antMatchers(HttpMethod.GET,"v1/gymguru/edamam/meals/{mealName}").hasRole("TRAINER")
                .antMatchers(HttpMethod.GET,"v1/gymguru/wger/exercises/{categoryId}").hasRole("TRAINER")
                .antMatchers(HttpMethod.GET,"v1/gymguru/wger/categories").hasRole("TRAINER")

                .antMatchers(HttpMethod.POST,"/v1/gymguru/trainers/{id}").permitAll()
                .antMatchers(HttpMethod.POST,"/v1/gymguru/users").permitAll()
                .antMatchers(HttpMethod.GET,"/v1/gymguru/trainers").permitAll()
                .and()
                .apply(new JwtConfigurer(jwtTokenProvider));
        //@formatter:on
    }
}
*/
/* http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET,"/v1/gymguru/exercises/plan/{planId}").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/v1/gymguru/meals/plan/{planId}").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/v1/gymguru/plans/{userId}").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/v1/gymguru/trainers/{id}").hasRole("USER")
                .antMatchers(HttpMethod.PUT,"/v1/gymguru/users").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/v1/gymguru/users/{id}").hasRole("USER")
                .antMatchers(HttpMethod.GET,"/v1/gymguru/subscriptions/user/{userId}").hasRole("USER")
                .antMatchers(HttpMethod.POST,"/v1/gymguru/subscriptions/user").hasRole("USER")
                .antMatchers(HttpMethod.PUT,"/v1/gymguru/subscriptions/user").hasRole("USER")

                .antMatchers(HttpMethod.POST,"/v1/gymguru/exercises").hasRole("TRAINER")
                .antMatchers(HttpMethod.POST,"/v1/gymguru/meals").hasRole("TRAINER")
                .antMatchers(HttpMethod.POST,"/v1/gymguru/plans").hasRole("TRAINER")
                .antMatchers(HttpMethod.PUT,"/v1/gymguru/plans").hasRole("TRAINER")
                .antMatchers(HttpMethod.PUT,"/v1/gymguru/trainers").hasRole("TRAINER")
                .antMatchers(HttpMethod.GET,"/v1/gymguru/subscriptions/trainer/{trainerId}").hasRole("TRAINER")
                .antMatchers(HttpMethod.GET,"/v1/gymguru/subscriptions/trainer/{trainerId}").hasRole("TRAINER")
                .antMatchers(HttpMethod.GET,"v1/gymguru/edamam/meals/{mealName}").hasRole("TRAINER")
                .antMatchers(HttpMethod.GET,"v1/gymguru/edamam/meals/{mealName}").hasRole("TRAINER")
                .antMatchers(HttpMethod.GET,"v1/gymguru/wger/exercises/{categoryId}").hasRole("TRAINER")
                .antMatchers(HttpMethod.GET,"v1/gymguru/wger/categories").hasRole("TRAINER")

                .antMatchers(HttpMethod.POST,"/v1/gymguru/trainers/{id}").permitAll()
                .antMatchers(HttpMethod.POST,"/v1/gymguru/users").permitAll()
                .antMatchers(HttpMethod.GET,"/v1/gymguru/trainers").permitAll()
                .and()
                .formLogin().permitAll()
                .and()
                .logout().permitAll()
                .and()
                .csrf().disable();*/
