package com.jcho.backapi.domain.board;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    /*public Page<Board> findByBoardOrderByBoardNoDesc(Board board, Pageable pageable);

    public Board findByBoardNo(Board board);*/

    @Query(value = "SELECT bd FROM Board bd join fetch bd.regUser")
    public List<Board> findByPageable(Pageable pageable);
}