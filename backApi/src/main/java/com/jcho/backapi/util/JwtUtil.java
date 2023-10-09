package com.jcho.backapi.util;

import com.jcho.backapi.BackApiApplication;
import com.jcho.backapi.common.CommonCode;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class JwtUtil {

    private static Logger logger = BackApiApplication.logger;

    /**
     * 토큰 내 유저정보확인
     * @param token
     * @return
     */
    public static String extractUserId(String token) {
        token = getReplaceToken(token);
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * 토큰 파기 확인
     * @param token
     * @return
     */
    public static Date extractExpiration(String token) {
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
    public static <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
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
    private static Boolean isTokenExpired(String token) {
        token = getReplaceToken(token);
        return extractExpiration(token).before(new Date());
    }

    /**
     * 복호화 키를 담은 토큰 생성
     * @param userId
     * @return
     */
    public static String generateToken(String userId) {
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
    public static boolean validateToken(String jwtToken) throws Exception {
        jwtToken = getReplaceToken(jwtToken);
        Claims claims = null;
        try {
            claims = Jwts.parser().setSigningKey(CommonCode.JWT_KEY.getBytes()).parseClaimsJws(jwtToken).getBody();
            logger.info(String.format("userId : %s ", extractUserId(jwtToken)));
        }catch (SignatureException | MalformedJwtException e) {
            throw e;
        } catch (ExpiredJwtException e) {
            // 유효기간 지남
            throw e;
        }catch (Exception e){
            throw e;
        }
        return !claims.getExpiration().before(new Date());
    }

    public static String getReplaceToken(String jwtToken){
        return jwtToken.replaceAll(CommonCode.TOKEN_PREFIX, "");
    }

    /**
     * 헤더 토큰으로부터 userId 반환
     * @param request
     * @return
     * @throws Exception
     */
    public static Long getUserIdFromToken(HttpServletRequest request) throws Exception{
        String token = request.getHeader(CommonCode.JWT_HEADER);
        String userId = null;
        try {
            if(token != null
                    && token.startsWith(CommonCode.TOKEN_PREFIX)
                    && validateToken(token)) {

                userId = extractUserId(token);
            }
        } catch (ExpiredJwtException e) {
            throw e;
        } catch (Exception e){
            throw e;
        }

        return Long.parseLong(userId);
    }
}