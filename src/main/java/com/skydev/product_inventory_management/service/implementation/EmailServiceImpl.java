package com.skydev.product_inventory_management.service.implementation;

import com.skydev.product_inventory_management.service.interfaces.IMailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.File;
import java.nio.charset.StandardCharsets;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements IMailService {

    @Value("${email.sender.username}")
    private String username;

    private final JavaMailSender mailSender;

    @Override
    public void sendEmail(String toUser, String subject, String body) {

        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setFrom(username);
        simpleMailMessage.setTo(toUser);
        simpleMailMessage.setSubject(subject);
        simpleMailMessage.setText(body);

        mailSender.send(simpleMailMessage);

    }

    @Override
    public void sendEmailWithFile(String toUser, String subject, String body, File file) throws MessagingException {

        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, StandardCharsets.UTF_8.name());

        mimeMessageHelper.setFrom(username);
        mimeMessageHelper.setTo(toUser);
        mimeMessageHelper.setSubject(subject);
        mimeMessageHelper.setText(body, StandardCharsets.UTF_8.name());
        mimeMessageHelper.addAttachment(file.getName(), file);

        mailSender.send(mimeMessage);

    }
}
