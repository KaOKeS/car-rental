package com.dawidpater.project.carrental.service;

import com.dawidpater.project.carrental.service.contract.NotyficationSender;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class NotyficationSenderService implements NotyficationSender {
    private final JavaMailSender mailSender;

    @Override
    public void send(String to,String subject,String body){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("takethatcar@gmail.com");
        message.setTo(to);
        message.setText(body);
        message.setSubject(subject);

        log.debug("Trying to send email to: {}, with subject: {} and body: {}",to,subject,body);
        mailSender.send(message);
        log.info("Mail sent");
    }
}
