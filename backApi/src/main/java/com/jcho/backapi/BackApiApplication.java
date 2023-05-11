package com.jcho.backapi;

import com.jcho.backapi.domain.user.User;
import com.jcho.backapi.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class BackApiApplication {

    @Autowired
    private UserRepository userRepository;

    public static final Logger logger = LoggerFactory.getLogger(BackApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BackApiApplication.class, args);
    }

    @PostConstruct
    public void initUser(){
        List<User> userList = Stream.of(
                new User("test1", "123")
                , new User("test2", "123")
                , new User("test3", "123")
        ).collect(Collectors.toList());
        userRepository.saveAll(userList);
    }
}
