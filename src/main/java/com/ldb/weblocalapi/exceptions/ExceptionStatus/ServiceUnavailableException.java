package com.ldb.weblocalapi.exceptions.ExceptionStatus;

import com.ldb.weblocalapi.exceptions.DetailMessage.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
public class ServiceUnavailableException extends RuntimeException {

    private ErrorInfo errorInfo;

    public ServiceUnavailableException(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public ErrorInfo getError() {
        return errorInfo;
    }
}
