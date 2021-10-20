package com.example.springsecurityjwt.filter;

import com.example.springsecurityjwt.domain.dto.CustomUserDetails;
import com.example.springsecurityjwt.util.JWTUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * 这个过滤器用于对携带 access-token 的请求执行权限检查.
 */
@Slf4j
public class JWTAuthorizationFilter extends OncePerRequestFilter {
    private static final Set<String> WHITE_LIST = Stream.of("/auth/register").collect(Collectors.toSet());
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        log.info("authorization filter doFilterInternal");
        final String authorization = request.getHeader(JWTUtils.TOKEN_HEADER);
        log.info("raw-access-token: {}",authorization);
        if (!StringUtils.hasText(authorization)) {
            if (WHITE_LIST.contains(request.getRequestURI())) {
                filterChain.doFilter(request,response);
            }else{
                response.getWriter().write("未经授权的访问");
            }
            return;
        }
        final String jsonWebToken = authorization.replace(JWTUtils.TOKEN_PREFIX,"");
        CustomUserDetails customUserDetails = JWTUtils.userDetails(jsonWebToken);
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(customUserDetails.getUsername(),customUserDetails.getPassword(),customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(token);
        filterChain.doFilter(request,response);

    }
}
