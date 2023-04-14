package com.v1.gymguru.security;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.v1.gymguru.controller.exception.single.CredentialNotFoundException;
import com.v1.gymguru.controller.exception.single.TrainerNotFoundException;
import com.v1.gymguru.controller.exception.single.UserNotFoundException;
import com.v1.gymguru.domain.Credential;
import com.v1.gymguru.domain.CredentialType;
import com.v1.gymguru.service.CredentialService;
import com.v1.gymguru.service.TrainerService;
import com.v1.gymguru.service.UserService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

@Component
@Slf4j
public class AuthSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final int expTime;
    private final String secret;
    private final UserService userService;
    private final TrainerService trainerService;
    private final CredentialService credentialService;

    public AuthSuccessHandler(@Value("${jwt.expiration}") int expTime, @Value("${jwt.secret}") String secret, UserService userService, TrainerService trainerService, CredentialService credentialService) {
        this.expTime = expTime;
        this.secret = secret;
        this.userService = userService;
        this.trainerService = trainerService;
        this.credentialService = credentialService;
    }

    @SneakyThrows
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication){
        UserDetails principal = (UserDetails) authentication.getPrincipal();
        Credential credential = credentialService.getByEmail(principal.getUsername());
        CredentialType credentialType = credential.getRole();
        Long userId = getId(credential);

        String token = JWT.create()
                .withSubject(credentialService.getByEmail(principal.getUsername()).getUsername())
                .withClaim("credentialType", credentialType.toString())
                .withExpiresAt(Date.from(Instant.ofEpochMilli(ZonedDateTime.now(ZoneId.systemDefault()).toInstant().toEpochMilli() + expTime)))
                .sign(Algorithm.HMAC256(secret));
        response.addHeader("Authorization", "Bearer " + token);
        response.addHeader("Content-Type", "application/json");
        response.getWriter().write("{\"token\": \""+token+"\", \"role\": \""+credentialType+"\", \"userId\": \""+userId+"\"}");
    }

    private Long getId(Credential credential) throws UserNotFoundException, CredentialNotFoundException, TrainerNotFoundException {
        if (credential.getRole() == CredentialType.ROLE_USER) {
            return userService.getUserByEmail(credential.getEmail()).getId();
        } else {
            return trainerService.getTrainerByEmail(credential.getEmail()).getId();
        }
    }
}