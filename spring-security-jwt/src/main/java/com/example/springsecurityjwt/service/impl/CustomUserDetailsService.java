package com.example.springsecurityjwt.service.impl;

import com.example.springsecurityjwt.mybatis.entity.Role;
import com.example.springsecurityjwt.mybatis.entity.UserDAO;
import com.example.springsecurityjwt.mybatis.mapper.AuthenticationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CustomUserDetailsService implements UserDetailsService {
    @Autowired
    private AuthenticationMapper authMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDAO userDAO = authMapper.loadUserByUsername(username);
        if (userDAO == null) {
            throw new UsernameNotFoundException("用户未找到");
        }
        List<Role> roles = authMapper.findRoleByUserId(userDAO.getId());
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return new User(userDAO.getName(), userDAO.getPassword(),authorities);
    }
}
