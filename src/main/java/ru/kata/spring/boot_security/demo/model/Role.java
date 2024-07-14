package ru.kata.spring.boot_security.demo.model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;
import java.util.Set;

@Getter
@Setter
@Entity(name = "t_roles")
public class Role implements GrantedAuthority {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String roles;

//    @Transient
//    @ManyToMany(mappedBy = "roles")
//    private Set<User> users;


    public Role(Long id, String role) {

        this.id = id;
        this.roles = role;
    }


    public Role() {
    }

//    public Set<User> getUsers() {
//        return users;
//    }

//    public void setUsers(Set<User> users) {
//        this.users = users;
//    }

//    public Long getId() {
//        return id;
//    }
//
//    public void setId(Long id) {
//        this.id = id;
//    }
//
//    public String getRoles() {
//        return roles;
//    }
//
//    public void setRoles(String roles) {
//        this.roles = roles;
//    }


    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", roles='" + roles +
                '}';
    }

    @Override
    public String getAuthority() {
        return roles;
    }
}
