package com.jcho.backapi.filter;

import com.jcho.backapi.util.JwtUtil;
import com.jcho.backapi.web.user.service.impl.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private LoginService loginService;

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = ((HttpServletRequest)request).getHeader(jwtUtil.jwtHeader);
        String userId = null;

        // 현재 Authentication 정보
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 토큰 확인 유효 확인
        if(token != null && jwtUtil.validateToken(token)){
            userId = jwtUtil.extractUserId(token);
            //getPrincipal 과 loadUserByUsername로 불러온 UserDetails 값 비교하기
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            UserDetails userDetails2 = loginService.loadUserByUsername(userId);

            SecurityContextHolder.getContext().setAuthentication(authentication);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(authentication);
        }

        /*if(token != null && jwtUtil.validateToken(token)){
            userId = jwtUtil.extractUserId(token);
            UserDetails userDetails = loginService.loadUserByUsername(userId);

            UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));

            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }*/
        filterChain.doFilter(request, response);
    }
}