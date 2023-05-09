package com.jcho.backApi.web.user.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class UserDto implements Serializable {
    private final Long userId;
    private final String loginId;
    private final String loginPw;
    private final String userNm;
    private final LocalDateTime RegDt;
    private final LocalDateTime lastLoginDt;
}
