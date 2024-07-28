package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {


    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;


    @Autowired
    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(() ->
                new EntityNotFoundException(String.format("User is %s not found", email)));
    }


    @Override
    public User findUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> new EntityNotFoundException(String.format("User is %s not found", id)));
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Transactional
    @Override
    public void saveUser(User user, int[] rolesId) {

        HashSet<Role> roles = new HashSet<>();
        for (int roleId : rolesId) {
            roles.add(roleService.findRoleById(roleId));
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }


    @Transactional
    @Override
    public void updateUser(User user, int[] roleId) {

        HashSet<Role> roles = new HashSet<>();
        for (int role : roleId) {
            roles.add(roleService.findRoleById(role));
        }


        if (userRepository.existsById(user.getId())) {
            user.setId(user.getId());
            user.setRoles(roles);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } else throw new EntityNotFoundException(String.format("User is %s not found", user.getId()));

    }

    @Transactional
    @Override
    public boolean deleteUser(int userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }


}
