package org.kdr.blaster.security.filter;

import com.google.gson.Gson;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.kdr.blaster.domain.member.UserRole;
import org.kdr.blaster.dto.MemberDTO;
import org.kdr.blaster.util.JWTUtil;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

@Log4j2
public class JWTCheckFilter extends OncePerRequestFilter {

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        String path = request.getRequestURI();

        if (path.startsWith("/api/member/login")) {
            return true;
        }
        if (path.startsWith("/api/member/register")) {
            return true;
        }
        if (path.startsWith("/api/member/refresh")) {
            return true;
        }

        return false;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeaderStr = request.getHeader("Authorization");

        try {
            String accessToken = authHeaderStr.substring(7);
            Claims claims = JWTUtil.validateToken(accessToken);

            Long id = Long.valueOf(claims.getSubject());
            String email = (String) claims.get("email");
            UserRole userRole = UserRole.valueOf((String) claims.get("userRole"));
            String nickname = (String) claims.get("nickname");

            MemberDTO memberDTO = new MemberDTO(email, "", userRole, id, nickname);

            UsernamePasswordAuthenticationToken authenticationToken
                    = new UsernamePasswordAuthenticationToken(memberDTO,null, memberDTO.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            filterChain.doFilter(request, response);

        }catch(Exception e){

            Gson gson = new Gson();
            String msg = gson.toJson(Map.of("JWTCheckFilter.java error", e.getMessage()));

            response.setContentType("application/json");
            PrintWriter printWriter = response.getWriter();
            printWriter.println(msg);
            printWriter.close();
        }
    }
}
