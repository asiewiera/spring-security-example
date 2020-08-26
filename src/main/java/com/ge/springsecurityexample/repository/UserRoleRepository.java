package com.ge.springsecurityexample.repository;

import com.ge.springsecurityexample.entity.AppUser;
import com.ge.springsecurityexample.entity.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {

    Set<UserRole> findByAppUsers(AppUser appUser);


}
