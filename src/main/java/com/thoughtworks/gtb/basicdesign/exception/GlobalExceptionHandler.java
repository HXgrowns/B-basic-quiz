package com.thoughtworks.gtb.basicdesign.exception;

import com.thoughtworks.gtb.basicdesign.response.Result;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = BusinessException.class)
    public ResponseEntity<Result> handleBusinessException(BusinessException e) {
        return ResponseEntity.badRequest().body(Result.errorBusiness(e.getExceptionEnum()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> handleMethodArgumentNotValidExceptionException(MethodArgumentNotValidException e) {
        BindingResult bindingResult = e.getBindingResult();
        List<Result> results = new ArrayList<>();
        if (bindingResult.hasFieldErrors()) {
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                results.add(Result.errorSystem(fieldError.getDefaultMessage()));
            }
        }

        return ResponseEntity.badRequest().body(results);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> handleConstraintViolationException(ConstraintViolationException e) {
        String message = "";
        for (ConstraintViolation<?> constraintViolation : e.getConstraintViolations()) {
            message = constraintViolation.getMessage();
        }
        return ResponseEntity.badRequest().body(Result.errorSystem(message));
    }
}
