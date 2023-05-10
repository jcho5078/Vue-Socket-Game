package com.jcho.backapi.domain.user;

import com.jcho.backapi.util.BooleanConverter;
import com.jcho.backapi.web.user.dto.UserDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.Comment;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Table(name = "T_USER")
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long userId;

    @Comment("로그인 ID")
    @Column(name = "LOGIN_ID", length = 50, unique = true)
    private String loginId;

    @Comment("로그인 PW")
    @Column(name = "LOGIN_PW", length = 100)
    private String loginPw;

    @Comment("사용자 이름")
    @Column(name = "USER_NM", length = 150, unique = true)
    private String userNm;

    @Comment("가입일")
    @Column(name = "REG_DT", length = 30)
    @CreatedDate
    private LocalDateTime regDt;

    @Comment("이전 로그인 날짜")
    @Column(name = "LAST_LOGIN_DT", length = 30)
    @CreatedDate
    private LocalDateTime lastLoginDt;

    @Comment("유효여부")
    @Column(name = "IS_VALID", length = 5)
    @Convert(converter = BooleanConverter.class)
    @ColumnDefault(value = "'N'")
    private boolean isValid;

    /**
     * lastLoginDt 갱신
     * @param currentDateTime
     */
    public void updateLogin(LocalDateTime currentDateTime){
        this.lastLoginDt = currentDateTime;
    }

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