package com.jcho.backapi.filter;

import com.jcho.backapi.common.CommonCode;
import com.jcho.backapi.util.JwtUtil;
import com.jcho.backapi.web.user.service.LoginService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
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
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthFilter extends GenericFilterBean {

    @Autowired
    private LoginService loginService;



    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String token = ((HttpServletRequest)request).getHeader(CommonCode.JWT_HEADER);
        String userId = null;

        // 현재 Authentication 정보
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 허용 URL 체크
        boolean isAllowUrl = false;
        for(int i=0; i < CommonCode.ALLOW_URLS.length; i++){
            String url = ((HttpServletRequest) request).getRequestURI();
            String checkUrl = CommonCode.ALLOW_URLS[i];
            if(url.equals(checkUrl)){
                isAllowUrl = true;
                break;
            }
        }

        // 토큰 확인 유효 확인
        try {
            if(isAllowUrl == false
                    && token != null
                    && token.startsWith(CommonCode.TOKEN_PREFIX)
                    && JwtUtil.validateToken(token)){

                userId = JwtUtil.extractUserId(token);
                //getPrincipal 과 loadUserByUsername로 불러온 UserDetails 값 비교하기
                //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
                UserDetails userDetails = loginService.loadUserByUsername(userId);

                SecurityContextHolder.getContext().setAuthentication(authentication);

                UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                        new UsernamePasswordAuthenticationToken(userDetails, authentication.getCredentials(), userDetails.getAuthorities());
                usernamePasswordAuthenticationToken.setDetails(authentication);
            }
        } catch (SignatureException | MalformedJwtException e) {
            ((HttpServletResponse) response).sendError(401, "SignatureException error");
            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            // 유효기간 지남
            ((HttpServletResponse) response).sendError(401, "ExpiredJwtException error");
            filterChain.doFilter(request, response);
        } catch(Exception e) {
            SecurityContextHolder.clearContext();
            filterChain.doFilter(request, response);
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