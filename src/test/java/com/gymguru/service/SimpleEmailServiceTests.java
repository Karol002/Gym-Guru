package com.gymguru.service;

import com.gymguru.domain.Mail;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class SimpleEmailServiceTests {

    private JavaMailSender javaMailSender;
    private SimpleEmailService simpleEmailService;

    @BeforeEach
    public void setUp() {
        javaMailSender = mock(JavaMailSender.class);
        simpleEmailService = new SimpleEmailService(javaMailSender);
    }

    @Test
    public void sendEmail_shouldSendEmail() {
        //Given
        Mail mail = new Mail("test@example.com", "Test email", "This is a test email");

        //When
        simpleEmailService.send(mail);

        //Then
        ArgumentCaptor<SimpleMailMessage> argumentCaptor = ArgumentCaptor.forClass(SimpleMailMessage.class);
        verify(javaMailSender).send(argumentCaptor.capture());

        SimpleMailMessage sentMail = argumentCaptor.getValue();
        assertEquals(mail.getMailTo(), Objects.requireNonNull(sentMail.getTo())[0]);
        assertEquals(mail.getSubject(), sentMail.getSubject());
        assertEquals(mail.getMessage(), sentMail.getText());
    }
}

