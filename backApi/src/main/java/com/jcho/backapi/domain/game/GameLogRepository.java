package com.jcho.backapi.domain.game;

import com.jcho.backapi.web.game.dto.GameLogDto;
import com.jcho.backapi.web.game.dto.GameLogDtoForNq;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GameLogRepository extends JpaRepository<GameLog, Long> {

    @Query(value = "SELECT l.user_no as userNo, u.user_nm as userNm, count(l.user_no) as killCnt, row_number() over(order by l.user_no desc) as rank " +
            "FROM t_game_log l join t_user u " +
            "WHERE l.user_no = u.user_no " +
            "group by l.user_no, u.user_nm " +
            "order by rank desc", nativeQuery = true)
    public List<GameLogDtoForNq> getGameLogList();
}