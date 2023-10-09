package com.jcho.backapi.web.game.service.impl;

import com.jcho.backapi.domain.game.GameLog;
import com.jcho.backapi.domain.game.GameLogRepository;
import com.jcho.backapi.web.game.dto.GameLogDto;
import com.jcho.backapi.web.game.dto.GameLogDtoForNq;
import com.jcho.backapi.web.game.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameLogRepository gameLogRepository;

    @KafkaListener(topics = "game", groupId = "socket")
    public void consume(String message) throws IOException {
        System.out.println("receive message : " + message);
        Map<String, String> map = parseStringToMap(message, ",",":");

        GameLogDto gameLogDto = GameLogDto.builder()
                .userNo(Long.parseLong(map.get("bulletUserNo")))
                .killedUserNo(Long.parseLong(map.get("shipUserNo")))
                .build();
        GameLog gameLog = new GameLog().toEntity(gameLogDto);

        gameLogRepository.save(gameLog);
    }

    /**
     * String to Map
     * @param input
     * @param pairSeparator
     * @param keyValueSeparator
     * @return
     */
    public static Map<String, String> parseStringToMap(String input, String pairSeparator, String keyValueSeparator) {
        if (input == null || pairSeparator == null || keyValueSeparator == null) {
            return null;
        }

        input = input.replaceAll("[{\"}]", "");
        String[] pairs = input.split(pairSeparator);
        if (pairs.length == 0) {
            throw new IllegalArgumentException("Invalid input: " + input);
        }

        Map<String, String> map = new HashMap<>();
        for (String pair : pairs) {
            String[] keyValue = pair.split(keyValueSeparator);
            if (keyValue.length == 2) {
                map.put(keyValue[0], keyValue[1]);
            }
        }
        return map;
    }

    @Override
    public List<GameLogDtoForNq> getRankList() throws Exception {
        /*List<GameLog> gameLogs = gameLogRepository.getGameLogList();
        List<GameLogDto> dtos = gameLogs.stream().map(GameLogDto::toDto).collect(Collectors.toList());

        List<GameLogDto> result = dtos.stream().map(dto -> {
            return dto;
        }).collect(Collectors.toList());

        return result;*/
        List<GameLogDtoForNq> test = gameLogRepository.getGameLogList();
        return test;
    }

    @Override
    public GameLogDto getRankUserInfo(long userNo) throws Exception {
        return null;
    }
}
