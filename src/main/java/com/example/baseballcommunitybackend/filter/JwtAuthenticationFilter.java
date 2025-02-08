package com.example.baseballcommunitybackend.filter;

import com.example.baseballcommunitybackend.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtUtil jwtUtil;
    private final UserDetailsService userDetailsService;
    private static final String TOKEN_PREFIX = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Authorization 헤더 가져오기
        String authHeader = request.getHeader("Authorization");
        System.out.println("authHeader : " + authHeader);

        // 헤더가 존재하고 "Bearer "로 시작하는지 확인
        if (authHeader == null || !authHeader.startsWith(TOKEN_PREFIX)) {
            filterChain.doFilter(request, response);
            return;
        }

        // "Bearer " 접두사 제거 후 JWT 추출
        String token = authHeader.substring(TOKEN_PREFIX.length());

        // 토큰 검증
        if (jwtUtil.validateToken(token)) {
            String userId = jwtUtil.extractUserId(token);

            // DB에서 사용자 정보 가져오기 (UserDetails 필요)
            UserDetails userDetails = userDetailsService.loadUserByUsername(userId);

            // SecurityContext에 인증 정보 저장
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }
}