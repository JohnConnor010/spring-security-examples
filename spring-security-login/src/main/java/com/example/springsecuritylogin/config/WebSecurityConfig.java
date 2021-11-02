package com.example.springsecuritylogin.config;

import com.example.springsecuritylogin.handler.CustomAccessDeniedHandler;
import com.example.springsecuritylogin.handler.CustomAuthenticationFailureHandler;
import com.example.springsecuritylogin.handler.CustomLogoutSuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private CustomAuthenticationFailureHandler authenticationFailureHandler;
    @Autowired
    private CustomLogoutSuccessHandler logoutSuccessHandler;
    @Autowired
    private CustomAccessDeniedHandler accessDeniedHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user1").password(passwordEncoder().encode("123456")).roles("USER")
                .and()
                .withUser("user2").password(passwordEncoder().encode("123456")).roles("USER")
                .and()
                .withUser("admin").password(passwordEncoder().encode("123456")).roles("ADMIN");

    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/anonymous/**").anonymous()
                .antMatchers("/login/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .defaultSuccessUrl("/homePage")
                .failureHandler(authenticationFailureHandler)
                .and()
                .logout()
                .logoutUrl("/perform_logout")
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies("JSESSIONID")
                .and()
                .exceptionHandling().accessDeniedHandler(accessDeniedHandler)
                ;
    }

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
