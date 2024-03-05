package com.leepay.payrollcalc.handler;

import com.leepay.payrollcalc.dto.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error("Exception caught", e);
        return new ErrorResponse(e).build();
    }

    @ExceptionHandler({UsernameNotFoundException.class, BadCredentialsException.class})
    public ResponseEntity handleCommonException(Exception e) {
        log.error("Login Exception caught", e);
        return new ErrorResponse(e).build();
    }
}
