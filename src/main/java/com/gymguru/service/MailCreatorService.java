package com.gymguru.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
public class MailCreatorService {

    @Autowired
    @Qualifier("templateEngine")
    private TemplateEngine templateEngine;

    public String buildSubscribersMail(String message, String userName) {
        Context context = new Context();
        context.setVariable("message", message);
        context.setVariable("user_name", userName);
        context.setVariable("company_name", "GYM-GURU");
        context.setVariable("preview_message", "Welcome!");
        context.setVariable("goodbye_message", "Regards, GYM-GURU team!");
        return templateEngine.process("mail/gymguru-subscribers-mail", context);
    }
}