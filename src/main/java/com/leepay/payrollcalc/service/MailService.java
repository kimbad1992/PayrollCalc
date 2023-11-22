package com.leepay.payrollcalc.service;

import com.leepay.payrollcalc.dto.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.spring6.SpringTemplateEngine;

import static org.springframework.security.core.context.SecurityContextHolder.setContext;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public String sendMail(Mail mail, String content) {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(mail.getTo()); // 메일 수신자
            mimeMessageHelper.setSubject(mail.getSubject()); // 메일 제목
            mimeMessageHelper.setText(content); // 메일 본문 내용, HTML 여부
            javaMailSender.send(mimeMessage);

            log.info("Success");

            return "yes";

        } catch (MessagingException e) {
            log.info("fail");
            throw new RuntimeException(e);
        }
    }
}
