package org.kdr.blaster.controller;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.domain.member.Member;
import org.kdr.blaster.dto.MemberDTO;
import org.kdr.blaster.repository.MemberRepository;
import org.kdr.blaster.util.CustomJWTException;
import org.kdr.blaster.util.JWTUtil;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.time.Instant;
import java.util.Map;
import java.util.NoSuchElementException;

@RestController
@RequiredArgsConstructor
@Log4j2
public class APIRefreshController {

    private final MemberRepository memberRepository;

    @RequestMapping("/api/member/refresh")
    public Map<String, Object> refresh(
            @RequestHeader("Authorization") String authHeader,
            @RequestParam("refreshToken") String refreshToken
    ) {
        if (refreshToken == null) {
            throw new CustomJWTException("NULL_REFRESH");
        }

        if (authHeader == null || authHeader.length() < 7) {
            throw new CustomJWTException("INVALID STRING");
        }

        String accessToken = authHeader.substring(7);
        log.info(accessToken);

        if (!checkExpiredToken(accessToken)) {
            return Map.of("accessToken", accessToken, "refreshToken", refreshToken);
        }

        try {
            Claims claims = JWTUtil.validateToken(refreshToken);

            Long id = Long.valueOf(claims.getSubject());
            Member member = memberRepository.findById(id).orElseThrow(() -> new NoSuchElementException("사용자를 찾을 수 없습니다."));
            MemberDTO memberDTO = new MemberDTO(member);

            String newAccessToken = JWTUtil.generateAccessToken(Long.valueOf(claims.getSubject()), memberDTO.getClaims(), 1);
            String newRefreshToken = checkTime((Integer) claims.get("exp")) ?
                    JWTUtil.generateRefreshToken(Long.valueOf(claims.getSubject()), 60 * 24 * 7) : refreshToken;

            return Map.of("accessToken", newAccessToken, "refreshToken", newRefreshToken);
        } catch (CustomJWTException e) {
            if (e.getMessage().equals("Expired")) {
                throw new CustomJWTException("RefreshToken_Expired");
            }
            throw e;
        }
    }

    //시간이 1시간 미만으로 남았다면
    private boolean checkTime(Integer exp) {
        java.util.Date expDate = new java.util.Date((long) exp * 1000);
        long gapInMinutes = Duration.between(Instant.now(), expDate.toInstant()).toMinutes();
        return gapInMinutes < 60;
    }

    private boolean checkExpiredToken(String token) {
        try{
            JWTUtil.validateToken(token);
        }catch(CustomJWTException ex) {
            if(ex.getMessage().equals("Expired")){
                return true;
            }
        }
        return false;
    }
}
