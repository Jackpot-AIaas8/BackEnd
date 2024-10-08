package com.bitcamp.jackpot.jwt;

import com.bitcamp.jackpot.jwt.JWTUtil;
import com.bitcamp.jackpot.repository.RefreshRepository;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LogoutService {

    private final RefreshRepository refreshRepository;
    private final JWTUtil jwtUtil;

    public void logout(HttpServletRequest request, HttpServletResponse response) {

        // get refresh token
        String refresh = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("refresh")) {
                    refresh = cookie.getValue();
                }
            }
        }

        if (refresh == null) {
            throw new IllegalArgumentException("Refresh token is missing.");
        }

        // Token validation
        try {
            jwtUtil.isExpired(refresh);
        } catch (ExpiredJwtException e) {
            throw new IllegalArgumentException("Refresh token has expired.");
        }
        // Verify token category
        String category = jwtUtil.getCategory(refresh);
        if (!category.equals("refresh")) {
            throw new IllegalArgumentException("Invalid token category.");
        }

        // DB에서 토큰 존재 여부 확인
        if (!refreshRepository.existsByRefresh(refresh)) {
            throw new IllegalArgumentException("Refresh token not found.");
        }

        // 토큰 삭제 (DB 및 쿠키)
        refreshRepository.deleteByRefresh(refresh);
        Cookie cookie = new Cookie("refresh", null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
//        cookie.setSecure(true);
        cookie.setHttpOnly(true);

        response.addCookie(cookie);
    }
}
