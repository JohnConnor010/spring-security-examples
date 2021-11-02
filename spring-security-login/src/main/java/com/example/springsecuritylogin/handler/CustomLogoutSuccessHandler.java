package com.example.springsecuritylogin.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.authentication.logout.SimpleUrlLogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@Slf4j
public class CustomLogoutSuccessHandler extends SimpleUrlLogoutSuccessHandler implements LogoutSuccessHandler {
    public CustomLogoutSuccessHandler() {
        super();
        log.info("CustomLogoutSuccessHandler");
    }

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        final String refererUrl = request.getHeader("Referer");
        log.info("referer url:{}",refererUrl);
        super.onLogoutSuccess(request, response, authentication);
    }
}
