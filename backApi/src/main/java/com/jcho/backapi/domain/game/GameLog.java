package com.jcho.backapi.domain.game;

import com.jcho.backapi.domain.user.User;
import com.jcho.backapi.web.game.dto.GameLogDto;
import com.jcho.backapi.web.user.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_GAME_LOG")
@Getter
@NoArgsConstructor
@DynamicInsert
@DynamicUpdate
public class GameLog {

    public GameLog(long userNo, long killedUserNo){
        this.user = new User(userNo);
        this.killedUser = new User(killedUserNo);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "LOG_ID", nullable = false)
    private Long logId;

    @Comment("유저NO")
    @ManyToOne
    @JoinColumn(name = "USER_NO", nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private User user;

    @Comment("킬 유저NO")
    @ManyToOne
    @JoinColumn(name = "KILLED_USER_NO", nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private User killedUser;

    @Comment("일시")
    @Column(name = "KILL_DT", length = 30)
    @CreatedDate
    private LocalDateTime killDt;

    /**
     * convert entity
     * @param gameLogDto
     * @return
     */
    public GameLog toEntity(GameLogDto gameLogDto){
        this.user = new User(gameLogDto.getUserNo());
        this.killedUser = new User(gameLogDto.getKilledUserNo());
        this.killDt = LocalDateTime.now();

        return this;
    }
}