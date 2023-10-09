package com.jcho.backapi;

import com.jcho.backapi.domain.game.GameLog;
import com.jcho.backapi.domain.game.GameLogRepository;
import com.jcho.backapi.domain.user.User;
import com.jcho.backapi.domain.user.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SpringBootApplication
public class BackApiApplication {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private GameLogRepository gameLogRepository;


    @Value("${front-server.local-url}")
    private String frontLocalURL;

    @Value("${front-server.container-url}")
    private String frontConURL;

    @Value("${front-server.socket-url}")
    private String socketURL;

    @Value("${front-server.cloud-server}")
    private String cloudURL;

    public static final Logger logger = LoggerFactory.getLogger(BackApiApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(BackApiApplication.class, args);
    }

    /**
     * CORS config
     * @return
     */
    @Bean
    public WebMvcConfigurer corsConfigurer2(){
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowCredentials(false)
                        .allowedMethods("*")
                        .allowedHeaders("*")
                        .allowedOrigins(frontLocalURL, frontConURL, socketURL, cloudURL, "*");
            }
        };
    }

    /*@Bean
    public CorsConfigurationSource corsConfigurer(){
        CorsConfiguration corsConfiguration = new CorsConfiguration();

        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);
        return source;
    }*/

    @PostConstruct
    public void initUser(){

        List<User> viewList = userRepository.findAll();

        userRepository.deleteAll();

        List<User> userList = Stream.of(
                new User("test1", "123", "철수")
                , new User("test2", "123", "성준")
                , new User("test3", "123", "옥희")
        ).collect(Collectors.toList());
        userRepository.saveAll(userList);

        List<GameLog> gameLogs = Stream.of(
                new GameLog(1, 2)
                , new GameLog(1, 3)
                , new GameLog(2, 3)
        ).collect(Collectors.toList());

        gameLogRepository.saveAll(gameLogs);
    }
}
