package com.leepay.payrollcalc.dto;

import lombok.Data;

@Data
public class StompMessage {
    private String content;
    private String username;
}
