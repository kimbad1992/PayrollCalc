package com.leepay.payrollcalc.controller;

import com.leepay.payrollcalc.dto.ApiResponse;
import com.leepay.payrollcalc.dto.Employee;
import com.leepay.payrollcalc.exception.ErrorCode;
import com.leepay.payrollcalc.service.EmployeeService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /* 사원 등록 페이지 */
    @RequestMapping("/register")
    public String employeeRegisterPage() {
        return "/employee/register";
    }

    /* 엑셀 일괄 등록 페이지 */
    @RequestMapping("/excel")
    public String employeeExcelPage() {
        return "/employee/excel";
    }

    /* 사원 조회 페이지 */
    @RequestMapping("/list")
    public String employeeListPage(Model model, HttpServletRequest request) {
        return "/employee/list";
    }

    /**
     * Excel 파일을 파싱하여 Employee 객체 리스트를 반환하는 메서드.
     *
     * @param file MultipartFile 형태의 엑셀 파일.
     * @return List<Employee> 엑셀 파일에 담긴 정보를 가진 Employee 객체의 리스트.
     * @throws ErrorCode.EXCEL_PARSING_ERROR 엑셀 파싱 중 에러가 발생한 경우.
     */
    @PostMapping("/excelUpload.do")
    @ResponseBody
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        List<Employee> empList = employeeService.parsingExcel(file);
//        return new ApiResponse(employeeService.insertEmployeeListByExcel(empList)).build();
        return new ApiResponse(null).build();
    }

}
