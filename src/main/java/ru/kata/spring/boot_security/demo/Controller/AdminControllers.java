package ru.kata.spring.boot_security.demo.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.ValidatorUser;

import javax.validation.Valid;
import java.util.Objects;

@Controller
@RequestMapping(value = "/admin")
public class AdminControllers {
    private final String REDIRECTADMIN = "redirect:/admin/";

    private final ValidatorUser validator;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminControllers(UserService userService, ValidatorUser validator, RoleService roleService) {
        this.userService = userService;
        this.validator = validator;
        this.roleService = roleService;
    }


    @GetMapping("/")
    public String startAdminPanel(@ModelAttribute("user") User user, Model model, Authentication authentication) {
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("rolesList", roleService.getAllRoles());
        User currentUser1 = (User) authentication.getPrincipal();
        model.addAttribute("currentUser", userService.findUserByEmail(currentUser1.getEmail()));
        return "admin/superNewAdminPanel";
    }

    @PatchMapping(value = "/update")
    public String update(@Valid @ModelAttribute("user") User user, @RequestParam("selectedRoles") int[] rolesId) {

        userService.updateUser(user, rolesId);
        return REDIRECTADMIN;
    }


    @PostMapping("/new")
    public String createUser(@Valid @ModelAttribute("user") User user, Model model,
                             @RequestParam("rolesSelected") int[] rolesId, BindingResult bindingResult) {


        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute("errorMessage", Objects.requireNonNull(bindingResult
                            .getFieldError())
                    .getDefaultMessage());
            return "error/error";
        }

        userService.saveUser(user, rolesId);
        return REDIRECTADMIN;
    }


    @DeleteMapping("/delete")
    public String deleteUser(@RequestParam(value = "id", defaultValue = "1000") int id) {
        userService.deleteUser(id);
        return REDIRECTADMIN;
    }


}
