package com.v1.gymguru.security;

import com.v1.gymguru.service.CredentialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@Configuration
public class SecurityConfiguration {

    @Autowired
    private AuthenticationManager authenticationManager;
    private final AuthSuccessHandler authSuccessHandler;
    private final CredentialService credentialService;
    private final String secret;

    public SecurityConfiguration(AuthSuccessHandler authSuccessHandler, CredentialService credentialService, @Value("${jwt.secret}") String secret) {
        this.authSuccessHandler = authSuccessHandler;
        this.credentialService = credentialService;
        this.secret = secret;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeHttpRequests((auth) -> {
                    try {
                        auth
                                .antMatchers("/v1/gymguru/openai/**").permitAll()
                                .antMatchers("/v1/gymguru/trainers/**").permitAll()
                                .antMatchers("/v1/gymguru/users/**").permitAll()
                                .antMatchers("/v1/gymguru/wger/exercises/**").hasRole("TRAINER")
                                .antMatchers("/v1/gymguru/wger/categories/**").hasRole("TRAINER")
                                .anyRequest().authenticated() // pozostałe wymagają autoryzacji
                                .and()
                                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                                .and()
                                .addFilterBefore(authenticationFilter(), BasicAuthenticationFilter.class)
                                .addFilterAfter(new JwtAuthorizationFilter(authenticationManager, credentialService, secret), BasicAuthenticationFilter.class)
                                .exceptionHandling()
                                .authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED));
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }

    @Bean
    public JsonObjectAuthenticationFilter authenticationFilter() throws Exception {
        JsonObjectAuthenticationFilter filter = new JsonObjectAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(authSuccessHandler);
        filter.setAuthenticationManager(authenticationManager);
        return filter;
    }

}

  /* .antMatchers(HttpMethod.GET,"/v1/gymguru/exercises/plan/**").hasRole("USER")
           .antMatchers(HttpMethod.GET,"/v1/gymguru/meals/plan/**").hasRole("USER")
           .antMatchers(HttpMethod.GET,"/v1/gymguru/plans/**").hasRole("USER")
           .antMatchers(HttpMethod.GET,"/v1/gymguru/trainers/**").hasRole("USER")
           .antMatchers(HttpMethod.PUT,"/v1/gymguru/users").hasRole("USER")
           .antMatchers(HttpMethod.GET,"/v1/gymguru/users/**").hasRole("USER")
           .antMatchers(HttpMethod.GET,"/v1/gymguru/subscriptions/user/**").hasRole("USER")
           .antMatchers(HttpMethod.POST,"/v1/gymguru/subscriptions/user").hasRole("USER")
           .antMatchers(HttpMethod.PUT,"/v1/gymguru/subscriptions/user").hasRole("USER")

           .antMatchers(HttpMethod.POST,"/v1/gymguru/exercises").hasRole("TRAINER")
           .antMatchers(HttpMethod.POST,"/v1/gymguru/meals").hasRole("TRAINER")
           .antMatchers(HttpMethod.POST,"/v1/gymguru/plans").hasRole("TRAINER")
           .antMatchers(HttpMethod.PUT,"/v1/gymguru/plans").hasRole("TRAINER")
           .antMatchers(HttpMethod.PUT,"/v1/gymguru/trainers").hasRole("TRAINER")
           .antMatchers(HttpMethod.GET,"/v1/gymguru/subscriptions/trainer/**").hasRole("TRAINER")
           .antMatchers(HttpMethod.GET,"/v1/gymguru/subscriptions/trainer/**").hasRole("TRAINER")
           .antMatchers(HttpMethod.GET,"/v1/gymguru/openai").permitAll()
           .antMatchers("/v1/gymguru/edamam/**").hasRole("USER")
           .antMatchers("/v1/gymguru/wger/exercises/**").hasRole("USER")

           .antMatchers(HttpMethod.POST,"/v1/gymguru/users").permitAll()
           .antMatchers(HttpMethod.GET,"/v1/gymguru/trainers").permitAl
           .antMatchers("/v1/gymguru/wger/exercises/**").hasRole("USER")
           .antMatchers("/v1/gymguru/wger/categories/**").hasRole("USER")
*/