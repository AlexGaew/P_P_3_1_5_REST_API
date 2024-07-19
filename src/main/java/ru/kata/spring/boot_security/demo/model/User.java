package ru.kata.spring.boot_security.demo.model;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.UniqueElements;
import org.springframework.security.core.GrantedAuthority;
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
import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

@Setter
@Getter
@ToString
@Entity
@Table(name = "t_users")
public class User implements UserDetails {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(unique = true)
    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 15, message = "Name should be between 2 and 15 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "Name must contain only letters")
    private String name;


    @NotEmpty(message = "surName should not be empty")
    @Size(min = 2, max = 15, message = "surName should be between 2 and 15 characters")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "surName must contain only letters")
    private String surName;

    @Column(unique = true)
    @NotEmpty(message = "Empty values not allowed")
    @Email(message = "Not correct email entered")
    private String email;

    @Column()
    @Min(value = 1, message = "Age  should not be greater 1")
    private int age;

    @Column
    @NotEmpty(message = "Empty values not allowed")
    @Size(min = 3, max = 256, message = "Password should be between 3 and 8 character")
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

    public User(String name, String password, Collection<Role> roles) {
        this.name = name;
        this.password = password;
        this.roles = roles;
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
        return roles;


    }


}
