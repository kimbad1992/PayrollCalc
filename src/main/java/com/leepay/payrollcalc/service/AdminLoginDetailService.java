package com.leepay.payrollcalc.service;

import com.leepay.payrollcalc.dto.AdminDetails;
import com.leepay.payrollcalc.dto.AdminUser;
import com.leepay.payrollcalc.mapper.MainMapper;
import com.leepay.payrollcalc.mapper.SystemMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

@Service
@Slf4j
public class AdminLoginDetailService implements UserDetailsService {

    @Autowired
    private SystemMapper systemMapper;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isEmpty(username)) throw new UsernameNotFoundException("ID를 입력해주세요.");
        AdminUser adminUser = systemMapper.getAdminUserByUsername(username);

        if (adminUser == null) throw new UsernameNotFoundException("아이디 또는 비밀번호를 잘못 입력했습니다.");
        if (!adminUser.getEnabled()) throw new UsernameNotFoundException("사용할 수 없는 계정입니다.");
        return new AdminDetails(adminUser);
    }
}
