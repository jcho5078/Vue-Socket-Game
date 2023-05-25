package com.jcho.backapi.web.board.dto;

import com.jcho.backapi.domain.board.Board;
import com.jcho.backapi.domain.user.User;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BoardDto implements Serializable {
    private long boardNo;
    private String boardTitle;
    private String boardDetail;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
    private Long regUser;
    private String regUserNm;

    public static BoardDto toDto(Board board){
        return BoardDto.builder()
                .boardNo(board.getBoardNo())
                .boardTitle(board.getBoardTitle())
                .boardDetail(board.getBoardDetail())
                .regDt(board.getRegDt())
                .modDt(board.getModDt())
                .regUser(board.getRegUser().getUserNo())
                .build();
    }
}