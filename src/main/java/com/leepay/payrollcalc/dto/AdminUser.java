package com.leepay.payrollcalc.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

@Data
public class AdminUser {
    private Long id;
    private String username;
    private String password;
    private Boolean enabled;
    private List<String> roles;
}
