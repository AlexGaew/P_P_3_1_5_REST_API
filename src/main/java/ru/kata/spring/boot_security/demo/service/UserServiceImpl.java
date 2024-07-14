package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repositories.UserRepository;

import javax.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService, UserDetailsService {


    private final UserRepository userRepository;


    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findUserByUsername(String username) {
        return userRepository.findByName(username).orElseThrow();
    }

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByName(username);
        User user = optionalUser.orElseThrow();
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(), user.getAuthorities());
    }


    @Override
    public User findUserById(long id) {
        Optional<User> user = userRepository.findById(id);
        return user.orElse(new User());
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }


    @Transactional
    @Override
    public void saveUser(User user) {
//        User userFormDb = userRepository.findByName(user.getUsername()).orElseThrow();
        user.setRoles(Collections.singleton(new Role(2L, "ROLE_USER")));
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        userRepository.save(user);

    }


    @Transactional
    @Override
    public void updateUser(User user, long id) {
        if (userRepository.existsById(id)) {
            user.setId(id);
            userRepository.save(user);
        } else throw new EntityNotFoundException("User with id " + id + " not found");

    }

    @Transactional
    @Override
    public boolean deleteUser(long userId) {
        if (userRepository.findById(userId).isPresent()) {
            userRepository.deleteById(userId);
            return true;
        }
        return false;
    }


}
