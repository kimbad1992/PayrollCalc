package com.leepay.payrollcalc.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.leepay.payrollcalc.dto.ErrorResponse;
import com.leepay.payrollcalc.exception.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;
import java.io.PrintWriter;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error("Exception caught", e);
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity handleCommonException(Exception e) {
        log.error("Exception caught", e);
        return new ErrorResponse(e).build();
    }
}
