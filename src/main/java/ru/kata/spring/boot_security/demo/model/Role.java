package ru.kata.spring.boot_security.demo.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@ToString
@Entity(name = "t_roles")
public class Role implements GrantedAuthority {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String roles;


    public Role(Long id, String role) {

        this.id = id;
        this.roles = role;

    }


    public Role() {
    }


    @Override
    public String getAuthority() {
        return roles;
    }
}
