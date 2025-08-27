package com.techsoft.spring_boot_cicd.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/welcome")
    public String getMassage(){
        return "welcome to learn Jenkins";
    }
}
