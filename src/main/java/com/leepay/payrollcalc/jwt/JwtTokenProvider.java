package com.leepay.payrollcalc.jwt;

import com.leepay.payrollcalc.exception.CommonException;
import com.leepay.payrollcalc.exception.ErrorCode;
import com.leepay.payrollcalc.service.AdminLoginDetailService;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    @Autowired
    private AdminLoginDetailService adminLoginDetailService;

    private String secretKey = "yourSecretKey";

    // JWT 토큰에서 인증 정보를 추출하는 메서드
    public Authentication getAuthentication(String token) {
        // 토큰에서 사용자 이름 추출 (이 부분은 토큰 파싱 로직에 따라 다를 수 있음)
        String username = "leepay";

        // UserDetailsService를 사용하여 UserDetails 객체 로드
        UserDetails userDetails = adminLoginDetailService.loadUserByUsername(username);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    // JWT 토큰의 유효성을 검사하는 메서드
    public boolean validateToken(String token) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    // 요청 헤더에서 토큰을 추출하는 메서드
    public String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
