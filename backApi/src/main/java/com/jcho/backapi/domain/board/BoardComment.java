package com.jcho.backapi.domain.board;

import com.jcho.backapi.domain.board.cpKey.BoardCommentKey;
import com.jcho.backapi.domain.user.User;
import com.jcho.backapi.web.board.dto.BoardCommentDto;
import com.jcho.backapi.web.board.dto.BoardDto;
import lombok.Getter;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "T_BOARD_COMMENT")
@Getter
@IdClass(BoardCommentKey.class)
public class BoardComment {

    @Id
    @Column(name = "BOARD_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardNo;

    @Id
    @Column(name = "COMMENT_NO")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long commentNo;

    @Comment("댓글 내용")
    @Lob
    @Column(name = "COMMENT_DETAIL", length = 500)
    private String commentDetail;

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
     * convert board comment entity
     * @param boardCommentDto
     * @return
     */
    public BoardComment toEntity(BoardCommentDto boardCommentDto, User regUser){
        this.boardNo = boardCommentDto.getBoardNo();
        this.commentNo = boardCommentDto.getCommentNo();
        this.commentDetail = boardCommentDto.getCommentDetail();
        this.regDt = LocalDateTime.now();
        this.modDt = LocalDateTime.now();
        this.regUser = regUser;

        return this;
    }
}