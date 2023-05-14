package com.jcho.backapi.util;

import com.jcho.backapi.common.CommonCode;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtUtil {

    /**
     * 토큰 내 유저정보확인
     * @param token
     * @return
     */
    public String extractUserId(String token) {
        token = getReplaceToken(token);
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 토큰 파기 확인
     * @param token
     * @return
     */
    public Date extractExpiration(String token) {
        token = getReplaceToken(token);
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * 토큰정보 추출
     * claimResolver 추출 형식 명시
     * @param token
     * @param claimsResolver
     * @param <T>
     * @return
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        token = getReplaceToken(token);
        final Claims claims = Jwts.parser().setSigningKey(CommonCode.JWT_KEY.getBytes()).parseClaimsJws(token).getBody();
        String val = claims.toString();//test
        return claimsResolver.apply(claims);
    }

    /**
     * 토큰만료 확인
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        token = getReplaceToken(token);
        return extractExpiration(token).before(new Date());
    }

    /**
     * 복호화 키를 담은 토큰 생성
     * @param userId
     * @return
     */
    public String generateToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);
        Key key = Keys.hmacShaKeyFor(CommonCode.JWT_KEY.getBytes(StandardCharsets.UTF_8));

        String jwt = Jwts.builder().setClaims(claims).setSubject(userId).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10)) // 1시간 유지
                .signWith(SignatureAlgorithm.HS256, key).compact();

        return jwt;
    }

    /**
     * 토큰 검증
     * @param jwtToken
     * @return
     */
    public boolean validateToken(String jwtToken) {
        jwtToken = getReplaceToken(jwtToken);
        Claims claims = Jwts.parser().setSigningKey(CommonCode.JWT_KEY.getBytes()).parseClaimsJws(jwtToken).getBody();
        return !claims.getExpiration().before(new Date());
    }

    public String getReplaceToken(String jwtToken){
        return jwtToken.replaceAll(CommonCode.TOKEN_PREFIX, "");
    }
}