package com.leepay.payrollcalc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PhoneNumber {
    private String frontNumber;
    private String midNumber;
    private String rearNumber;

    public PhoneNumber(String phone) {
        String[] parts = phone.split("-");
        this.frontNumber = parts[0];
        this.midNumber = parts[1];
        this.rearNumber = parts[2];
    }

    public String getPhoneNumber() {
        return frontNumber + '-' + midNumber + '-' + rearNumber;
    }
}
