package com.leepay.payrollcalc.controller;

import com.leepay.payrollcalc.util.PropUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/playground")
public class PlaygroundController {

    @RequestMapping("/map")
    public String kakaoMap(Model model) {
        model.addAttribute("appKey", PropUtil.getProperty("system.kakao-api-key.js"));
        return "/playground/map";
    }
}
