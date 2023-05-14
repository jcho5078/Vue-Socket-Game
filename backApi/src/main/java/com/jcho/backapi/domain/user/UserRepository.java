package com.jcho.backapi.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    public User findUsersByLoginIdAndLoginPw(String loginId, String loginPw);

    public User findByLoginId(String loginId);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END " +
            "FROM User u " +
            "WHERE u.loginId = :loginId")
    public boolean existsLoginId(@Param("loginId") String loginId);

    @Query("SELECT CASE WHEN COUNT(u) > 0 THEN TRUE ELSE FALSE END " +
            "FROM User u " +
            "WHERE u.userNm = :userNm")
    public boolean existsUserNm(@Param("userNm") String userNm);
}