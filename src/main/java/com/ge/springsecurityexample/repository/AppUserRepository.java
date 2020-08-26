package com.ge.springsecurityexample.repository;

import com.ge.springsecurityexample.entity.AppUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Long> {
    AppUser findByName(String name);
}
