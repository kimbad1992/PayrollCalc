package com.leepay.payrollcalc.service;

import com.leepay.payrollcalc.dto.AdminDetails;
import com.leepay.payrollcalc.dto.AdminUser;
import com.leepay.payrollcalc.mapper.MainMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AdminLoginDetailService implements UserDetailsService {

    @Autowired
    private MainMapper mainMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AdminUser adminUser = mainMapper.getAdminUser(username);

        if (adminUser == null) throw new UsernameNotFoundException(username + "을 찾을 수 없습니다.");
        if (!adminUser.getEnabled()) throw new UsernameNotFoundException("사용할 수 없는 계정입니다.");
        return new AdminDetails(adminUser);
    }
}
