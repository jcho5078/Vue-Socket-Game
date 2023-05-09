package com.jcho.backapi.web.user.controller;

import com.jcho.backapi.domain.user.User;
import com.jcho.backapi.domain.user.UserRepository;
import com.jcho.backapi.util.JwtUtil;
import com.jcho.backapi.web.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<EntityModel> login(@RequestBody UserDto userDto) throws Exception{

        UserDto responseDto = UserDto.mapper(userRepository.findById(userDto.getUserId()).get());

        EntityModel entityModel = EntityModel.of(userDto, linkTo(methodOn(UserController.class).login(responseDto)).withSelfRel());

        return ResponseEntity.ok()
                .header(jwtUtil.jwtHeader, jwtUtil.generateToken(Long.toString(userDto.getUserId())))
                .body(entityModel);
    }
}