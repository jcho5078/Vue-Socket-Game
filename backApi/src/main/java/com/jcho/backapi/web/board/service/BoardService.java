package com.jcho.backapi.web.board.service;

import com.jcho.backapi.web.board.dto.BoardDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardService {

    public Page<BoardDto> getBoardList(BoardDto boardDto, Pageable pageable) throws Exception;

    public BoardDto getBoardOne(BoardDto boardDto) throws Exception;

    public int writeBoardList(BoardDto boardDto) throws Exception;

    public int deleteBoardList(BoardDto boardDto) throws Exception;
}
