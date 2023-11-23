package com.leepay.payrollcalc.service;

import com.leepay.payrollcalc.dto.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import java.util.Properties;

import static org.springframework.security.core.context.SecurityContextHolder.setContext;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public String sendMail(Mail mail, String content, String templeteLocation) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(mail.getTo()); // 메일 수신자
            mimeMessageHelper.setSubject(mail.getSubject()); // 메일 제목

            // Thymeleaf를 사용하여 HTML 콘텐츠 생성
            Context context = new Context();
            context.setVariable("message", content);
            String htmlContent = templateEngine.process(templeteLocation, context);
            mimeMessageHelper.setText(htmlContent, true); // HTML 콘텐츠 설정
            javaMailSender.send(mimeMessage);
            return "T";
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
