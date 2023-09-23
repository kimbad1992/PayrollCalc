package com.leepay.payrollcalc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {

    @RequestMapping("/login")
    public String login() {
        return "/login/login";
    }

    @RequestMapping("/main")
    public String main() {
        return "/main";
    }
}
