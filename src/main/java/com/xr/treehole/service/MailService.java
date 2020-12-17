package com.xr.treehole.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {

    @Value("${spring.mail.username}")
    private String from;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    public void SendSimpleMail(String to, String subject, String content){

        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setTo(to);
        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        mailMessage.setFrom(from);

        mailSender.send(mailMessage);
    }

    // TODO: log
    public void SendHtmlMail(String to, String subject, String content){
        MimeMessage mimeMessage = mailSender.createMimeMessage();

        MimeMessageHelper helper;

        try {
            helper = new MimeMessageHelper(mimeMessage, true);
            helper.setFrom(from);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, true);
            mailSender.send(mimeMessage);
        }catch (MessagingException e){
            e.printStackTrace();
        }
    }

    public void SendTemplateMail(String to, String subject, String registerCode){
        Context context = new Context();
        context.setVariable("id", registerCode);
        String emailContent = templateEngine.process("mailTemplate", context);

        this.SendHtmlMail(to, subject, emailContent);

    }

}
