package com.bitcamp.jackpot.jwt;

import com.bitcamp.jackpot.domain.RefreshEntity;
import com.bitcamp.jackpot.util.RedisUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import java.io.IOException;
import java.util.Date;

public interface ReissueService {
    ResponseEntity<?> reissue(HttpServletRequest request, HttpServletResponse response) throws IOException;

    default Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
        //cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }

    default void addRefreshEntity(String username, String refresh, Long expiredMs, RedisUtil redisUtil) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        redisUtil.set(username, refreshEntity, expiredMs.intValue() / (1000 * 60));  // 밀리초를 분 단위로 변환
    }
}
