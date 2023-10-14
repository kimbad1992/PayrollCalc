package com.leepay.payrollcalc.handler;

import com.leepay.payrollcalc.dto.ErrorResponse;
import com.leepay.payrollcalc.exception.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ModelAttribute;

@Slf4j
@ControllerAdvice
public class GlobalHandler {

    @ExceptionHandler(Exception.class)
    public void handleException(Exception e, HttpServletRequest request, HttpServletResponse response) {
        log.error("Exception caught", e);
    }

    @ExceptionHandler(CommonException.class)
    public ResponseEntity handleCommonException(Exception e) {
        log.error("Exception caught", e);
        return new ErrorResponse(e).build();
    }

    /* 페이지 URL을 Model에 담기 위해 사용 */
    @ModelAttribute("servletPath")
    String getRequestServletPath(HttpServletRequest request) {
        return request.getServletPath();
    }
}
