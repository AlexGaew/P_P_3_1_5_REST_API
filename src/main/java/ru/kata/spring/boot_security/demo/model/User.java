package ru.kata.spring.boot_security.demo.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Setter
@Getter
@Entity
@Table(name = "t_users")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 15, message = "Name should be between 2 and 15 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only letters")
    private String name;

    @Column()
    @NotEmpty(message = "surName should not be empty")
    @Size(min = 2, max = 15, message = "surName should be between 2 and 15 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "surName must contain only letters")
    private String surName;

    @Column()
    @Min(value = 1, message = "Age  should not be greater 1")
    private int age;

    @Column
    private String password;


    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Collection<Role> roles;


    public User(String name, String surName, int age, String password) {
        this.name = name;
        this.surName = surName;
        this.age = age;
        this.password = password;
    }

    public User() {
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream()
                .map(role -> new SimpleGrantedAuthority(role.getRoles()))
                .collect(Collectors.toList());

    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", surName='" + surName + '\'' +
                ", age=" + age +
                ", roles=" + roles +
                '}';
    }
}
