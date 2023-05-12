package com.jcho.backapi.config;

import com.jcho.backapi.filter.JwtAuthFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration(proxyBeanMethods = false)
@EnableWebSecurity
public class SercurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.key}")
    private String jwtKey;

    @Autowired
    private JwtAuthFilter jwtAuthFilter;

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.csrf().disable()
                .httpBasic().disable()
                .cors()
                .and()
                //.headers().frameOptions().disable().and()
                .authorizeHttpRequests()
                .requestMatchers("/user/login", "/user/signUp", "/board/list", "/board/view").permitAll()
                .requestMatchers("/v2/api-docs", "/configuration/**", "/swagger*/**", "/webjars/**")
                .hasAnyRole("AUTH").anyRequest().authenticated();
    }

}
