package com.jcho.backapi.web.user.service.impl;

import com.jcho.backapi.BackApiApplication;
import com.jcho.backapi.common.CommonCode;
import com.jcho.backapi.domain.user.User;
import com.jcho.backapi.domain.user.UserRepository;
import com.jcho.backapi.util.JwtUtil;
import com.jcho.backapi.web.user.dto.UserDto;
import com.jcho.backapi.web.user.service.LoginService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    private UserRepository userRepository;

    private Logger logger = BackApiApplication.logger;

    /**
     * spring security 인증
     * @param userId
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
        long id = Long.parseLong(userId);
        User user = userRepository.findById(id).get();
        return new org.springframework.security.core.userdetails.User(user.getLoginId(), user.getLoginPw(), new ArrayList<>());
    }

    /**
     * login 수행
     * @param userDto
     * @return
     * @throws Exception
     */
    @Override
    public UserDto login(UserDto userDto) throws Exception {
        User user = userRepository.findUsersByLoginIdAndLoginPw(userDto.getLoginId(), userDto.getLoginPw());
        if(user == null) return null;

        UserDto responseDto = UserDto.toDto(user);

        responseDto.setLoginPw(null);//hide in response password

        if(StringUtils.isEmpty(responseDto.getUserNo())){
            logger.info("'" + userDto.getLoginId() + "' is not found.\n");
            return null;
        }else{
            responseDto.setJwtToken(JwtUtil.generateToken(Long.toString(responseDto.getUserNo())));
        }

        return responseDto;
    }



    /**
     * signUp 수행
     * @param userDto
     * @return
     * @throws Exception
     */
    @Override
    public <T> T signUp(UserDto userDto) throws Exception{
        if (userRepository.existsLoginId(userDto.getLoginId())
                || userRepository.existsUserNm(userDto.getUserNm())){
            logger.info("'" + userDto.getLoginId() + "/" + userDto.getUserNm() + "' is already exist.\n");
            return (T) new Exception("'" + userDto.getLoginId() + "/" + userDto.getUserNm() + "' is already exist.");
        }

        User user = userRepository.save(new User().toEntity(userDto));
        UserDto responseDto = UserDto.toDto(user);

        responseDto = this.login(responseDto);

        return (T) responseDto;
    };

    /**
     * 유저정보 반환
     * @return
     * @throws Exception
     */
    @Override
    public UserDto getUserInfo(UserDto userDto) throws Exception{
        UserDto responseDto = UserDto.toDto(userRepository.findById(userDto.getUserNo()).get());

        return responseDto;
    }
}