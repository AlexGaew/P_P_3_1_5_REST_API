package ru.kata.spring.boot_security.demo.service;

import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @Override
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role findRoleById(long id) {
        return roleRepository.getById(id);
    }

    @Override
    @Transactional
    public void saveRole(Role role) {

    }

    @Override
    @Transactional
    public void updateUser(Role role, long id) {

    }

    @Override
    @Transactional
    public void deleteRole(long id) {
        roleRepository.getById(id);
    }
}
