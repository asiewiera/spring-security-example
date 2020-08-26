package com.ge.springsecurityexample.config;


import com.ge.springsecurityexample.entity.AppUser;
import com.ge.springsecurityexample.repository.AppUserRepository;
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


@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    private final AppUserRepository appUserRepository;

    @Autowired
    public WebSecurityConfig(AppUserRepository appUserRepository) {
        this.appUserRepository = appUserRepository;
    }


    @Bean
    public PasswordEncoder getPasswordEncoder(){
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
                AppUser appUser = appUserRepository.findByName(username);
                return appUser;
            }
        });
        AppUser appUser = new AppUser();
        appUser.setName("user2");
        appUser.setPassword(getPasswordEncoder().encode("user2"));
        appUser.setRole("ROLE_USER");
        appUserRepository.save(appUser);
        appUser = new AppUser();
        appUser.setName("admin2");
        appUser.setPassword(getPasswordEncoder().encode("admin2"));
        appUser.setRole("ROLE_ADMIN");
        appUserRepository.save(appUser);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/hello/a").hasRole("ADMIN")
                .antMatchers("/hello/u").hasAuthority("ROLE_USER")
                .anyRequest().permitAll()
                .and().formLogin().permitAll()
                .and().logout();
    }


}
