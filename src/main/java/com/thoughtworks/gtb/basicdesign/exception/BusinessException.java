package com.thoughtworks.gtb.basicdesign.exception;

public class BusinessException extends RuntimeException {
    private ExceptionEnum exceptionEnum;

    public BusinessException(ExceptionEnum exceptionEnum) {
        this.exceptionEnum = exceptionEnum;
    }

    public ExceptionEnum getExceptionEnum() {
        return exceptionEnum;
    }
}
