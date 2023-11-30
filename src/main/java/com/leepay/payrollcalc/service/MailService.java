package com.leepay.payrollcalc.service;

import com.leepay.payrollcalc.dto.Mail;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.util.MapUtils;

import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final SpringTemplateEngine templateEngine;

    public String sendMail(Mail mail, String templeteLocation, Map<String, Object> variables) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(mail.getTo()); // 메일 수신자
            mimeMessageHelper.setSubject(mail.getSubject()); // 메일 제목

            String htmlContent = "";
            if (StringUtils.hasText(templeteLocation)) {
                Context context = new Context();
                if (!MapUtils.isEmpty(variables)) {
                    context.setVariable("message", mail.getMessage());
                    context.setVariables(variables);
                }
                htmlContent = templateEngine.process(templeteLocation, context);
            } else {
                htmlContent = mail.getMessage();
            }
            mimeMessageHelper.setText(htmlContent, true); // HTML 콘텐츠 설정

            javaMailSender.send(mimeMessage);
            return "T";
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    public String sendMail(Mail mail) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            mimeMessageHelper.setTo(mail.getTo()); // 메일 수신자
            mimeMessageHelper.setSubject(mail.getSubject()); // 메일 제목

            String htmlContent = "";
            htmlContent = mail.getMessage();
            mimeMessageHelper.setText(htmlContent, true); // HTML 콘텐츠 설정

            javaMailSender.send(mimeMessage);
            return "T";
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
