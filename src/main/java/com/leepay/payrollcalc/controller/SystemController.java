package com.leepay.payrollcalc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/system")
public class SystemController {

    @RequestMapping("/commonCode")
    public String commonCodePage() {
        return "/system/commonCode";
    }
}
