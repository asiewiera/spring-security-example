package com.ge.springsecurityexample.service;

import com.ge.springsecurityexample.dto.AppUserDto;
import com.ge.springsecurityexample.entity.AppUser;
import com.ge.springsecurityexample.entity.UserRole;
import com.ge.springsecurityexample.repository.AppUserRepository;
import com.ge.springsecurityexample.repository.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AppUserServiceImpl implements AppUserService {

    AppUserRepository appUserRepository;
    UserRoleRepository userRoleRepository;

    public AppUserServiceImpl(AppUserRepository appUserRepository, UserRoleRepository userRoleRepository) {
        this.appUserRepository = appUserRepository;
        this.userRoleRepository = userRoleRepository;
    }


    public AppUserDto findByName(String username) {
        AppUser appUser = appUserRepository.findByName(username);
        Set<UserRole> userRoles = userRoleRepository.findByAppUsers(appUser);
        AppUserDto appUserDto = new AppUserDto(appUser);
        appUserDto.setUserRoles(userRoles);
        return appUserDto;
    }


}
