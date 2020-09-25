package com.smolderingdrake.homelibrarycore.service;

import com.smolderingdrake.homelibrarycore.model.EmailForm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Profile("email")
@Service
public class EmailService {

    private final JavaMailSender javaMailSender;

    @Value("${email.receiver}")
    private String receiver;

    public EmailService(final JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public void sendMail(final EmailForm email) throws MessagingException {
        final MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        final MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
        helper.setTo(receiver);
        helper.setSubject(email.getSubject());
        helper.setText(email.getMessage());
        javaMailSender.send(mimeMessage);
    }
}
