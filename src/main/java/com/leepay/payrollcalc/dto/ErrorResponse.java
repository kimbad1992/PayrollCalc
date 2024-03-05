package com.leepay.payrollcalc.dto;

import com.leepay.payrollcalc.exception.CommonException;
import com.leepay.payrollcalc.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private String code;
    private HttpStatus status;
    private List<Map<String, String>> errors;

    public ErrorResponse (Throwable e){
        if (e instanceof CommonException) {
            CommonException ex = (CommonException)e ;
            this.code = ex.getErrCode();
            this.message = ex.getErrMsg();
            this.status = ex.getStatus();
        } else if (e instanceof DataAccessException) {
            SQLException sqlException = (SQLException)e.getCause();
            String sqlState = sqlException.getSQLState();
            String value;
            switch (sqlState) {
                case "23505": value = "DUPLICATE_KEY"; break;
                default: value = "INTERNAL_SERVER_ERROR"; break;
            }
            this.code = ErrorCode.valueOf(value).getCode();
            this.message = ErrorCode.valueOf(value).getErrMsg();
            this.status = ErrorCode.valueOf(value).getStatus();
        } else if (e instanceof AuthenticationException) {
            // 사용자 인증 중 에러 발생 시
            this.code = "9999";
            this.message = e.getMessage();
            this.status = HttpStatus.INTERNAL_SERVER_ERROR;
        } else {
            CommonException ex = new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
            this.code = ex.getErrCode();
            this.message = ex.getErrMsg();
            this.status = ex.getStatus();
        }
    }

    public ErrorResponse (BindingResult e){
        this.status = HttpStatus.BAD_REQUEST;
        // 필드 명을 주어서 페이지에서 사용할 수 있게 함
        this.errors = getErrorFieldList(e);
    }

    public ResponseEntity build() {
        return new ResponseEntity<>(this, status);
    }

    private List<Map<String,String>> getErrorFieldList(BindingResult bindingResult) {
        List<Map<String,String>> errors = new ArrayList<>();
        for (FieldError error : bindingResult.getFieldErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            errorMap.put("field", error.getField());
            errorMap.put("defaultMessage", error.getDefaultMessage());
            errors.add(errorMap);
        }
        return errors;
    }
}
