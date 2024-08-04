package ru.kata.spring.boot_security.demo.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import ru.kata.spring.boot_security.demo.util.Constant.MyCodeStatus;



@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Error {
    private MyCodeStatus code;
    private  String message;

}
