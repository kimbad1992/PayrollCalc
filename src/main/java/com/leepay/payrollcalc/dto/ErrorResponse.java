package com.leepay.payrollcalc.dto;

import com.leepay.payrollcalc.exception.CommonException;
import com.leepay.payrollcalc.exception.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.sql.SQLException;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {
    private String message;
    private String code;
    private HttpStatus status;

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
        } else {
            CommonException ex = new CommonException(ErrorCode.INTERNAL_SERVER_ERROR);
            this.code = ex.getErrCode();
            this.message = ex.getErrMsg();
            this.status = ex.getStatus();
        }
    }

    public ResponseEntity build() {
        ResponseEntity responseEntity = new ResponseEntity<>(this, status);
        return responseEntity;
    }
}
