package com.v1.gymguru.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
public class EmailConfig {
    @Value("${admin.mail}")
    private String adminMail;
}
