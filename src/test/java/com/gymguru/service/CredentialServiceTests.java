package com.gymguru.service;

import com.gymguru.config.Deleter;
import com.gymguru.controller.exception.single.CredentialNotFoundException;
import com.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.gymguru.controller.exception.single.InvalidCredentialException;
import com.gymguru.domain.Credential;
import com.gymguru.domain.User;
import com.gymguru.domain.enums.CredentialType;
import com.gymguru.security.PasswordChanger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CredentialServiceTests {

    @Autowired
    private CredentialService credentialService;

    @Autowired
    private UserService userService;

    @Autowired
    private Deleter deleter;

    @Autowired
    PasswordEncoder passwordEncoder;

    @AfterEach
    public void clean() {
        deleter.deleteAllFromEachEntity();
    }

    @Test
    void testGetAllCredentials() throws EmailAlreadyExistException {
        //Given
        Credential credential1 = new Credential("test1@example.com", "password1", CredentialType.ROLE_USER);
        Credential credential2 = new Credential("test2@example.com", "password2", CredentialType.ROLE_USER);
        User user1 = new User("john", "smith", credential1);
        User user2 = new User("mike", "jphnson", credential2);

        userService.saveUser(user1);
        userService.saveUser(user2);

        //When
        List<Credential> credentials = credentialService.getAll();

        //Then
        assertEquals(2, credentials.size());
    }

    @Test
    void testGetCredentialById() throws CredentialNotFoundException, EmailAlreadyExistException {
        //Given
        Credential credential = new Credential("test1@example.com", "password1", CredentialType.ROLE_USER);
        User user = new User("john", "smith", credential);

        userService.saveUser(user);

        //When
        Credential resultCredential = credentialService.getById(credential.getId());
        Long credentialId = credential.getId();

        //Then
        assertEquals(credentialId, resultCredential.getId());
    }

    @Test
    void testGetCredentialByEmail() throws CredentialNotFoundException, EmailAlreadyExistException {
        //Given
        Credential credential = new Credential("test1@example.com", "password1", CredentialType.ROLE_USER);
        User user = new User("john", "smith", credential);

        userService.saveUser(user);

        //When
        Credential resultCredential = credentialService.getByEmail(credential.getEmail());
        Long credentialId = credential.getId();

        //Then
        assertEquals(credentialId, resultCredential.getId());
    }

    @Test
    void testChangePassword() throws CredentialNotFoundException, InvalidCredentialException, EmailAlreadyExistException {
        //Given
        Credential credential = new Credential("test@example.com", "password", CredentialType.ROLE_USER);
        User user = new User("john", "smith", credential);
        PasswordChanger passwordChanger = new PasswordChanger("test@example.com", "password", "newpassword");

        userService.saveUser(user);

        //When
        credentialService.changePassword(passwordChanger);
        Credential updatedCredential = credentialService.getById(credential.getId());

        //Then
        assertTrue(passwordEncoder.matches("newpassword", updatedCredential.getPassword()));
    }

    @Test
    void testGetCredentialIdByEmail() throws CredentialNotFoundException, EmailAlreadyExistException {
        //Given
        Credential credential = new Credential("test@example.com", "password", CredentialType.ROLE_USER);
        User user = new User("john", "smith", credential);

        userService.saveUser(user);

        //When
        Long id = credentialService.getCredentialIdByEmail(credential.getEmail());

        //Then
        assertEquals(credential.getId(), id);
    }

    @Test
    void testIsEmailAvailable() throws EmailAlreadyExistException {
        ///Given
        Credential credential = new Credential("test@example.com", "password", CredentialType.ROLE_USER);
        User user = new User("john", "smith", credential);

        userService.saveUser(user);

        //When
        boolean available = credentialService.isEmailAvailable("new@example.com");
        boolean unavailable = credentialService.isEmailAvailable("test@example.com");

        //Then
        assertTrue(available);
        assertFalse(unavailable);
    }

    @Test
    void testEncodePassword() throws EmailAlreadyExistException {
        ///Given
        Credential credential = new Credential("test@example.com", "password", CredentialType.ROLE_USER);
        User user = new User("john", "smith", credential);

        userService.saveUser(user);

        assertTrue(passwordEncoder.matches("password", credential.getPassword()));
    }

    @Test
    void testCascadeWhenRemoveUser() throws EmailAlreadyExistException {
        ///Given
        Credential credential = new Credential("test@example.com", "password", CredentialType.ROLE_USER);
        User user = new User("john", "smith", credential);

        userService.saveUser(user);

        //When
        int sizeBeforeDelete  = credentialService.getAll().size();
        deleter.deleteFromUsers();
        int sizeAfterDelete  = credentialService.getAll().size();

        //Then
        assertEquals(1, sizeBeforeDelete);
        assertEquals(0, sizeAfterDelete);
    }
}


