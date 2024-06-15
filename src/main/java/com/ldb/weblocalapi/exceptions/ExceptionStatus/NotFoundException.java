package com.ldb.weblocalapi.exceptions.ExceptionStatus;

import com.ldb.weblocalapi.exceptions.DetailMessage.ErrorInfo;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {

    private ErrorInfo errorInfo;

    public NotFoundException(ErrorInfo errorInfo) {
        this.errorInfo = errorInfo;
    }

    public ErrorInfo getError() {
        return errorInfo;
    }
}
