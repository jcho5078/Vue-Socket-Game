package com.jcho.backapi.web.user.controller;

import com.jcho.backapi.BackApiApplication;
import com.jcho.backapi.domain.user.UserRepository;
import com.jcho.backapi.util.JwtUtil;
import com.jcho.backapi.web.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.slf4j.Logger;
import java.util.logging.Level;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    private Logger logger = BackApiApplication.logger;

    /**
     * 로그인
     * @param userDto
     * @return
     * @throws Exception
     */
    @PostMapping("/login")
    public ResponseEntity<EntityModel> login(@RequestBody UserDto userDto) throws Exception{

        UserDto responseDto = UserDto.toDto(userRepository.findUsersByLoginIdAndLoginPw(userDto.getLoginId(), userDto.getLoginPw()));

        if(StringUtils.isEmpty(responseDto.getUserId())){
            logger.info("'" + userDto.getLoginId() + "' is not found.\n");
            return ResponseEntity
                    .notFound().build();
        }

        EntityModel entityModel = EntityModel.of(responseDto, linkTo(methodOn(UserController.class).login(responseDto)).withSelfRel());

        return ResponseEntity.ok()
                .header(jwtUtil.jwtHeader, jwtUtil.generateToken(Long.toString(responseDto.getUserId())))
                .body(entityModel);
    }
}