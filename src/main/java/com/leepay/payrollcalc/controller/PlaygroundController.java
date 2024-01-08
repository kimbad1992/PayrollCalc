package com.leepay.payrollcalc.controller;

import com.leepay.payrollcalc.constant.Constant;
import com.leepay.payrollcalc.dto.Mail;
import com.leepay.payrollcalc.dto.StompMessage;
import com.leepay.payrollcalc.service.MailService;
import com.leepay.payrollcalc.service.PlaygroundService;
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
        String appKey = "Bearer "+PropUtil.getProperty("system.lostark-api-key");
        model.addAttribute("appKey", appKey);
        return "/playground/lostark";
    }

    @RequestMapping("/llm")
    public String llm(Model model) {
        return "/playground/llm";
    }

    @PostMapping("/translate.do")
    @ResponseBody
    public String translate(@RequestBody String message) {
        String clientId = PropUtil.getProperty("system.naver-api-key.clientId");
        String clientSecret = PropUtil.getProperty("system.naver-api-key.secretKey");

        String apiURL = "https://openapi.naver.com/v1/papago/n2mt";
        String text;
        text = URLEncoder.encode(message, StandardCharsets.UTF_8);

        Map<String, String> requestHeaders = new HashMap<>();
        requestHeaders.put("X-Naver-Client-Id", clientId);
        requestHeaders.put("X-Naver-Client-Secret", clientSecret);

        return post(apiURL, requestHeaders, text);
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

    private static String post(String apiUrl, Map<String, String> requestHeaders, String text){
        HttpURLConnection con = connect(apiUrl);
        String postParams = "source=en&target=ko&text=" + text; //원본언어: 한국어 (ko) -> 목적언어: 영어 (en)
        try {
            con.setRequestMethod("POST");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.write(postParams.getBytes());
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 응답
                return readBody(con.getInputStream());
            } else {  // 에러 응답
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }

    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }

    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);

        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();

            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }

            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는데 실패했습니다.", e);
        }
    }
}