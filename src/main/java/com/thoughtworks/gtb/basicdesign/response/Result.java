package com.thoughtworks.gtb.basicdesign.response;

import com.thoughtworks.gtb.basicdesign.exception.BusinessException;
import com.thoughtworks.gtb.basicdesign.exception.ExceptionEnum;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
public class Result {
    private int code;
    private String message;
    private Date timestamp;
    private long status;

    public static Result errorBusiness(ExceptionEnum e) {
        return Result.builder()
                .code(e.getCode())
                .message(e.getMessage())
                .timestamp(new Date())
                // GTB: - 全都是 400？
                .status(400)
                .build();
    }
}
