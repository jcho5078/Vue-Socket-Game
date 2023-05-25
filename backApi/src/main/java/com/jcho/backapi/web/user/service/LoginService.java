package com.jcho.backapi.web.user.service;

import com.jcho.backapi.web.user.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.servlet.http.HttpServletRequest;

public interface LoginService extends UserDetailsService {

    /**
     * spring security, 들어온 ID값으로 유저 확인
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException;

    /**
     * login 수행
     * @param userDto
     * @return
     * @throws Exception
     */
    public UserDto login(UserDto userDto) throws Exception;

    /**
     * signUp 수행
     * @param userDto
     * @return
     * @throws Exception
     */
    public <T> T signUp(UserDto userDto) throws Exception;

    /**
     * 유저정보 반환
     * @param userDto
     * @return
     * @throws Exception
     */
    public UserDto getUserInfo(UserDto userDto) throws Exception;
}
