package com.ge.springsecurityexample.entity;

import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.util.Set;

@Component
@Table(name = "app_user")
@Entity
public class AppUser {

    public String getPassword() {
        return password;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    private String password;

    @ManyToMany
    private Set<UserRole> userRoles;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<UserRole> getUserRoles() {
        return userRoles;
    }

    public void setUserRoles(Set<UserRole> userRoles) {
        this.userRoles = userRoles;
    }
}
