package com.example.springsecuritycustomuserdetails;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class SpringSecurityCustomUserDetailsApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringSecurityCustomUserDetailsApplication.class, args);

    }

}
