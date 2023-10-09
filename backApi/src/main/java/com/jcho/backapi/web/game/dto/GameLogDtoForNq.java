package com.jcho.backapi.web.game.dto;

import java.time.LocalDateTime;

public interface GameLogDtoForNq {
    public Long getUserNo();
    public String getUserNm();
    public Long getKilledUserNo();
    public String getKilledUserNm();
    public Long getKillCnt();
    public LocalDateTime getKillDt();
    public Long getRank();
}
