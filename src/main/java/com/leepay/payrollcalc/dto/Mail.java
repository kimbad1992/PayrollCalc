package com.leepay.payrollcalc.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Mail {
    private String to;
    private String from;
    private String subject;
    private String message;
}
