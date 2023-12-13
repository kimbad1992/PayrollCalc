package com.leepay.payrollcalc.handler;

import com.leepay.payrollcalc.dto.ErrorResponse;
import com.leepay.payrollcalc.exception.CommonException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
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

    /* 세션 유지 시간 표기를 위해 사용 */
    @ModelAttribute("sessionTime")
    Long getSessionRemainingTime(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            long currentTime = System.currentTimeMillis();
            // 세션 만료 시간 - 현재 시간 = 잔여 시간
            long remainingTime = session.getMaxInactiveInterval() * 1000L - (currentTime - session.getLastAccessedTime());
            return Math.max(remainingTime, 0); // 음수가 되지 않도록 조정
        }
        return null;
    }
}
