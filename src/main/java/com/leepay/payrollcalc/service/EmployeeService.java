package com.leepay.payrollcalc.service;

import com.leepay.payrollcalc.constant.EmpConstant;
import com.leepay.payrollcalc.dto.Employee;
import com.leepay.payrollcalc.exception.CommonException;
import com.leepay.payrollcalc.exception.ErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class EmployeeService {
    public List<Employee> parsingExcel(MultipartFile file) {
        try {
            ZipSecureFile.setMinInflateRatio(0.005); // 비율 설정. 이 값을 조정할 수 있습니다.
            List<Employee> empList = new ArrayList<Employee>();
            try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
                Sheet sheet = workbook.getSheetAt(0); // 0번 시트
                for (Row row : sheet) {
                    if (row.getRowNum() == 0) continue; // 첫번째 행은 버림
                    if (row.getCell(0) == null) break; // 순서 번호가 없는 행부터는 중단

                    Employee emp = new Employee();
                    // 0번 인덱스는 순서이므로 버림
                    emp.setAffiliation(row.getCell(1).getStringCellValue()); // 소속
                    emp.setName(row.getCell(2).getStringCellValue()); // 이름
                    emp.setWorkLocation(row.getCell(3).getStringCellValue()); // 근무지
                    emp.setPosition(row.getCell(4).getStringCellValue()); // 직급
                    emp.setPhone(row.getCell(5).getStringCellValue()); // 휴대폰
                    if (row.getCell(6).getCellType() == CellType.NUMERIC) emp.setJoinDate(row.getCell(6).getDateCellValue()); // 입사일
                    if (row.getCell(7).getCellType() == CellType.NUMERIC) emp.setSafetyCertDate(row.getCell(7).getDateCellValue()); // 기초안전이수교육 날짜
//                    emp.setAddress(row.getCell(8).toString()); // 주소(간이)
                    emp.setEmergencyContact(row.getCell(9).getStringCellValue()); // 비상연락망
                    emp.setResidentNo(row.getCell(10).getStringCellValue()); // 주민번호
                    emp.setEducation(row.getCell(11).getStringCellValue()); // 학력
                    if (row.getCell(12).getCellType() == CellType.NUMERIC) emp.setCareerMonths((int) row.getCell(12).getNumericCellValue()); // 경력(개월)
                    emp.setRate(row.getCell(13).toString()); // 급수
                    emp.setBankName(row.getCell(14).getStringCellValue()); // 은행명
                    emp.setAccountNo(row.getCell(15).getStringCellValue()); // 계좌번호
                    emp.setOtherAccountNo(row.getCell(16).getStringCellValue()); // 타인계좌
                    emp.setAddress(row.getCell(17).getStringCellValue()); // 주소3(풀주소)
                    emp.setDormitoryAddress(row.getCell(18).getStringCellValue()); // 숙소
                    emp.setEmail(row.getCell(19).getStringCellValue()); // 이메일
                    emp.setReferrer(row.getCell(20).getStringCellValue()); // 추천인
                    if (row.getCell(21).getCellType() == CellType.NUMERIC) emp.setFirstContractDate(row.getCell(21).getDateCellValue()); // 최초계약일
                    if (row.getCell(22).getCellType() == CellType.NUMERIC) emp.setRenewalContractDate(row.getCell(22).getDateCellValue()); // 갱신계약일
                    if (row.getCell(23).getCellType() == CellType.NUMERIC) emp.setEndDateExpected(row.getCell(23).getDateCellValue()); // 종료예정일
                    if (row.getCell(24).getCellType() == CellType.NUMERIC) emp.setResignationDate(row.getCell(24).getDateCellValue()); // 퇴사일
                    if (row.getCell(25).getCellType() == CellType.NUMERIC) emp.setLastWorkDate(row.getCell(25).getDateCellValue()); // 마지막 실근무일
//                    emp.setLastWorkDate(row.getCell(24).getDateCellValue()); // 퇴사사유
                    emp.setHealthCheck(row.getCell(27).getStringCellValue().equals(EmpConstant.OKAY)); // 건강검진
                    emp.setFamilyRegisterCopy(row.getCell(28).getStringCellValue().equals(EmpConstant.OKAY)); // 등본
                    emp.setBankbookCopy(row.getCell(29).getStringCellValue().equals(EmpConstant.OKAY)); // 통장사본
                    emp.setEmploymentContract(row.getCell(30).getStringCellValue().equals(EmpConstant.OKAY)); // 근로계약서
                    emp.setMealCard(row.getCell(31).getStringCellValue().equals(EmpConstant.OKAY)); // 식비
                    emp.setReferralBonus(row.getCell(32).getStringCellValue().equals(EmpConstant.OKAY)); // 추천수당
                    emp.setReturnStatus(row.getCell(33).getStringCellValue().equals(EmpConstant.OKAY)); // 퇴사자 반납여부
                    emp.setPayGrade(row.getCell(34).getStringCellValue()); // 호봉
                    if (row.getCell(35).getCellType() == CellType.NUMERIC) emp.setHourlyWage((int) row.getCell(35).getNumericCellValue()); // 시급
                    if (row.getCell(36).getCellType() == CellType.NUMERIC) emp.setSalary((int) row.getCell(36).getNumericCellValue()); // 급여
                    if (row.getCell(37).getCellType() == CellType.NUMERIC) emp.setOtHourlyWage((int) row.getCell(37).getNumericCellValue()); // OT시급

                    empList.add(emp);
                }

                log.debug("emp? : {}", empList);
            }
            return empList;
        } catch (Exception e) {
            throw new CommonException(ErrorCode.EXCEL_PARSING_ERROR);
        }
    }

    private Object getCellAsTypeCheck (Row row, int index) {
        Cell cell = row.getCell(index);
        if (cell != null && cell.getCellType() == CellType.NUMERIC) {
            return cell.getDateCellValue();
        }
        return null;
    }

}
