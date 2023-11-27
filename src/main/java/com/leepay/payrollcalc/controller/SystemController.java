package com.leepay.payrollcalc.controller;

import com.leepay.payrollcalc.dto.*;
import com.leepay.payrollcalc.service.MailService;
import com.leepay.payrollcalc.service.SystemService;
import com.leepay.payrollcalc.util.CommonUtil;
import com.leepay.payrollcalc.util.RandomUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Controller
@RequestMapping("/system")
public class SystemController {

    @Autowired
    private SystemService systemService;

    @Autowired
    private RandomUtil randomUtil;

    @Autowired
    private MailService mailService;

    @RequestMapping("/commonCode")
    public String commonCodePage() {
        return "/system/commonCode";
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

    /* 관리자 조회 페이지 */
    @RequestMapping("/administration")
    public String administrationPage(Model model, HttpServletRequest request) {
        model.addAttribute("adminUserList", systemService.getAdminUserList());
        return "/system/administration";
    }

    /* 관리자 등록 페이지 */

    @RequestMapping("/register")
    public String adminUserRegisterPage() {
        return "/system/register";
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
//        return "/system/register";
    }

    /* 관리자 등록 시 임시 비밀번호 생성 */
    @RequestMapping("/getRandomString")
    @ResponseBody
    public Map<String, Object> getRandomString() {
        String randomKey = randomUtil.generate(10);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("randomKey", randomKey);
        return map;
    }

    @RequestMapping("/edit/{id}")
    public String adminEdit(@PathVariable Integer id, Model model) {
        model.addAttribute("adminUser", systemService.getAdminUserList());
        return null;
    }

    @RequestMapping("/profile")
    public String goProfile(Model model, @AuthenticationPrincipal AdminDetails adminDetails) {
        model.addAttribute("adminUser",
                systemService.getAdminUserById(adminDetails.getId()));
        return "/system/profile";
    }
}
