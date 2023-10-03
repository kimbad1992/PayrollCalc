package com.leepay.payrollcalc.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class CommonException extends RuntimeException {

    private ErrorCode errorCode;

    public CommonException() { super(); }

    public CommonException(String message) { super(message); }

    public CommonException(Throwable e) { super(e); }

    public CommonException(String message, Throwable e) {
        super(message, e);
    }

    public CommonException(ErrorCode errorCode) {
        super(errorCode.getCode());
        this.errorCode = errorCode;
    }

    public CommonException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public CommonException(String message, Exception e, ErrorCode errorCode) {
        super(message, e);
        this.errorCode = errorCode;
    }

    public String getErrMsg() {
        return errorCode.getErrMsg();
    }

    public String getErrCode() {
        return errorCode.getCode();
    }

    public HttpStatus getStatus() { return errorCode.getStatus();
    }
}
