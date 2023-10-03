package com.leepay.payrollcalc.dto;

import lombok.Data;

import lombok.Data;
import java.util.Date;

@Data
public class Employee {
    private String affiliation;
    private String name;
    private String workLocation;
    private String position;
    private String phone;
    private String rate;
    private Date joinDate;
    private Date safetyCertDate;
    private String address;
    private String emergencyContact;
    private String residentNo;
    private String education;
    private Integer careerMonths;
    private String bankName;
    private String accountNo;
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
    private Boolean familyRegisterCopy;
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
