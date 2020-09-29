package com.smolderingdrake.homelibrarycore.controller;

import com.smolderingdrake.homelibrarycore.model.EmailForm;
import com.smolderingdrake.homelibrarycore.service.EmailService;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.validation.Valid;

@Profile("email")
@CrossOrigin
@RestController
@RequestMapping("/api/contact")
public class ContactController {

    private final EmailService emailService;

    public ContactController(final EmailService emailService) {
        this.emailService = emailService;
    }

    @GetMapping
    public EmailForm getForm() {
        return new EmailForm("from", "sub", "mes");
    }

    @PostMapping
    public void sendEmail(@Valid @RequestBody final EmailForm emailForm) throws MessagingException {
        emailService.sendMail(emailForm);
    }
}
