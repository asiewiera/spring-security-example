package com.ge.springsecurityexample.repository;

import com.ge.springsecurityexample.dto.AppUserDto;
import com.ge.springsecurityexample.entity.AppUser;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    AppUser findByName(String name);

    @Query("SELECT new com.ge.springsecurityexample.dto.AppUserDto(au.id, au.name, au.password, au.userRoles) from AppUser au inner join au.userRoles WHERE au.name=?1")
    AppUserDto findAppUserDtoByName(String name);


    @Query("SELECT distinct au from AppUser au join fetch au.userRoles where au.name= :name")
    List<AppUser> findAppUserDtoByName2(String name);
}
