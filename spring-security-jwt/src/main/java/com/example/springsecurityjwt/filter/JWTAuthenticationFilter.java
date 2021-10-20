package com.example.springsecurityjwt.filter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.springsecurityjwt.domain.dto.CustomUserDetails;
import com.example.springsecurityjwt.domain.entity.UserVO;
import com.example.springsecurityjwt.util.JWTUtils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Component;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 这个过滤器是身份验证的过滤器. 主要职责是完成对请求中携带的用户信息的校验并颁发 access-token.
 */
@Slf4j
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        super.setFilterProcessesUrl("/auth/login");
        super.setUsernameParameter("username");

    }

    @SneakyThrows
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        log.info("authentication filter successful authentication:{}",authResult);
        UserDetails userDetails = (UserDetails) authResult.getPrincipal();
        CustomUserDetails customUserDetails = new CustomUserDetails();
        customUserDetails.setName(userDetails.getUsername());
        customUserDetails.setPassword(userDetails.getPassword());
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (GrantedAuthority authority : userDetails.getAuthorities()) {
            authorities.add(new SimpleGrantedAuthority(authority.getAuthority()));
        }
        customUserDetails.setAuthorities(authorities);
        String value = JWTUtils.TOKEN_PREFIX + JWTUtils.create(customUserDetails.getUsername(),false,customUserDetails);
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("access-token",value);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(jsonObject.toJSONString());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        log.info("authentication filter unsuccessful authentication:{}",failed.getMessage());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
        response.getWriter().write("authentication failed,reason: " + failed.getMessage());
    }
}
