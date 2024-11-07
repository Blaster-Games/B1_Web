package org.kdr.blaster.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Log4j2
public class JWTUtil {
    private final static String key = "1234567890123456789012345678901234567890";

    public static String generateAccessToken(Long id, Map<String, Object> valueMap, int min) {
        SecretKey key = null;
        try{
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return Jwts.builder()
                .setHeader(Map.of("typ","JWT"))
                .setSubject(String.valueOf(id))
                .addClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(key)
                .compact();
    }

    public static String generateRefreshToken(Long id, int min) {
        SecretKey key = null;
        try{
            key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
        }catch(Exception e){
            throw new RuntimeException(e.getMessage());
        }
        return Jwts.builder()
                .setHeader(Map.of("typ","JWT"))
                .setSubject(String.valueOf(id))
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(key)
                .compact();
    }

    public static Claims validateToken(String token) {
        Claims claim = null;
        try{
            SecretKey key = Keys.hmacShaKeyFor(JWTUtil.key.getBytes("UTF-8"));
            claim = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();// 파싱 및 검증, 실패 시 에러

        }catch(MalformedJwtException malformedJwtException){
            throw new CustomJWTException("MalFormed", malformedJwtException);
        }catch(ExpiredJwtException expiredJwtException){
            throw new CustomJWTException("expired: " + token, expiredJwtException);
        }catch(InvalidClaimException invalidClaimException){
            throw new CustomJWTException("Invalid", invalidClaimException);
        }catch(JwtException jwtException){
            throw new CustomJWTException("JWTError", jwtException);
        }catch(Exception e){
            throw new CustomJWTException("JWTUtil.java validateToken Error", e);
        }

        return claim;
    }
}
