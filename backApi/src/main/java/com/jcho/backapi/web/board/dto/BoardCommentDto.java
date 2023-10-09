package com.jcho.backapi.web.board.dto;

import com.jcho.backapi.domain.board.BoardComment;
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
public class BoardCommentDto implements Serializable {
    private long boardNo;
    private long commentNo;
    private String commentDetail;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
    private Long regUser;
    private String regUserNm;

    private boolean newComment;

    public static BoardCommentDto toDto(BoardComment comment){
        return BoardCommentDto.builder()
                .boardNo(comment.getBoardNo())
                .commentNo(comment.getCommentNo())
                .commentDetail(comment.getCommentDetail())
                .regDt(comment.getRegDt())
                .modDt(comment.getModDt())
                .regUser(comment.getRegUser().getUserNo())
                .regUserNm(comment.getRegUser().getUserNm())
                .build();
    }
}