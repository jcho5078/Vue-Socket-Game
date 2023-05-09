package com.jcho.backApi.web.user.controller;

import com.jcho.backApi.domain.user.User;
import com.jcho.backApi.domain.user.UserRepository;
import com.jcho.backApi.util.JwtUtil;
import com.jcho.backApi.web.user.dto.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<EntityModel> login(@RequestBody UserDto userDto) throws Exception{

        EntityModel entityModel = EntityModel.of(userDto , );


        return ResponseEntity.ok()
                .header(jwtUtil.jwtHeader, jwtUtil.generateToken(Long.toString(userDto.getUserId())))
                .body(entityModel);
    }
}
