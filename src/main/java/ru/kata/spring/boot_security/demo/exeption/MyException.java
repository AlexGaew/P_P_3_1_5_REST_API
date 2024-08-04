package ru.kata.spring.boot_security.demo.exeption;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import ru.kata.spring.boot_security.demo.util.Constant.MyCodeStatus;


@Data
@Builder
public class MyException extends RuntimeException {
    private final MyCodeStatus code;
    private final String message;
    private final HttpStatus httpStatus;
}
