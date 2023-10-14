package com.leepay.payrollcalc.service;

import com.leepay.payrollcalc.constant.EmpConstant;
import com.leepay.payrollcalc.dto.Employee;
import com.leepay.payrollcalc.exception.CommonException;
import com.leepay.payrollcalc.exception.ErrorCode;
import com.leepay.payrollcalc.mapper.EmployeeMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
public class EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    public List<Employee> parsingExcel(MultipartFile file) {
        try {
            ZipSecureFile.setMinInflateRatio(0.005); // Zip Bomb 방지용 압축비
            List<Employee> empList = new ArrayList<Employee>();
            try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
                Sheet sheet = workbook.getSheetAt(0); // 0번 시트
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) continue; // 첫번째 행은 버림
                    if (row.getCell(0) == null) break; // 순서 번호가 없는 행부터는 중단

                    Employee emp = new Employee();
                    // 0번 인덱스는 순서이므로 버림
                    emp.setAffiliation(getCellExpectString(row, 1)); // 소속
                    emp.setName(getCellExpectString(row, 2)); // 이름
                    emp.setWorkLocation(getCellExpectString(row, 3)); // 근무지
                    emp.setPosition(getCellExpectString(row, 4)); // 직급
                    emp.setPhone(getCellExpectString(row, 5)); // 휴대폰
                    emp.setJoinDate(getCellExpectDate(row, 6)); // 입사일
                    emp.setSafetyCertDate(getCellExpectDate(row, 7)); // 기초안전이수교육 날짜
//                    emp.setAddress(row.getCell(8).toString()); // 주소(간이)
                    emp.setEmergencyContact(getCellExpectString(row, 9)); // 비상연락망
                    emp.setResidentNo(getCellExpectString(row, 10)); // 주민번호
                    emp.setEducation(getCellExpectString(row, 11)); // 학력
                    emp.setCareerMonths(getCellExpectNumeric(row, 12)); // 경력(개월)
                    emp.setRate(getCellExpectString(row, 13)); // 급수
                    emp.setBankName(getCellExpectString(row, 14)); // 은행명
                    emp.setAccountNo(getCellExpectString(row, 15)); // 계좌번호
                    emp.setOtherAccountNo(getCellExpectString(row, 16)); // 타인계좌
                    emp.setAddress(getCellExpectString(row, 17)); // 주소3(풀주소)
                    emp.setDormitoryAddress(getCellExpectString(row, 18)); // 숙소
                    emp.setEmail(getCellExpectString(row, 19)); // 이메일
                    emp.setReferrer(getCellExpectString(row, 20)); // 추천인
                    emp.setFirstContractDate(getCellExpectDate(row, 21)); // 최초계약일
                    emp.setRenewalContractDate(getCellExpectDate(row, 22)); // 갱신계약일
                    emp.setEndDateExpected(getCellExpectDate(row, 23)); // 종료예정일
                    emp.setResignationDate(getCellExpectDate(row, 24)); // 퇴사일
                    emp.setLastWorkDate(getCellExpectDate(row, 25)); // 마지막 실근무일
//                    emp.setLastWorkDate(row.getCell(24).getDateCellValue()); // 퇴사사유
                    emp.setHealthCheck(Objects.equals(getCellExpectString(row, 27), EmpConstant.CELL_OKAY)); // 건강검진
                    emp.setFamilyRegisterCopy(Objects.equals(getCellExpectString(row, 28), EmpConstant.CELL_OKAY)); // 등본
                    emp.setBankbookCopy(Objects.equals(getCellExpectString(row, 29), EmpConstant.CELL_OKAY)); // 통장사본
                    emp.setEmploymentContract(Objects.equals(getCellExpectString(row, 30), EmpConstant.CELL_OKAY)); // 근로계약서
                    emp.setMealCard(Objects.equals(getCellExpectString(row, 31), EmpConstant.CELL_OKAY)); // 식비
                    emp.setReferralBonus(Objects.equals(getCellExpectString(row, 32), EmpConstant.CELL_OKAY)); // 추천수당
                    emp.setReturnStatus(Objects.equals(getCellExpectString(row, 33), EmpConstant.CELL_OKAY)); // 퇴사자 반납여부
                    emp.setPayGrade(getCellExpectString(row, 34)); // 호봉
                    emp.setHourlyWage(getCellExpectNumeric(row, 35)); // 시급
                    emp.setSalary(getCellExpectNumeric(row, 36)); // 급여
                    emp.setOtHourlyWage(getCellExpectNumeric(row, 37)); // OT시급
                    empList.add(emp);
                }

                log.debug("emp? : {}", empList);
            }
            return empList;
        } catch (Exception e) {
            throw new CommonException(ErrorCode.EXCEL_PARSING_ERROR);
        }
    }

    private String getCellExpectString (Row row, int index) {
        // 1. Null check
        // 2. Type check
        Cell cell = row.getCell(index);
        if (cell != null && cell.getCellType() == CellType.STRING) {
            return cell.getStringCellValue();
        } else {
            return null;
        }
    }

    private Date getCellExpectDate(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell != null) {
            if (cell.getCellType() == CellType.NUMERIC) {
                return cell.getDateCellValue();
            }
        }
        return null;
    }
    
    private Integer getCellExpectNumeric(Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell != null) {
            if (cell.getCellType() == CellType.NUMERIC) {
                return ((Double) cell.getNumericCellValue()).intValue();
            }
        }
        return null;
    }

    @Transactional
    public int insertEmployeeListByExcel(List<Employee> empList) {
        employeeMapper.upsertPersonInfoByExcel(empList); // PERSON_INFO
//        employeeMapper.upsertWorkInfoByExcel(empList); // WORK_INFO
//        employeeMapper.upsertFinancialStatusByExcel(empList); // FINANCIAL_STATUS
//        employeeMapper.upsertEducationAndCareerByExcel(empList); // EDUCATION_AND_CAREER
//        employeeMapper.upsertDocumentStatusByExcel(empList); // DOCUMENT_STATUS
        return employeeMapper.insertEmployeeListByExcel(empList);
    }
}
