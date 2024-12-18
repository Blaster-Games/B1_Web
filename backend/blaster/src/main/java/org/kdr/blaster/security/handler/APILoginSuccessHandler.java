package org.kdr.blaster.security.handler;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.dto.member.MemberDTO;
import org.kdr.blaster.util.AuthenticationUtil;
import org.kdr.blaster.util.JWTUtil;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class APILoginSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MemberDTO memberDTO = AuthenticationUtil.getAuthenticatedMember();

        Map<String, Object> claims = memberDTO.getClaims();

        String accessToken = JWTUtil.generateAccessToken(memberDTO.getId(), claims, 10);
        String refreshToken = JWTUtil.generateRefreshToken(memberDTO.getId(), 60 * 24 * 7);

        claims.put("accessToken", accessToken);
        claims.put("refreshToken", refreshToken);
        claims.put("id", memberDTO.getId());

        Gson gson = new Gson();

        String jsonStr = gson.toJson(claims);

        response.setContentType("application/json; charset=UTF-8");

        PrintWriter printWriter = response.getWriter();
        printWriter.println(jsonStr);
        printWriter.close();
    }
}
