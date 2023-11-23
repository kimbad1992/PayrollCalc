package com.leepay.payrollcalc.controller;

import com.leepay.payrollcalc.dto.Mail;
import com.leepay.payrollcalc.service.MailService;
import com.leepay.payrollcalc.service.PlaygroundService;
import com.leepay.payrollcalc.util.PropUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/playground")
@Slf4j
public class PlaygroundController {

    @Autowired
    private MailService mailService;
    @Autowired
    private PlaygroundService playgroundService;

    @RequestMapping("/map")
    public String kakaoMap(Model model) {
        model.addAttribute("appKey", PropUtil.getProperty("system.kakao-api-key.js"));
        return "/playground/map";
    }

    @RequestMapping("/mail")
    public String mailSend() {
        return "/playground/mail";
    }

    @PostMapping("/sendMail.do")
    @ResponseBody
    public String doSend(@RequestBody Mail mail) {
        String succeed = mailService.sendMail(mail, mail.getMessage(), "/email/admin-register");
        return succeed;
    }

    @RequestMapping("/getCctvData.do")
    @ResponseBody
    public String getCctvData() {
        return playgroundService.getCctvData();
    }
}
