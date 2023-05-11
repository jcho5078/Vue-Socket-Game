package com.jcho.backapi.domain.board;

import lombok.Getter;

import javax.persistence.*;

@Entity
@Table(name = "T_BOARD")
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;
}