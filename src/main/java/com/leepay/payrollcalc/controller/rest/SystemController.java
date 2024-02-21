package com.leepay.payrollcalc.controller.rest;

import com.leepay.payrollcalc.dto.*;
import com.leepay.payrollcalc.service.MailService;
import com.leepay.payrollcalc.service.SystemService;
import com.leepay.payrollcalc.util.CommonUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @Autowired
    private MailService mailService;

    @GetMapping("/menuList")
    public ResponseEntity getMenuList() {
        return new ApiResponse<>(systemService.getAllMenu()).build();
    }

    @PostMapping("/insertGroupCode.do")
    public ResponseEntity insertGroupCode(@RequestBody List<GroupCode> groupCodes, Model model) {
        log.debug("groupCodes : {}", groupCodes);
        return null;
    }

    @RequestMapping("/adminUserRegister.do")
    @ResponseBody
    public ResponseEntity<?> adminUserRegister(@Valid @ModelAttribute AdminUser adminUser, BindingResult bindingResult) {
        log.debug("운영자 정보 : {}", adminUser);

        if(bindingResult.hasErrors()) {
            return new ErrorResponse(bindingResult).build();
        }

        systemService.adminUserRegister(adminUser);

        Mail mail = Mail.builder()
                .to(adminUser.getEmail())
                .subject(EmailEnum.ADMIN_REGISTER.getSubject())
                .build();

        mailService.sendMail(mail, EmailEnum.ADMIN_REGISTER.getTemplateLocation()
                ,CommonUtil.convertDtoToMap(adminUser));
        return new ApiResponse<>().build();
    }
}
