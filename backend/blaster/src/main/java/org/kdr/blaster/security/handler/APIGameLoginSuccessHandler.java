package org.kdr.blaster.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.domain.member.Member;
import org.kdr.blaster.dto.MemberDTO;
import org.kdr.blaster.repository.MemberRepository;
import org.kdr.blaster.util.AuthenticationUtil;
import org.kdr.blaster.util.JWTUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class APIGameLoginSuccessHandler implements AuthenticationSuccessHandler {

    private final MemberRepository memberRepository;

    public APIGameLoginSuccessHandler(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MemberDTO memberDTO = AuthenticationUtil.getAuthenticatedMember();

        Map<String, Object> claims = memberDTO.getClaims();

        String accessToken = JWTUtil.generateAccessToken(memberDTO.getId(), claims, 60 * 24);
        String refreshToken = JWTUtil.generateRefreshToken(memberDTO.getId(), 60 * 24);

        claims.put("accessToken", accessToken);
        claims.put("refreshToken", refreshToken);
        claims.put("id", memberDTO.getId());

        Gson gson = new Gson();

        String jsonStr = gson.toJson(claims);

        response.setContentType("application/json; charset=UTF-8");

        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();

        Member member = memberRepository.findByEmail(memberDTO.getEmail())
                .orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다: " + memberDTO.getEmail()));

        member.onLogin();
        memberRepository.save(member);
    }
}
