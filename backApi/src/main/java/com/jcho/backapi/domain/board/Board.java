package com.jcho.backapi.domain.board;

import com.jcho.backapi.domain.user.User;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_BOARD")
@Getter
public class Board {

    @Id
    @Column(name = "BOARD_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;

    @Comment("등록일")
    @Column(name = "BOARD_TITLE", length = 350)
    private String boardTitle;

    @Comment("등록일")
    @Column(name = "BOARD_DT", length = 30)
    @CreatedDate
    private LocalDateTime boardDt;

    @Comment("수정일")
    @Column(name = "MOD_DT", length = 30)
    @CreatedDate
    private LocalDateTime modDt;

    @ManyToOne
    @JoinColumn(name = "USER_ID", nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private User user;
}