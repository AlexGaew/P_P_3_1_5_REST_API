package ru.kata.spring.boot_security.demo.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;


@Controller
@RequestMapping(value = "/user")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/info")
    public String printInfoUserById(Principal principal, Model model) {
        User user = userService.findUserByUsername(principal.getName());
        model.addAttribute("userById", user);
        System.out.println(user);
        Role role = userService.findUserByUsername(principal.getName()).getRoles().stream().findFirst().orElseThrow();
        if (role.getRoles().contains("ROLE_ADMIN")) {
            model.addAttribute("user", false);
        } else {
            model.addAttribute("user", true);
        }
        return "user/userInfoById";
    }
}
