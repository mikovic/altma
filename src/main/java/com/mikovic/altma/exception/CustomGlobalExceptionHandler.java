package com.mikovic.altma.exception;

import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

@ControllerAdvice
public class CustomGlobalExceptionHandler {

    // Let Spring BasicErrorController handle the exception, we just override the status code
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public String  handleMethodException(Exception ex, WebRequest request) throws Exception {

        return "redirect:/";
    }

}
