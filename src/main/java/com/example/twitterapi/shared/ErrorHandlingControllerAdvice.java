package com.example.twitterapi.shared;

import com.example.twitterapi.dto.Message;
import com.example.twitterapi.dto.ValidationErrorResponse;
import com.example.twitterapi.dto.Violation;
import org.apache.tomcat.util.http.fileupload.impl.SizeLimitExceededException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

    @ControllerAdvice
    public class ErrorHandlingControllerAdvice {
        @ExceptionHandler(ConstraintViolationException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ResponseBody
        ValidationErrorResponse onConstraintValidationException(
                ConstraintViolationException e) {
            ValidationErrorResponse error = new ValidationErrorResponse();
            for (ConstraintViolation violation : e.getConstraintViolations()) {
                error.getViolations().add(
                        new Violation(violation.getPropertyPath().toString(), violation.getMessage()));
            }
            return error;
        }

        @ExceptionHandler(MethodArgumentNotValidException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ResponseBody
        ValidationErrorResponse onMethodArgumentNotValidException(
                MethodArgumentNotValidException e) {
            ValidationErrorResponse error = new ValidationErrorResponse();
            for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
                error.getViolations().add(
                        new Violation(fieldError.getField(), fieldError.getDefaultMessage()));
            }
            return error;
        }

        @ExceptionHandler(SizeLimitExceededException.class)
        @ResponseStatus(HttpStatus.BAD_REQUEST)
        @ResponseBody
        Message onSizeLimitExceededException(SizeLimitExceededException e) {
            return new Message("file is too big! the maximum size is 8mb");
        }

    }