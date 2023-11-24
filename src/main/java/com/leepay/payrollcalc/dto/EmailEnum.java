package com.leepay.payrollcalc.dto;

import lombok.Getter;

@Getter
public enum EmailEnum {
    ADMIN_REGISTER("/email/admin-register", "안녕?");

    private final String templateLocation;
    private final String subject;

    EmailEnum(String templateLocation, String subject) {
        this.templateLocation = templateLocation;
        this.subject = subject;
    }

}
