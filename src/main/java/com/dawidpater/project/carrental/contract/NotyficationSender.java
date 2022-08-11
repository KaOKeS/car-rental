package com.dawidpater.project.carrental.contract;

public interface NotyficationSender {
    void send(String to, String subject,String body);
}
