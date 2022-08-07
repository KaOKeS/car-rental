package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.service.contract.EmailSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class EmailSenderService implements EmailSender {
    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String to,String subject,String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("takethatcar@gmail.com");
        message.setTo(to);
        message.setText(body);
        message.setSubject(subject);

        mailSender.send(message);

        log.info("Mail sending to: " + to);
        System.out.println("Mail sent successfully.");
    }
}
