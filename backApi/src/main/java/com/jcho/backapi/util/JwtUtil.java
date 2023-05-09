package com.jcho.backapi.util;

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
     * jwt생성 키
     */
    @Value("${jwt.key}")
    private String jwtKey;

    /**
     * jwt 헤더 조회
     */
    @Value("${jwt.header}")
    public String jwtHeader;

    /**
     * 토큰 내 유저정보확인
     * @param token
     * @return
     */
    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 토큰 파기 확인
     * @param token
     * @return
     */
    public Date extractExpiration(String token) {
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
        final Claims claims = Jwts.parser().setSigningKey(jwtKey.getBytes()).parseClaimsJws(token).getBody();
        String val = claims.toString();//test
        return claimsResolver.apply(claims);
    }

    /**
     * 토큰
     * @param token
     * @return
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * 복호화 키를 담은 토큰 생성
     * @param userId
     * @return
     */
    public String generateToken(String userId) {
        Claims claims = Jwts.claims().setSubject(userId);
        Key key = Keys.hmacShaKeyFor(jwtKey.getBytes(StandardCharsets.UTF_8));

        String jwt = Jwts.builder().setClaims(claims).setSubject(userId).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, key).compact();

        return jwt;
    }

    /**
     * 토큰 검증
     * @param jwtToken
     * @return
     */
    public boolean validateToken(String jwtToken) {
        Claims claims = Jwts.parser().setSigningKey(jwtKey.getBytes()).parseClaimsJws(jwtToken).getBody();
        return !claims.getExpiration().before(new Date());
    }
}