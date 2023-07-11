package com.mikovic.altma.controllers;

import com.mikovic.altma.exception.OAuth2AuthenticationProcessingException;
import com.mikovic.altma.exception.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.mail.MailException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

@EnableWebMvc
@ControllerAdvice
public class ControllerExceptionHandler  extends ResponseEntityExceptionHandler {


    @ExceptionHandler(OAuth2AuthenticationProcessingException.class)
    public ResponseEntity<?> resourceNotFoundException(OAuth2AuthenticationProcessingException ex, WebRequest request) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Calendar cal = Calendar.getInstance();
        String message = ex.getMessage();


        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MailException.class)
    public ResponseEntity<?> mailException(MailException  ex, Model model, WebRequest request){
        String message = ex.getMessage();
        model.addAttribute("message", message);

        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<?> interruptedException(InterruptedException  ex, Model model, WebRequest request){
        String message = ex.getMessage();
        model.addAttribute("message", message);

        return new ResponseEntity<>(message, HttpStatus.UNAUTHORIZED);
    }
}
