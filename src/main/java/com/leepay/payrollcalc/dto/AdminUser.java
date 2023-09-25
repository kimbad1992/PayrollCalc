package com.leepay.payrollcalc.dto;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
public class AdminUser implements Serializable{

    @Serial
    private static final long serialVersionUID = 1L;

    private Long id;
    private String username;
    private String password;
    private Boolean enabled;
    private List<String> roles;
}
