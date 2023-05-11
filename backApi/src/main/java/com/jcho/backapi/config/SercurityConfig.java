package com.jcho.backapi.config;

import com.jcho.backapi.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SercurityConfig {

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Bean
    protected SecurityFilterChain configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .headers().frameOptions().disable()
                .and()
                .authorizeHttpRequests()
                .requestMatchers("/user/login").permitAll()// 임시로 접근 완전 허용
                .requestMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
                    .hasAnyRole("USER", "AUTH").anyRequest().authenticated();
        return http.build();
    }
}
