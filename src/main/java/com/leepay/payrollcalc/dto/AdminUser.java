package com.leepay.payrollcalc.dto;

import lombok.Data;

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
    private String email;
    private String oauth_provider;
    private String oauth_provider_id;
    private String oauth_profile_picture;
    private Boolean two_factor_enabled;
    private String two_factor_secret;
}
