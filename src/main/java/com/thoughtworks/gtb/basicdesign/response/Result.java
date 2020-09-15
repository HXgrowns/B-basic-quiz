package com.thoughtworks.gtb.basicdesign.response;

import com.thoughtworks.gtb.basicdesign.exception.BusinessException;
import com.thoughtworks.gtb.basicdesign.exception.ExceptionEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Result {
    private int code;
    private String message;

    public static Result errorSystem() {
        return Result.builder().code(ExceptionEnum.UNKNOWN_ERROR.getCode())
                .message(ExceptionEnum.UNKNOWN_ERROR.getMessage()).build();
    }

    public static Result errorBusiness(ExceptionEnum e) {
        return Result.builder().code(e.getCode()).message(e.getMessage()).build();
    }
}
