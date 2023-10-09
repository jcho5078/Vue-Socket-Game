package com.jcho.backapi.web.board.service.impl;

import com.jcho.backapi.domain.board.Board;
import com.jcho.backapi.domain.board.BoardComment;
import com.jcho.backapi.domain.board.BoardCommentRepository;
import com.jcho.backapi.domain.board.BoardRepository;
import com.jcho.backapi.domain.board.cpKey.BoardCommentKey;
import com.jcho.backapi.domain.user.User;
import com.jcho.backapi.domain.user.UserRepository;
import com.jcho.backapi.web.board.dto.BoardCommentDto;
import com.jcho.backapi.web.board.dto.BoardDto;
import com.jcho.backapi.web.board.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BoardServiceImpl implements BoardService {

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private BoardCommentRepository boardCommentRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<BoardDto> getBoardList(Pageable pageable) throws Exception {
        List<Board> boards = boardRepository.findByPageable(pageable);
        List<BoardDto> result = boards.stream().map(BoardDto::toDto).collect(Collectors.toList());

        result = result.stream().map(dto -> {
            dto.setRegUserNm(userRepository.findById(dto.getRegUser()).get().getUserNm());
            return dto;
        }).collect(Collectors.toList());

        return result;
    }

    @Override
    public BoardDto getBoardOne(long boardNo) throws Exception {
        BoardDto boardDto = BoardDto.builder()
                .boardNo(boardNo)
                .build();

        boardDto = boardRepository.findById(boardDto.getBoardNo()).map(BoardDto::toDto).get();
        boardDto.setRegUserNm(userRepository.findById(boardDto.getRegUser()).get().getUserNm());

        return boardRepository.findById(boardDto.getBoardNo()).map(BoardDto::toDto).get();
    }

    @Override
    public BoardDto writeBoard(BoardDto boardDto, Long regUserId) throws Exception {
        User regUser = userRepository.findById(regUserId).get();
        Board board = new Board().toEntity(boardDto, regUser);

        boardRepository.save(board);
        return BoardDto.toDto(board);
    }

    @Override
    public boolean deleteBoard(BoardDto boardDto, long userId) throws Exception {
        if(boardDto.getBoardNo() != 0){
            Board board = boardRepository.findById(boardDto.getBoardNo()).get();
            User regUser = userRepository.findById(userId).get();

            if(checkBoardUser(board, regUser)){
                boardRepository.delete(board);
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }

    /**
     * 게시물 댓글 가져오기
     * @param boardNo
     * @return
     * @throws Exception
     */
    @Override
    public List<BoardCommentDto> getBoardCommentList(long boardNo) throws Exception {
        List<BoardCommentDto> result = boardCommentRepository.getCommentsByBoardNo(boardNo).stream()
                .map(BoardCommentDto::toDto).collect(Collectors.toList());
        return result;
    }

    /**
     * 게시물 댓글 생성
     * @param boardCommentDto
     * @param regUserId
     * @return
     * @throws Exception
     */
    @Override
    public BoardCommentDto writeBoardComment(BoardCommentDto boardCommentDto, Long regUserId) throws Exception {
        User regUser = userRepository.findById(regUserId).get();
        List<BoardComment> comments = boardCommentRepository.getCommentsByBoardNo(boardCommentDto.getBoardNo());

        if(boardCommentDto.isNewComment() && comments.size() != 0){
            long maxCommentNo = Collections.max(comments
                    , Comparator.comparingLong(BoardComment::getCommentNo)).getCommentNo();

            boardCommentDto.setCommentNo(maxCommentNo + 1);
        }
        BoardComment boardComment = new BoardComment().toEntity(boardCommentDto, regUser);
        boardCommentRepository.save(boardComment);
        return BoardCommentDto.toDto(boardComment);
    }

    /**
     * 게시물 댓글 삭제
     * @param boardCommentDto
     * @param userId
     * @return
     * @throws Exception
     */
    @Override
    public boolean deleteBoardComment(BoardCommentDto boardCommentDto, long userId) throws Exception {
        List<BoardComment> list = boardCommentRepository
                .findByBoardNoAndCommentNo(boardCommentDto.getBoardNo(), boardCommentDto.getCommentNo());
        BoardComment boardComment = list.get(0);
        User regUser = userRepository.findById(userId).get();

        if(checkBoardCommentUser(boardComment, regUser)){
            boardCommentRepository.delete(boardComment);
            return true;
        }else{
            return false;
        }
    }

    /**
     * 게시물 작성자인지 체크
     * @param board
     * @param currentUser
     * @return
     */
    private boolean checkBoardUser(Board board, User currentUser){
        User regUser = board.getRegUser();

        if(regUser.getUserNo().equals(currentUser.getUserNo())){
            return true;
        }else{
            return false;
        }
    }

    /**
     * 댓글 작성자인지 체크
     * @param boardComment
     * @param currentUser
     * @return
     */
    private boolean checkBoardCommentUser(BoardComment boardComment, User currentUser){
        User regUser = boardComment.getRegUser();

        if(regUser.getUserNo().equals(currentUser.getUserNo())){
            return true;
        }else{
            return false;
        }
    }
}
