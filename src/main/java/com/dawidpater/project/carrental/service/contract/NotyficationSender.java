package com.dawidpater.project.carrental.service.contract;

public interface NotyficationSender {
    void send(String to, String subject,String body);
}
