package com.jcho.backapi.domain.board;

import com.jcho.backapi.domain.user.User;
import com.jcho.backapi.web.board.dto.BoardDto;
import com.jcho.backapi.web.user.dto.UserDto;
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
    private long boardNo;

    @Comment("게시물 제목")
    @Column(name = "BOARD_TITLE", length = 350)
    private String boardTitle;

    @Comment("게시물 내용")
    @Lob
    @Column(name = "BOARD_DETAIL", length = 350)
    private String boardDetail;

    @Comment("등록일")
    @Column(name = "REG_DT", length = 30)
    @CreatedDate
    private LocalDateTime regDt;

    @Comment("수정일")
    @Column(name = "MOD_DT", length = 30)
    @CreatedDate
    private LocalDateTime modDt;

    @Comment("작성자")
    @ManyToOne
    @JoinColumn(name = "REG_ID", nullable = false, foreignKey = @ForeignKey(ConstraintMode.CONSTRAINT))
    private User regUser;

    /**
     * convert board entity
     * @param boardDto
     * @return
     */
    public Board toEntity(BoardDto boardDto, User regUser){
        this.boardNo = boardDto.getBoardNo();
        this.boardTitle = boardDto.getBoardTitle();
        this.boardDetail = boardDto.getBoardDetail();
        this.regDt = LocalDateTime.now();
        this.modDt = LocalDateTime.now();
        this.regUser = regUser;

        return this;
    }
}