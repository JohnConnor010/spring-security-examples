package com.example.springsecuritylogin.handler;

import com.alibaba.fastjson.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;

@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        JSONObject jsonObject = new JSONObject();
        jsonObject.fluentPut("message",exception.getMessage())
                .fluentPut("timestamp", LocalDateTime.now());
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write(JSONObject.toJSONString(jsonObject));
    }
}
