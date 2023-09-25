package com.leepay.payrollcalc.controller;

import com.leepay.payrollcalc.service.EmployeeService;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;


    @RequestMapping("/register")
    public String employeeRegisterPage() {
        return "/employee/register";
    }

    @RequestMapping("/excel")
    public String employeeExcelPage() {
        return "/employee/excel";
    }

    @RequestMapping("/list")
    public String employeeListPage() {
        return "/employee/list";
    }

    @PostMapping("/excelUpload.do")
    @ResponseBody
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) {
        try {
            ZipSecureFile.setMinInflateRatio(0.005); // 비율 설정. 이 값을 조정할 수 있습니다.
            try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
                Sheet sheet = workbook.getSheetAt(0);
                for (Row row : sheet) {
                    Cell nameCell = row.getCell(0);
                    Cell affiliationCell = row.getCell(1);
                    String name = nameCell.getStringCellValue();
                    String affiliation = affiliationCell.getStringCellValue();
                    // 데이터베이스에 저장하는 로직
                }
            }
            return new ResponseEntity<>("업로드 성공", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("업로드 실패", HttpStatus.BAD_REQUEST);
        }
    }

}
