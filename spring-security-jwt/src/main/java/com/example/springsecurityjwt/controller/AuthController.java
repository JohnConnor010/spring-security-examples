package com.example.springsecurityjwt.controller;

import com.example.springsecurityjwt.mybatis.entity.UserDAO;
import com.example.springsecurityjwt.mybatis.mapper.AuthenticationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthenticationMapper authMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @GetMapping(value = "/hello",produces = MediaType.APPLICATION_JSON_VALUE)
    public String hello(){
        return "HelloWorld";
    }

    @PostMapping(value = "/register",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDAO> register(@RequestBody Map<String,Object> payload){
        UserDAO userDAO = new UserDAO();
        userDAO.setName(payload.get("name").toString());
        String password = passwordEncoder.encode(payload.get("password").toString());
        userDAO.setPassword(password);
        authMapper.saveUserDAO(userDAO);
        return new ResponseEntity<>(userDAO, HttpStatus.OK);
    }
}
