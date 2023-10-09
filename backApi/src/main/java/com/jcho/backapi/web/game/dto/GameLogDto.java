package com.jcho.backapi.web.game.dto;

import com.jcho.backapi.domain.game.GameLog;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameLogDto implements Serializable {
    private long userNo;
    private long killedUserNo;
    private LocalDateTime killDt;

    public static GameLogDto toDto(GameLog gameLog){
        return GameLogDto.builder()
                .userNo(gameLog.getUserNo().getUserNo())
                .killedUserNo(gameLog.getKilledUserNo().getUserNo())
                .killDt(gameLog.getKillDt())
                .build();
    }
}