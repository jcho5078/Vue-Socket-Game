package com.jcho.backapi.web.user.controller;

import com.jcho.backapi.BackApiApplication;
import com.jcho.backapi.common.CommonCode;
import com.jcho.backapi.common.ResultCode;
import com.jcho.backapi.common.dto.ResponseDTO;
import com.jcho.backapi.domain.user.User;
import com.jcho.backapi.domain.user.UserRepository;
import com.jcho.backapi.util.CommonUtil;
import com.jcho.backapi.util.JwtUtil;
import com.jcho.backapi.web.user.dto.UserDto;
import com.jcho.backapi.web.user.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.logging.Level;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LoginService loginService;

    /**
     * 로그인
     * @param userDto
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDto userDto) throws Exception{

        return CommonUtil.responseResult(loginService.login(userDto));
    }

    /**
     * 회원가입
     * @param userDto
     * @return
     * @throws Exception
     */
    @PostMapping("/signUp")
    public ResponseEntity<?> signUp(@RequestBody UserDto userDto) throws Exception{

        return CommonUtil.responseResult(loginService.signUp(userDto));
    }

    /**
     * 유저정보 확인
     * @param request
     * @return
     */
    @GetMapping("/info")
    public ResponseEntity<?> getUserInfo(HttpServletRequest request, UserDto userDto) throws Exception {

        return CommonUtil.responseResult(request, loginService.getUserInfo(userDto));
    }
}