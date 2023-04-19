package com.gymguru.service;

import com.gymguru.domain.Mail;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;


@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {
    public final static String SUBJECT = "GYM-GURU";
    public final static String SUBSCRIPTION_EXPIRED = "The validity of your subscription has expired";
    public final static String SUBSCRIPTION_ACTIVE = "Thank you for choosing GYM-GURU";
    private final JavaMailSender javaMailSender;
    private final MailCreatorService mailCreatorService;

   public void send(final Mail mail) {
       log.info("Starting email preparation...");
       try {
           javaMailSender.send(createSubscribersMessage(mail));
           log.info("Email has been sent.");
       } catch (MailException e) {
           log.error("Failed to process email sending: " + e.getMessage(), e);
       }
   }

    private MimeMessagePreparator createSubscribersMessage(final Mail mail) {
        return mimeMessage -> {
            MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
            messageHelper.setTo(mail.getMailTo());
            messageHelper.setSubject(mail.getSubject());
            messageHelper.setText(mailCreatorService.buildSubscribersMail(mail.getMessage(), mail.getMailTo()), true);
        };
    }
}
