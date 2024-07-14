package ru.kata.spring.boot_security.demo.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import javax.validation.Valid;
import java.security.Principal;

@Controller
@RequestMapping(value = "/admin")
public class AdminControllers {
    private final String REDIRECT_INFO = "redirect:/info";


    private final UserService userService;

    @Autowired
    public AdminControllers(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/info")
    public String printInfoAllUser(Model model, Principal principal) {
        model.addAttribute("infoAllUsers", userService.getAllUsers());
        Role role = userService.findUserByUsername(principal.getName()).getRoles().stream().findFirst().orElseThrow();
        if (role.getRoles().contains("ROLE_ADMIN")) {
            model.addAttribute("user", false);
            model.addAttribute("role", role.getRoles());
        } else {
            model.addAttribute("user", true);
        }
        return "admin/userInfo";
    }

    @GetMapping("/user/{id}")
    public String printInfoUserById(@PathVariable("id") long id, Model model) {
        model.addAttribute("userById", userService.findUserById(id));
        Role role = userService.findUserById(id).getRoles().stream().findFirst().orElseThrow();
        if (role.getRoles().contains("ROLE_ADMIN")) {
            model.addAttribute("user", false);
        } else {
            model.addAttribute("user", true);
        }
        return "admin/userInfoById";
    }

    @GetMapping("/new")
    public String newUser(Model model, Principal principal) {
        model.addAttribute("user", userService.findUserByUsername(principal.getName()));
        return "admin/newUser";
    }

    @PostMapping()
    public String createUser(@Valid @ModelAttribute("user") User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "admin/newUser";
        }
        model.addAttribute("message", "User saved successfully");
        userService.saveUser(user);
        return REDIRECT_INFO;
    }

    @GetMapping("/user/{id}/edit")
    public String editUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        return "admin/userEdit";
    }

    @PatchMapping("/user/{id}")
    public String update(@Valid @ModelAttribute("user") User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/userEdit";
        }
        userService.updateUser(user, user.getId());
        return "redirect:/admin/user/" + user.getId();
    }

    @DeleteMapping("/info")
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return REDIRECT_INFO;
    }


}
