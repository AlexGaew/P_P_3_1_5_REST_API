package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.exeption.MyException;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.UserRepository;
import ru.kata.spring.boot_security.demo.util.Constant.MyCodeStatus;

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
                new EntityNotFoundException(String.format("User is email: %s not found", email)));
    }


    @Override
    public User findUserById(int id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElseThrow(() -> MyException.builder()
                .code(MyCodeStatus.REQUEST_VALIDATION_ERROR)
                .message(String.format("User is id: %s not found", id))
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Transactional
    @Override
    public void saveUser(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw MyException.builder()
                    .code(MyCodeStatus.EMAIL_ALREADY_EXIST)
                    .message(String.format("User is eMail: %s already exist", user.getEmail()))
                    .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build();
        }

        HashSet<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {
            roles.add(roleService.findRoleById(role.getId()));
        }
        user.setRoles(roles);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);

    }


    @Transactional
    @Override
    public void updateUser(User user) {

        HashSet<Role> roles = new HashSet<>();
        for (Role role : user.getRoles()) {

            roles.add(roleService.findRoleById(role.getId()));
        }
        user.setRoles(roles);
        if (userRepository.existsById(user.getId())) {
            user.setId(user.getId());

            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } else throw MyException.builder()
                .code(MyCodeStatus.REQUEST_VALIDATION_ERROR)
                .message(String.format("User is id: %s not found", user.getId()))
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();

    }

    @Transactional
    @Override
    public boolean deleteUser(int userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        } else throw MyException.builder()
                .code(MyCodeStatus.REQUEST_VALIDATION_ERROR)
                .message(String.format("User is id: %s not found", userId))
                .httpStatus(HttpStatus.INTERNAL_SERVER_ERROR)
                .build();
    }

}
