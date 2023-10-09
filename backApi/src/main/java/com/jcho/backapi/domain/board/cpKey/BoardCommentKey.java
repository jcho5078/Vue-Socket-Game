package com.jcho.backapi.domain.board.cpKey;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@EqualsAndHashCode
public class BoardCommentKey implements Serializable {
    @EqualsAndHashCode.Include
    @Id
    private Long boardNo;

    @EqualsAndHashCode.Include
    @Id
    private Long commentNo;
}
