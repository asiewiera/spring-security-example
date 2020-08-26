package com.ge.springsecurityexample.service;

import com.ge.springsecurityexample.dto.AppUserDto;
import org.springframework.stereotype.Service;


public interface AppUserService {
    public AppUserDto findByName(String username);
}
