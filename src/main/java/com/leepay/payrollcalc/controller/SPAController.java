package com.leepay.payrollcalc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class SPAController {

    @RequestMapping(value={"/", "/anotherPath/**"})
    public String index() {
        return "forward:/index.html";
    }
}
