package com.thoughtworks.gtb.basicdesign.exception;

public enum ExceptionEnum {
    SUCCESS(0, "success"),
    UNKNOWN_ERROR(1, "unknown_error"),
    USER_NOT_FOUND(2, "user not found"),
    ;

    private int code;
    private String message;

    ExceptionEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
