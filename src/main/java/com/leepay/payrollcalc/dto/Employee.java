package com.leepay.payrollcalc.dto;

import com.leepay.payrollcalc.validation.ValidPhoneNumber;
import com.leepay.payrollcalc.validation.ValidResidentNo;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import lombok.Data;
import java.util.Date;

@Data
public class Employee {
    private Integer personId;

    @NotBlank(message = "소속명은 필수 입력 사항입니다.")
    private String affiliation;

    @NotBlank(message = "이름은 필수 입력 사항입니다.")
    private String name;

    @NotBlank(message = "근무지는 필수 입력 사항입니다.")
    private String workLocation;
    @NotBlank(message = "직급은 필수 입력 사항입니다.")
    private String position;
    @ValidPhoneNumber(message = "잘못된 형식의 전화번호입니다.")
    private PhoneNumber phone = new PhoneNumber(); // NullValueInNestedPathException 방지
    private String rate;
    private Date joinDate;
    private Date safetyCertDate;
    private String address;
    private String emergencyContact;
    @ValidResidentNo(message = "잘못된 형식의 주민번호입니다.")
    private ResidentNumber residentNo = new ResidentNumber(); // NullValueInNestedPathException 방지
    @NotBlank(message = "학력은 필수 입력 사항입니다.")
    private String education;
    @NotNull(message = "경력(개월)은 필수 입력 사항입니다.")
    @Min(value = 0, message = "경력은 0이상의 값을 입력해야 합니다.")
    private Integer careerMonths;
    @NotBlank(message = "은행명은 필수 입력 사항입니다.")
    private String bank;
    @NotBlank(message = "계좌번호는 필수 입력 사항입니다.")
    private String accountNumber;
    private String otherAccountNo;
    private String email;
    private String dormitoryAddress;
    private String referrer;
    private Date firstContractDate;
    private Date renewalContractDate;
    private Date endDateExpected;
    private Date resignationDate;
    private Date lastWorkDate;
    private Boolean healthCheck;
    private Boolean familyRegister;
    private Boolean bankbookCopy;
    private Boolean employmentContract;
    private Boolean mealCard;
    private Boolean referralBonus;
    private Boolean returnStatus;
    private String payGrade;
    private Integer hourlyWage;
    private Integer salary;
    private Integer otHourlyWage;
    private Boolean businessIncomeConvert;
    private Boolean employmentInsuranceExempt;
    private Boolean resignationType;
}
