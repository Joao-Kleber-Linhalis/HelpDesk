package com.jk.helpdesk.controllers.exceptions;

import com.jk.helpdesk.services.exceptions.ObjectNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.time.LocalDateTime;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<StandardError> ObjectNotFoundException(ObjectNotFoundException ex, HttpServletRequest request){
        StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.NOT_FOUND.value(),"Object Not Found", ex.getMessage(),request.getRequestURI());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<StandardError> IllegalArgumentException(IllegalArgumentException ex, HttpServletRequest request){
        StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),"Illegal Argument", ex.getMessage(),request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<StandardError> DataIntegrityViolationException(DataIntegrityViolationException ex, HttpServletRequest request){
        StandardError error = new StandardError(System.currentTimeMillis(), HttpStatus.BAD_REQUEST.value(),"Violação de dados", ex.getMessage(),request.getRequestURI());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<StandardError> validationErros(MethodArgumentNotValidException ex, HttpServletRequest request){
        ValidationError errors = new ValidationError(System.currentTimeMillis(),HttpStatus.BAD_REQUEST.value(), "Validation Error","Erro na validação dos campos",request.getRequestURI());
        for(FieldError x : ex.getBindingResult().getFieldErrors()){
            errors.addError(x.getField(),x.getDefaultMessage());
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
