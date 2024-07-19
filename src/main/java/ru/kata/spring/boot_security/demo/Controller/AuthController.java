package ru.kata.spring.boot_security.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.ValidatorUser;

import javax.validation.Valid;

@Controller
@RequestMapping("/auth")
public class AuthController {

    private final ValidatorUser validatorUser;
    private final UserService userService;

    @Autowired
    public AuthController(ValidatorUser validatorUser, UserService userService) {
        this.validatorUser = validatorUser;
        this.userService = userService;
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {

        return "auth/registration";
    }

    @PostMapping("registration")
    public String performRegistration(@ModelAttribute("user") @Valid User user, BindingResult bindingResult) {

        validatorUser.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "auth/registration";
        }
        userService.saveUser(user);
        return "redirect:/auth/login";
    }
}
