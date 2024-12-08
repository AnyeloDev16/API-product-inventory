package com.skydev.product_inventory_management.service.interfaces;

import jakarta.mail.MessagingException;

import java.io.File;

public interface IMailService {

    void sendEmail(String toUser, String subject, String body);
    void sendEmailWithFile(String toUser, String subject, String body, File file) throws MessagingException;

}
