package com.leepay.payrollcalc;


import com.leepay.payrollcalc.dto.ResidentNumber;
import com.leepay.payrollcalc.validation.ResidentNumberValidator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;

public class ValidResidentNoTest {

    @Test
    void 주민등록번호_테스트() {
        ResidentNumber rn1 = new ResidentNumber();
        ResidentNumber rn2 = new ResidentNumber();

        rn1.setFrontNumber("000401");
        rn2.setFrontNumber("990101");
        rn1.setRearNumber("4031123");
        rn2.setRearNumber("1234567");

        ResidentNumberValidator validator = new ResidentNumberValidator();
        assertFalse(validator.isValid(rn1, null));
        assertFalse(validator.isValid(rn2, null));
    }

}
