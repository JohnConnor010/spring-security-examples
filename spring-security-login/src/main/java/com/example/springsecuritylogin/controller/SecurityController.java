package com.example.springsecuritylogin.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
public class SecurityController {

    @RequestMapping(value = "",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String index(){
        return "this is index page";
    }

    @RequestMapping(value = "/login")
    public String login(@RequestParam(value = "error",required = false,defaultValue = "true") final boolean error){
        log.info("error=" + error);
        log.info("login");
        return "login.html";
    }

    @GetMapping(value = "/admin/index",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String adminIndex(){
        return "This is admin index";
    }

    @GetMapping(value = "/anonymous/index",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String anonymousIndex(){
        return "this is anonymous index";
    }

    @GetMapping(value = "/login/index",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String loginIndex(){
        return "this is login index";
    }

    @GetMapping(value = "/accessDenied")
    public String accessDenied(){
        return "accessDenied.html";
    }

    @GetMapping(value = "/homePage")
    public String homePage(){
        return "homepage.html";
    }

    @RequestMapping(value = "/doLogin",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String performLogin(){
        return "this is perform_login page";
    }

    @GetMapping(value = "/loginFail",produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String loginFail(){
        return "loginFail";
    }
}
