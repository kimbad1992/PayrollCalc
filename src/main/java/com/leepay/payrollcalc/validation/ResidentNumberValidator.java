package com.leepay.payrollcalc.validation;

import com.leepay.payrollcalc.dto.PhoneNumber;
import com.leepay.payrollcalc.dto.ResidentNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ResidentNumberValidator implements ConstraintValidator<ValidResidentNo, ResidentNumber> {

    @Override
    public boolean isValid(ResidentNumber residentNo, ConstraintValidatorContext context) {
        if (residentNo == null) {
            return false;
        }

        if (!residentNo.getResidentNo().matches("\\d{6}-\\d{7}")) { // 길이 검사
            return false;
        }

        return true;
    }
}
