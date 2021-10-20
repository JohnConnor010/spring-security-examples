package com.example.springsecurityjwt.mybatis.mapper;

import com.example.springsecurityjwt.mybatis.entity.Role;
import com.example.springsecurityjwt.mybatis.entity.UserDAO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AuthenticationMapper {
    UserDAO loadUserByUsername(final String username);
    List<Role> findRoleByUserId(final Integer userId);
    int saveUserDAO(UserDAO userDAO);
}
