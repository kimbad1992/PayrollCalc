package com.leepay.payrollcalc.dto;

import com.leepay.payrollcalc.constant.EmpConstant;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import com.leepay.payrollcalc.exception.ErrorCode;
import org.springframework.validation.FieldError;

@Getter
@Setter
public class ApiResponse<T> {
    static final long serialVersionUID = 1L;

    public static final ApiResponse<?> EMPTY = new ApiResponse<>();

    // T:성공, F:실패
    private String result;
    private String code;
    private String msg;
    private HttpStatus status;

    @Nullable
    private T data;

    public ApiResponse()  {
        this.result = "T";
        this.msg = EmpConstant.SUCCESSFUL_RESPONSE_MSG;
        this.code = EmpConstant.STRING_ZERO;
        this.status = HttpStatus.OK;
    }

    public ApiResponse(@Nullable T data) {
        this.result = "T";
        this.msg = EmpConstant.SUCCESSFUL_RESPONSE_MSG;
        this.code = EmpConstant.STRING_ZERO;
        this.status = HttpStatus.OK;
        this.data = data;
    }

    public ResponseEntity build() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        if (this.status == HttpStatus.OK) {
            // 성공 시 조정
        }

        ResponseEntity responseEntity = new ResponseEntity<>(this, headers, status);
        return responseEntity;
    }
}
