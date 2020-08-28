package com.ge.springsecurityexample.dto;

import com.ge.springsecurityexample.entity.AppUser;
import com.ge.springsecurityexample.entity.UserRole;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;


@Component
public class AppUserDto implements UserDetails {

    private Long id;

    private String name;

    private String password;

    private Set<UserRole> userRoles;

    public AppUserDto() {
    }

    public AppUserDto(AppUser appUser) {
        this.id = appUser.getId();
        this.name = appUser.getName();
        this.password = appUser.getPassword();
        this.userRoles = appUser.getUserRoles();
    }


    public AppUserDto(Long id, String name, String password, Set<UserRole> userRoles) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.userRoles = userRoles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userRoles.stream().map(e -> new SimpleGrantedAuthority(e.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return name;
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
