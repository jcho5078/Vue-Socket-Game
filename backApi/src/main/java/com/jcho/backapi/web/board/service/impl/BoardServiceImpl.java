package com.jcho.backapi.web.board.service.impl;

import com.jcho.backapi.domain.board.Board;
import com.jcho.backapi.domain.board.BoardRepository;
import com.jcho.backapi.web.board.dto.BoardDto;
import com.jcho.backapi.web.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Override
    public Page<BoardDto> getBoardList(BoardDto boardDto, Pageable pageable) throws Exception {
        return boardRepository.findByBoardOrderByBoardIdDesc(new Board().toEntity(boardDto), pageable).map(BoardDto::toBoard);
    }

    @Override
    public BoardDto getBoardOne(BoardDto boardDto) throws Exception {
        return null;
    }

    @Override
    public int writeBoardList(BoardDto boardDto) throws Exception {
        return 0;
    }

    @Override
    public int deleteBoardList(BoardDto boardDto) throws Exception {
        return 0;
    }
}
