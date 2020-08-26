package com.ge.springsecurityexample.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExampleController {

    @GetMapping("/hello")
    public String hello() {
        return "Ge my hero";
    }

    @GetMapping("/hello/u")
    public String helloUser() {
        return "Ge my hero, user";
    }

    @GetMapping("/hello/a")
    public String helloAdmin() {
        return "Ge my hero, admin";
    }

}
