package com.jcho.backapi.web.game.controller;

import com.jcho.backapi.web.game.dto.GameLogDto;
import com.jcho.backapi.web.game.dto.GameLogDtoForNq;
import com.jcho.backapi.web.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameLogController {

    @Autowired
    private GameService gameService;


    @GetMapping(value = "/rank")
    public List<GameLogDtoForNq> test() throws Exception{
        return gameService.getRankList();
    }
}
