package com.jcho.backapi.web.user.dto;

import com.jcho.backapi.domain.user.User;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
public class UserDto implements Serializable {
    private Long userNo;
    private String loginId;
    private String loginPw;
    private String userNm;
    private LocalDateTime regDt;
    private LocalDateTime lastLoginDt;
    private boolean isValid;

    private String jwtToken;

    public static UserDto toDto(User user){
        return UserDto.builder()
                .userNo(user.getUserNo())
                .loginId(user.getLoginId())
                .loginPw(user.getLoginPw())
                .userNm(user.getUserNm())
                .regDt(user.getRegDt())
                .lastLoginDt(user.getLastLoginDt())
                .isValid(user.isValid() == true ? true : false)
                .build();
    }
}