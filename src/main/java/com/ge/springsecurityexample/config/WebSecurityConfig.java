package com.ge.springsecurityexample.config;


import com.ge.springsecurityexample.dto.AppUserDto;
import com.ge.springsecurityexample.entity.AppUser;
import com.ge.springsecurityexample.entity.UserRole;
import com.ge.springsecurityexample.repository.AppUserRepository;
import com.ge.springsecurityexample.repository.UserRoleRepository;
import com.ge.springsecurityexample.service.AppUserService;
import com.ge.springsecurityexample.service.AppUserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppUserRepository appUserRepository;
    private final UserRoleRepository userRoleRepository;
    private final AppUserService appUserService;

    @Autowired
    public WebSecurityConfig(AppUserRepository appUserRepository, UserRoleRepository userRoleRepository, AppUserService appUserService) {
        this.appUserRepository = appUserRepository;
        this.userRoleRepository = userRoleRepository;
        this.appUserService = appUserService;
    }


    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
/*        User user1 = new User("user1", getPasswordEncoder().encode("user1"), Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER")));
        User admin1 = new User("admin1", "{noop}admin1", Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"), new SimpleGrantedAuthority("ROLE_ADMIN")));
        auth.inMemoryAuthentication()
                .withUser(user1);
        auth.inMemoryAuthentication()
                .withUser("admin1").password("{noop}admin1").roles("ADMIN", "USER");*/


        auth.userDetailsService(new UserDetailsService() {
            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                //AppUser appUser = appUserRepository.findByName(username);

                AppUserDto appUserDto = appUserService.findByName(username);
                return appUserDto;
            }
        });
        Set<UserRole> userRoles = new HashSet<>();
        UserRole userRole = new UserRole();
        userRole.setName("ROLE_USER");
        userRoles.add(userRole);
        userRole = new UserRole();
        userRole.setName("ROLE_ADMIN");
        userRoles.add(userRole);
        userRole = new UserRole();
        userRole.setName("ROLE_TESTER");
        userRoles.add(userRole);
        userRoleRepository.saveAll(userRoles);

        AppUser appUser = new AppUser();
        appUser.setName("user2");
        appUser.setPassword(getPasswordEncoder().encode("user2"));
        Iterable<UserRole> userRolesAll = userRoleRepository.findAll();
        userRoles = StreamSupport.stream(userRolesAll.spliterator(), true)
                .filter(e -> e.getName().matches("^(ROLE_USER|ROLE_TESTER)$"))
                .collect(Collectors.toSet());
        appUser.setUserRoles(userRoles);
        appUserRepository.save(appUser);
        appUser = new AppUser();
        appUser.setName("admin2");
        appUser.setPassword(getPasswordEncoder().encode("admin2"));
        userRoles = StreamSupport.stream(userRolesAll.spliterator(), true)
                .filter(e -> e.getName().matches("^(ROLE_ADMIN|ROLE_USER)$"))
                .collect(Collectors.toSet());
        appUser.setUserRoles(userRoles);
        appUserRepository.save(appUser);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic();
        http.authorizeRequests()
                .antMatchers("/hello/a").hasRole("ADMIN")
                .antMatchers("/hello/u").hasAuthority("ROLE_USER")
                .anyRequest().permitAll()
                .and().formLogin().permitAll()
                .and().logout();
    }


}
