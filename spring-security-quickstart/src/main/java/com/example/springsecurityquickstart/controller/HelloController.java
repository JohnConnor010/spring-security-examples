package com.example.springsecurityquickstart.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class HelloController {
    @GetMapping(value = "/hello")
    public String hello(){
        log.info("authentication:{}", SecurityContextHolder.getContext().getAuthentication());
        return "You have successfully accessed inside";
    }
}
