package ru.kata.spring.boot_security.demo.Controller;


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
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.ValidatorUser;

import javax.validation.Valid;
import java.util.HashSet;

@Controller
@RequestMapping(value = "/admin")
public class AdminControllers {
    private final  String REDIRECTINFO = "redirect:admin/info/";

    private final ValidatorUser validator;
    private final UserService userService;
    private final RoleService roleService;

    @Autowired
    public AdminControllers(UserService userService, ValidatorUser validator,
                            RoleService roleService) {
        this.userService = userService;
        this.validator = validator;
        this.roleService = roleService;
    }


    @GetMapping("/info")
    public String printInfoAllUser(Model model) {
        model.addAttribute("userList", userService.getAllUsers());
        model.addAttribute("rolesList", roleService.getAllRoles());

        return "admin/userInfo";
    }


    @GetMapping("/user/{id}")
    public String printInfoUserById(@PathVariable("id") long id, Model model) {
        model.addAttribute("userById", userService.findUserById(id));
        Role role = userService.findUserById(id).getRoles().stream().findFirst().orElse(new Role(1L, "null"));
        if (role.getRoles().contains("ROLE_ADMIN")) {
            model.addAttribute("user", false);
        } else {
            model.addAttribute("user", true);
        }
        return "admin/userInfoById";
    }

    @GetMapping("/new")
    public String newUser(@ModelAttribute("user") User user,
                          Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/newUser";
    }

    @PostMapping()
    public String createUser(@Valid @ModelAttribute("user") User user,
                             @RequestParam("rolesSelected") long[] rolesId,
                             BindingResult bindingResult) {

        HashSet<Role> roles = new HashSet<>();
        for (long roleId : rolesId) {
            roles.add(roleService.findRoleById(roleId));
        }

        validator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/newUser";
        }
        user.setRoles(roles);
        userService.saveUser(user);
        return REDIRECTINFO;
    }

    @GetMapping("/user/{id}/edit")
    public String editUser(@PathVariable("id") long id, Model model) {
        model.addAttribute("user", userService.findUserById(id));
        model.addAttribute("roles", roleService.getAllRoles());
        return "admin/userEdit";
    }

    @PatchMapping("/user/{id}")
    public String update(@Valid @ModelAttribute("user") User user, @RequestParam("rolesSelected") long[] rolesId,
                         BindingResult bindingResult) {

        HashSet<Role> roles = new HashSet<>();
        for (long roleId : rolesId) {
            roles.add(roleService.findRoleById(roleId));
        }

        if (bindingResult.hasErrors()) {
            return "admin/userEdit";
        }
        user.setRoles(roles);
        userService.updateUser(user, user.getId());
        return "redirect:/admin/info";
    }

    @DeleteMapping()
    public String deleteUser(@RequestParam Long id) {
        userService.deleteUser(id);
        return REDIRECTINFO;
    }


}
