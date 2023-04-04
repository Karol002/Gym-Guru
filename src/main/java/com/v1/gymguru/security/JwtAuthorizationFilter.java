package com.v1.gymguru.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.v1.gymguru.controller.exception.single.CredentialNotFoundException;
import com.v1.gymguru.controller.exception.single.InvalidEmailException;
import com.v1.gymguru.controller.exception.single.InvalidTokenException;
import com.v1.gymguru.service.CredentialService;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    private static final String TOKEN_PREFIX = "Bearer ";
    private final CredentialService credentialService;
    private final String secret;

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager, CredentialService credentialService, String secret) {
        super(authenticationManager);
        this.credentialService = credentialService;
        this.secret = secret;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        UsernamePasswordAuthenticationToken auth = null;
        try {
            auth = getAuthentication(request);
            SecurityContextHolder.getContext().setAuthentication(auth);
            filterChain.doFilter(request, response);
        } catch (InvalidTokenException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid token");
        } catch (CredentialNotFoundException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Credential not found");
        } catch (InvalidEmailException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid email");
        } catch (TokenExpiredException e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token has expired");
        } catch (JWTDecodeException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid token format");
        }
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) throws InvalidTokenException, CredentialNotFoundException, InvalidEmailException {
        String token = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (token == null || !token.startsWith(TOKEN_PREFIX)) {
            //throw new InvalidTokenException();
            return new UsernamePasswordAuthenticationToken(null, null, null);
        }

        try {
            String email = JWT.require(Algorithm.HMAC256(secret))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""))
                    .getSubject();
            if (email.isEmpty()) return new UsernamePasswordAuthenticationToken(null, null, null);
            UserDetails userDetails = credentialService.getByEmail(email);
            return new UsernamePasswordAuthenticationToken(userDetails.getUsername(), null, userDetails.getAuthorities());
        } catch (TokenExpiredException e) {
            throw new TokenExpiredException("The token has expired");
        }
    }
}
