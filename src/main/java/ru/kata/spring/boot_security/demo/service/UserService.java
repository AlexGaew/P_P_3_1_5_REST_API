package ru.kata.spring.boot_security.demo.service;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();

    User findUserByEmail(String email);

    User findUserById(int id);


    void saveUser(User user);


    void updateUser(User user, int id);


    boolean deleteUser(int id);
}
