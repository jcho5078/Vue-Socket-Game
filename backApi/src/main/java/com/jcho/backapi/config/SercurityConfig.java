package com.jcho.backapi.config;

import com.jcho.backapi.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.http.HttpServletRequest;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SercurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .headers(). frameOptions().disable()
                .and()
                .authorizeHttpRequests(authorizationManagerRequestMatcherRegistry -> {
                    authorizationManagerRequestMatcherRegistry

                            .requestMatchers(new RequestMatcher() {
                                @Override
                                public boolean matches(HttpServletRequest request) {
                                    return false;
                                }
                            }).hasAnyRole().anyRequest().authenticated()

                })

                .authorizeHttpRequests()


                .requestMatchers("/user/login").permitAll()// 임시로 접근 완전 허용
                .requestMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
                .hasAnyRole("USER", "AUTH").anyRequest().authenticated()
                .formLogin(login -> login
                    .loginPage("/user/login")
                    .permitAll()
                )
                .logout(logout -> logout
                    .permitAll()
                );
        return http.build();
    }
}
