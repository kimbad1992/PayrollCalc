package com.leepay.payrollcalc.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {
    EXCEL_PARSING_ERROR("9001", "엑셀 파싱 중 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    INTERNAL_SERVER_ERROR("9999", "서버 에러가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR);

    private String errCode;
    private String errMsg;
    private HttpStatus status;

    private ErrorCode(String errCode, String errMsg, HttpStatus status) {
        this.errCode = errCode;
        this.errMsg = errMsg;
        this.status = status;
    }

    public String getCode() {
        return errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }
    public HttpStatus getStatus() {
        return status;
    }
}
