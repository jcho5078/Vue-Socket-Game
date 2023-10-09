package com.jcho.backapi.web.game.service;

import com.jcho.backapi.web.game.dto.GameLogDto;
import com.jcho.backapi.web.game.dto.GameLogDtoForNq;

import java.util.List;

public interface GameService {

    public List<GameLogDtoForNq> getRankList() throws Exception;

    public GameLogDto getRankUserInfo(long userNo) throws Exception;
}
