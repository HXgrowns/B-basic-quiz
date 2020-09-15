package com.thoughtworks.gtb.basicdesign.exception;

import com.thoughtworks.gtb.basicdesign.response.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<Result> handleBusinessException(BusinessException e) {
        return ResponseEntity.badRequest().body(Result.errorBusiness(e.getExceptionEnum()));
    }
}
