package com.jcho.backapi.domain.board;

import com.jcho.backapi.domain.board.cpKey.BoardCommentKey;
import com.jcho.backapi.domain.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardCommentRepository extends JpaRepository<BoardComment, BoardCommentKey> {

    @Query("SELECT c, u " +
            "FROM BoardComment c INNER JOIN c.regUser u " +
            "WHERE c.boardNo = :boardNo " +
            "ORDER BY c.commentNo")
    public List<BoardComment> getCommentsByBoardNo(@Param("boardNo") Long boardNo);

    @Query("SELECT c, u " +
            "FROM BoardComment c INNER JOIN c.regUser u " +
            "WHERE c.boardNo = :boardNo " +
            "   AND c.commentNo = :commentNo ")
    public List<BoardComment> findByBoardNoAndCommentNo(@Param("boardNo") Long boardNo, @Param("commentNo") Long commentNo);

    public List<BoardComment> findByRegUser(User user);
}