package com.leepay.payrollcalc.controller;

import com.leepay.payrollcalc.dto.GroupCode;
import com.leepay.payrollcalc.dto.StaticMenu;
import com.leepay.payrollcalc.service.SystemService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @RequestMapping("/commonCode")
    public String commonCodePage() {
        return "/system/commonCode";
    }
    @RequestMapping("/administration")
    public String administrationPage(Model model, HttpServletRequest request) {
        model.addAttribute("adminUserList", systemService.getAdminUserList());
        return "/system/administration";
    }

    @PostMapping("/insertGroupCode.do")
    public ResponseEntity insertGroupCode(@RequestBody List<GroupCode> groupCodes, Model model) {
        log.debug("groupCodes : {}", groupCodes);
        return null;
    }

    @GetMapping("/refreshMenu")
    @ResponseBody
    public String refreshMenu() {
        StaticMenu.menuList = systemService.getAllMenu();
        return "good";
    }
}
