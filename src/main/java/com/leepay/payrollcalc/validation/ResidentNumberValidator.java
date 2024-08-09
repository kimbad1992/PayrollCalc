package com.leepay.payrollcalc.validation;

import com.leepay.payrollcalc.annotation.ValidResidentNo;
import com.leepay.payrollcalc.dto.ResidentNumber;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.List;
import java.util.stream.IntStream;

public class ResidentNumberValidator implements ConstraintValidator<ValidResidentNo, ResidentNumber> {

    @Override
    public boolean isValid(ResidentNumber residentNo, ConstraintValidatorContext context) {
        if (residentNo == null) {
            return false;
        }

        if (!residentNo.getResidentNo().matches("\\d{6}-\\d{7}")) { // 길이 검사
            return false;
        }

        if (!validCode(residentNo)) {
            return false;
        }

        return true;
    }

    private boolean validCode(ResidentNumber residentNumber) {
        String residentNo = residentNumber.getResidentNo().replace("-", "");
        List<Integer> numbers = residentNo.chars().mapToObj(Character::getNumericValue).toList();

        int lastNum = numbers.get(numbers.size() - 1); // 주민등록번호 마지막 값 저장
        int result = IntStream.range(0, numbers.size() - 1) // 마지막 항은 제외
                .map(i -> numbers.get(i) * (2 + i % 8)) // 2~9까지 반복
                .sum();

        int mod = (11 - result % 11) % 10;

        return mod == lastNum;
    }
}
