package com.jcho.backapi.web.board.controller;

import com.jcho.backapi.common.CommonCode;
import com.jcho.backapi.util.JwtUtil;
import com.jcho.backapi.web.board.dto.BoardDto;
import com.jcho.backapi.web.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/list")
    public List<BoardDto> getBoards(final Pageable pageable) throws Exception{

        List<BoardDto> result = boardService.getBoardList(pageable);

        return result;
    }

    @GetMapping("/{boardNo}")
    public ResponseEntity<BoardDto> getBoard(@PathVariable String boardNo) throws Exception{

        BoardDto result = boardService.getBoardOne(Long.parseLong(boardNo));

        return ResponseEntity.ok(result);
    }

    @PostMapping("/write")
    public ResponseEntity<BoardDto> writeBoard(HttpServletRequest request, @RequestBody BoardDto boardDto) throws Exception{
        long userId = JwtUtil.getUserIdFromToken(request);

        BoardDto result = boardService.writeBoard(boardDto, userId);

        return ResponseEntity.ok(result);
    }

    @PostMapping("/delete")
    public ResponseEntity<Integer> deleteBoard(HttpServletRequest request, @RequestBody BoardDto boardDto) throws Exception{
        long userId = JwtUtil.getUserIdFromToken(request);

        boolean result = boardService.deleteBoard(boardDto, userId);

        return ResponseEntity.ok(result?1:0);
    }
}
