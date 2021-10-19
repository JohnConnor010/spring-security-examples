package com.example.springsecuritycustomuserdetails.mybatis.mapper;

import com.example.springsecuritycustomuserdetails.mybatis.entity.Role;
import com.example.springsecuritycustomuserdetails.mybatis.entity.UserDAO;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Mapper
public interface AuthenticationMapper {
    UserDAO loadUserByUsername(final String username);

    List<Role> findRoleByUserId(final Integer userId);
}
