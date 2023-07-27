/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.dishavarshney.trimmy.config;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import javax.servlet.ServletException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.status;

/**
 * @author Disha Varshney
 */
@ControllerAdvice
public class AppExceptionHandler {

    @ExceptionHandler(value = {ServletException.class})
    public ResponseEntity handleException(ServletException ex, WebRequest request) {
        return notFound().build();
    }

    @ExceptionHandler(value = {BadCredentialsException.class, UsernameNotFoundException.class})
    public ResponseEntity handleException(BadCredentialsException ex, WebRequest request) {
        return status(UNAUTHORIZED).body(ex.getMessage());
    }

}
