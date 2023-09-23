package com.leepay.payrollcalc.provider;

import com.leepay.payrollcalc.dto.AdminDetails;
import com.leepay.payrollcalc.dto.AdminUser;
import com.leepay.payrollcalc.service.AdminLoginDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class AdminAuthenticatorProvider implements AuthenticationProvider {

    @Autowired
    private AdminLoginDetailService adminLoginDetailService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        AdminDetails adminDetails = (AdminDetails) adminLoginDetailService.loadUserByUsername(username);

        String dbPassword = adminDetails.getPassword();

        //TODO : 암호화 추가
        if (!dbPassword.equals(password)) {
            throw new BadCredentialsException("아이디 또는 비밀번호가 일치하지 않습니다.");
        }

        AdminUser adminUser = adminDetails.getAdminUser();

        if (adminUser == null || !adminUser.getEnabled()) {
            throw new BadCredentialsException("사용할 수 없는 계정입니다.");
        }

        return new UsernamePasswordAuthenticationToken(adminDetails, null, adminDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
