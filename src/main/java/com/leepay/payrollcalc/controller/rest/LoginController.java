package com.leepay.payrollcalc.controller.rest;

import com.leepay.payrollcalc.dto.ApiResponse;
import com.leepay.payrollcalc.dto.JwtToken;
import com.leepay.payrollcalc.service.LoginService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/login")
@Slf4j

public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping("/sign")
    public ResponseEntity login(@RequestBody Map<String, Object> map) {
        log.debug("logged in data : {}", map);
        JwtToken jwtToken = loginService.signIn((String) map.get("username"), (String) map.get("password"));
        log.debug("logged in token : {}", jwtToken);
        return new ApiResponse<>(jwtToken).build();
    }
}
