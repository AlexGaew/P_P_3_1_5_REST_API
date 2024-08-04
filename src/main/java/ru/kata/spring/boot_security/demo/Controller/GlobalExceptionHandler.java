package ru.kata.spring.boot_security.demo.Controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import ru.kata.spring.boot_security.demo.error.Error;
import ru.kata.spring.boot_security.demo.exeption.MyException;

@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(MyException.class)
    public ResponseEntity<Error> myGlobalException(MyException e) {
        log.error("internal server error: {}", e.toString());
        return new ResponseEntity<>(Error.builder()
                .message(e.getMessage())
                .code(e.getCode())
                .build(),
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
