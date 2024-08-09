package com.leepay.payrollcalc.controller;

import com.leepay.payrollcalc.constant.Constant;
import com.leepay.payrollcalc.dto.Mail;
import com.leepay.payrollcalc.dto.StompMessage;
import com.leepay.payrollcalc.service.MailService;
import com.leepay.payrollcalc.service.PlaygroundService;
import com.leepay.payrollcalc.util.PapagoUtil;
import com.leepay.payrollcalc.util.PropUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.Principal;
import java.util.HashMap;
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

    @RequestMapping("/chat")
    public String chatRoom() {
        return "/playground/chatRoom";
    }

    @RequestMapping("/lostark")
    public String lostark(Model model) {
         model.addAttribute("appKey", "Bearer "+PropUtil.getProperty("system.lostark-api-key"));
        return "/playground/lostark";
    }

    @RequestMapping("/llm")
    public String llm(Model model) {
        return "/playground/llm";
    }

    @PostMapping("/translate.do")
    @ResponseBody
    public String translate(@RequestBody String message) {
        return PapagoUtil.post(URLEncoder.encode(message, StandardCharsets.UTF_8));
    }

    @PostMapping("/sendMail.do")
    @ResponseBody
    public String doSend(@RequestBody Mail mail) {
        mailService.sendMail(mail);
        return "T";
    }

    @RequestMapping("/getCctvData.do")
    @ResponseBody
    public String getCctvData() {
        return playgroundService.getPlaygroundData(Constant.PLAYGROUND_CCTV);
    }

    @RequestMapping("/getRaidData.do")
    @ResponseBody
    public String getRaidData() {
        return playgroundService.getPlaygroundData(Constant.PLAYGROUND_RAID);
    }

    @MessageMapping("/sendMessage")
    @SendTo("/topic/public")
    public StompMessage sendMessage(StompMessage message, Principal principal) {
        message.setUsername(principal.getName());
        return message;
    }


}