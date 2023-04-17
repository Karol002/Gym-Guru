package com.gymguru.service;

import com.gymguru.config.Deleter;
import com.gymguru.controller.exception.single.CredentialNotFoundException;
import com.gymguru.controller.exception.single.EmailAlreadyExistException;
import com.gymguru.controller.exception.single.UserNotFoundException;
import com.gymguru.domain.Credential;
import com.gymguru.domain.User;
import com.gymguru.domain.enums.CredentialType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTests {
    @Autowired
    private UserService userService;

    @Autowired
    private Deleter deleter;

    @AfterEach
    public void clean() {
        deleter.deleteAllFromEachEntity();
    }

    @Test
    void testGetUsersEmails() throws EmailAlreadyExistException, UserNotFoundException {
        //Given
        Credential credential1 = new Credential("test1@example.com", "password1", CredentialType.ROLE_USER);
        Credential credential2 = new Credential("test2@example.com", "password2", CredentialType.ROLE_USER);
        User user1 = new User("john", "smith", credential1);
        User user2 = new User("mike", "johnson", credential2);

        userService.saveUser(user1);
        userService.saveUser(user2);

        //When
        List<User> users = userService.getAllUsers();

        //Then
        assertEquals(2, users.size());
    }

    @Test
    void testGetAllEmails() throws EmailAlreadyExistException {
        //Given
        Credential credential1 = new Credential("test1@example.com", "password1", CredentialType.ROLE_USER);
        Credential credential2 = new Credential("test2@example.com", "password2", CredentialType.ROLE_USER);
        User user1 = new User("john", "smith", credential1);
        User user2 = new User("mike", "jphnson", credential2);

        userService.saveUser(user1);
        userService.saveUser(user2);

        //When
        List<String> emails = userService.getAllEmails();

        //Then
        assertEquals(2, emails.size());
        assertTrue(emails.contains("test1@example.com"));
        assertTrue(emails.contains("test2@example.com"));
    }

    @Test
    void testGetUserById() throws EmailAlreadyExistException, UserNotFoundException {
        //Given
        Credential credential = new Credential("test@example.com", "password", CredentialType.ROLE_USER);
        User user = new User("john", "smith", credential);

        userService.saveUser(user);

        //When
        User resultUser = userService.getUserById(user.getId());
        Long userId = user.getId();

        //Then
        assertEquals(userId, resultUser.getId());
    }

    @Test
    void testGetUserByEmail() throws EmailAlreadyExistException, CredentialNotFoundException, UserNotFoundException {
        //Given
        Credential credential = new Credential("test@example.com", "password", CredentialType.ROLE_USER);
        User user = new User("john", "smith", credential);

        userService.saveUser(user);

        //When
        User resultUser = userService.getUserByEmail(credential.getEmail());
        Long userId = user.getId();

        //Then
        assertEquals(userId, resultUser.getId());
    }

    @Test
    void testSaveUser() throws EmailAlreadyExistException, UserNotFoundException, CredentialNotFoundException {
        //Given
        Credential credential = new Credential("test@example.com", "password", CredentialType.ROLE_USER);
        User user = new User("john", "smith", credential);

        //When
        userService.saveUser(user);
        User resultUser = userService.getUserByEmail(credential.getEmail());
        Long userId = user.getId();

        //Then
        assertEquals(userId, resultUser.getId());
    }

    @Test
    public void testUpdateUser() throws UserNotFoundException, EmailAlreadyExistException {
        //Given
        Credential credential = new Credential("test@example.com", "password", CredentialType.ROLE_USER);
        User user = new User("john", "smith", credential);
        userService.saveUser(user);

        //When
        user.setFirstName("michael");
        userService.updateUser(user);

        //Then
        assertEquals("michael", userService.getUserById(user.getId()).getFirstName());
    }

    @Test
    void testSaveUserWithExistingEmail() throws EmailAlreadyExistException {
        //Given
        Credential credential1 = new Credential("test@example.com", "password1", CredentialType.ROLE_USER);
        Credential credential2 = new Credential("test@example.com", "password2", CredentialType.ROLE_USER);
        User user1 = new User("john", "smith", credential1);
        User user2 = new User("mike", "johnson", credential2);

        userService.saveUser(user1);

        //Then
        assertThrows(EmailAlreadyExistException.class, () -> userService.saveUser(user2));
    }

    @Test
    void testCascadeDeleteCredential() throws EmailAlreadyExistException {
        ///Given
        Credential credential = new Credential("test@example.com", "password", CredentialType.ROLE_USER);
        User user = new User("john", "smith", credential);

        userService.saveUser(user);

        //When
        int sizeBeforeDelete = userService.getAllUsers().size();
        assertThrows(DataIntegrityViolationException.class, () -> deleter.deleteFromCredentials());
        int sizeAfterDelete = userService.getAllUsers().size();

        //Then
        assertEquals(1, sizeBeforeDelete);
        assertEquals(1, sizeAfterDelete);
    }
}
