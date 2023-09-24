package com.leepay.payrollcalc.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    // Spring Security의 Exception은 여기서 처리하지 않음

    // 통합
    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception e) {
        log.error("Exception caught", e);

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("/common/error");
        modelAndView.addObject("message", e.getMessage());

        return modelAndView;
    }
}
