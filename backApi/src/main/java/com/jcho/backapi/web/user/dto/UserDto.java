package com.jcho.backapi.web.user.dto;

import com.jcho.backapi.domain.user.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class UserDto implements Serializable {
    private final Long userId;
    private final String loginId;
    private final String loginPw;
    private final String userNm;
    private final LocalDateTime regDt;
    private final LocalDateTime lastLoginDt;
    private final boolean isValid;

    public static UserDto toDto(User user){
        return UserDto.builder()
                .loginId(user.getLoginId())
                .userNm(user.getUserNm())
                .regDt(user.getRegDt())
                .lastLoginDt(user.getLastLoginDt())
                .isValid(user.isValid())
                .build();
    }
}