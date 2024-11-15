package org.kdr.blaster.util;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import lombok.extern.log4j.Log4j2;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Map;

@Log4j2
public class JWTUtil {

    private final static String key = "1234567890123456789012345678901234567890";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));

    public static String generateAccessToken(Long id, Map<String, Object> valueMap, int min) {
        return Jwts.builder()
                .setHeader(Map.of("typ","JWT"))
                .setSubject(String.valueOf(id))
                .addClaims(valueMap)
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static String generateRefreshToken(Long id, int min) {
        return Jwts.builder()
                .setHeader(Map.of("typ","JWT"))
                .setSubject(String.valueOf(id))
                .setIssuedAt(Date.from(ZonedDateTime.now().toInstant()))
                .setExpiration(Date.from(ZonedDateTime.now().plusMinutes(min).toInstant()))
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Claims validateToken(String token) {
        try{
            return Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();// 파싱 및 검증, 실패 시 에러
        }catch(MalformedJwtException malformedJwtException){
            throw new CustomJWTException("MalFormed");
        }catch(ExpiredJwtException expiredJwtException){
            throw new CustomJWTException("Expired");
        }catch(InvalidClaimException invalidClaimException){
            throw new CustomJWTException("Invalid");
        }catch(JwtException jwtException){
            throw new CustomJWTException("JWTError");
        }catch(Exception e){
            throw new CustomJWTException("JWTUtil.java validateToken Error");
        }
    }
}
