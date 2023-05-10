package com.jcho.backapi.domain.board;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "T_BOARD")
@Getter
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long boardId;
}