package com.example.kottabi.config;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import lombok.RequiredArgsConstructor;
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender mailSender;

    public void sendPassword(String email, String password) {

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(email);
        message.setSubject("Your Kottabi password");
        message.setText(
                "Welcome to Kottabi!\n\n" +
                        "Your generated password is: " + password
        );

        mailSender.send(message);
    }

}