package com.example.springsecuritylogin.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
public class SecuredResourceController {

    @GetMapping(value = "/secured")
    public ResponseEntity<String> secured(HttpServletRequest request, HttpServletResponse response){
        return new ResponseEntity<>("accessing secured resource", HttpStatus.OK);
    }
}
