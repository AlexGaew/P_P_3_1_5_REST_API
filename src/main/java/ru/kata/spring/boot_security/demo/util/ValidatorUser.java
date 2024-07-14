package ru.kata.spring.boot_security.demo.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;

import java.security.Principal;

@Component
public class ValidatorUser implements Validator
{
    private final UserServiceImpl userService;

    @Autowired
    public ValidatorUser(UserServiceImpl userService){
        this.userService = userService;
    }



    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        try {
            userService.findUserByUsername(user.getName());
        } catch (Exception e) {
            return;
        }

        errors.rejectValue("name", null, "Пользовтаель с таким именем уже существует");

    }
}
