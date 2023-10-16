package com.leepay.payrollcalc.validation;

import com.leepay.payrollcalc.dto.PhoneNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PhoneValidator implements ConstraintValidator<ValidPhoneNumber, PhoneNumber> {

    @Override
    public boolean isValid(PhoneNumber phone, ConstraintValidatorContext context) {

        if (phone == null) {
            return false;
        }

        if (!phone.getPhoneNumber().matches("\\d{2,3}-\\d{3,4}-\\d{4}")) {
            return false;
        }

        return true;
    }
}
