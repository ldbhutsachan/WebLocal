package com.ldb.weblocalapi.exceptions.ExceptionStatus;

import com.ldb.weblocalapi.exceptions.DetailMessage.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class InternalServerError extends RuntimeException {

    private ErrorInfo errorInfo;

    public InternalServerError(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public ErrorInfo getError() {
        return errorInfo;
    }
}
