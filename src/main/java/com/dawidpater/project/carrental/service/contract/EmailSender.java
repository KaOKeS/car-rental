package com.dawidpater.project.carrental.service.contract;

public interface EmailSender {
    public void sendEmail(String to, String subject,String body);
}
