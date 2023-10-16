package com.leepay.payrollcalc.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ResidentNumber {
    private String frontNumber;
    private String rearNumber;

    public ResidentNumber(String residentNo) {
        String[] parts = residentNo.split("-");
        this.frontNumber = parts[0];
        this.rearNumber = parts[1];
    }

    public String getResidentNo() {
        return frontNumber + '-' + rearNumber;
    }
}
