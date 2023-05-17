package com.jcho.backapi.web.board.dto;

import com.jcho.backapi.domain.board.Board;
import com.jcho.backapi.domain.user.User;
import lombok.Builder;
import lombok.Data;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class BoardDto implements Serializable {
    private long boardId;
    private String boardTitle;
    private String boardDetail;
    private LocalDateTime regDt;
    private LocalDateTime modDt;
    private User regUser;
    private User modUser;

    private String jwtToken;

    public static BoardDto toBoard(Board board){
        return BoardDto.builder()
                .boardId(board.getBoardId())
                .boardTitle(board.getBoardTitle())
                .boardDetail(board.getBoardDetail())
                .regDt(board.getRegDt())
                .modDt(board.getModDt())
                .regUser(board.getRegUser())
                .modUser(board.getModUser())
                .build();
    }
}