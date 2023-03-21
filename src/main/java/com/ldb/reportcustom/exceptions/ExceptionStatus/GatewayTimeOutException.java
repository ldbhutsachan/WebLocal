package com.ldb.reportcustom.exceptions.ExceptionStatus;

import com.ldb.reportcustom.exceptions.DetailMessage.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.GATEWAY_TIMEOUT)
public class GatewayTimeOutException extends RuntimeException {

    private ErrorInfo errorInfo;

    public GatewayTimeOutException(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public ErrorInfo getError() {
        return errorInfo;
    }
}
