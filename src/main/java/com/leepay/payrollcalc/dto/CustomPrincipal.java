package com.leepay.payrollcalc.dto;

import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;

import java.security.Principal;
import java.util.Collection;

@Getter
public class CustomPrincipal implements Principal {

    private final String id;
    private String name;
    private String email;
    private final Collection<? extends GrantedAuthority> authorities;

    public CustomPrincipal(String id, String name, String email, Collection<? extends GrantedAuthority> authorities) {
        this.id =id;
        this.name = name;
        this.email = email;
        this.authorities = authorities;
    }
}
