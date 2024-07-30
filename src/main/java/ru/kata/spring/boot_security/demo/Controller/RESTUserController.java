package ru.kata.spring.boot_security.demo.Controller;


import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

@RestController
@RequestMapping("/api")
public class RESTUserController {


    @GetMapping("/users/name")
    public User getUserInfo(@AuthenticationPrincipal User user) {
        return user;

    }

}
