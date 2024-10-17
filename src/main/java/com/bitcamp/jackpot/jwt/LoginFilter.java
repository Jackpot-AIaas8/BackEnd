package com.bitcamp.jackpot.jwt;

import com.bitcamp.jackpot.domain.RefreshEntity;
import com.bitcamp.jackpot.dto.SignInDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.*;


public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final RedisUtil redisUtil;

    private final ObjectMapper objectMapper = new ObjectMapper(); // ObjectMapper 추가


    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil, RedisUtil redisUtil) {

        this.jwtUtil = jwtUtil;
        this.redisUtil = redisUtil;
        this.authenticationManager = authenticationManager;
        setFilterProcessesUrl("/member/signIn"); // 요청 처리 URL 설정
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) {
        try {
            SignInDTO signInDTO = objectMapper.readValue(request.getInputStream(), SignInDTO.class);
            String email = signInDTO.getEmail();
            String pwd = signInDTO.getPwd();


            UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(email, pwd, null);

            //어덴티케이션 매니저에 검증을위해 토큰위임
            return authenticationManager.authenticate(authToken);
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed: " + e.getMessage());
        }
    }

    //로그인 성공시 실행하는 jwt 발급 메소드
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {


        //유저 정보
        String username = authentication.getName();

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
        GrantedAuthority auth = iterator.next();
        String role = auth.getAuthority();
        System.out.println(role);
        //토큰 생성
        String access = jwtUtil.createJwt("access", username, role, 600000L);
        String refresh = jwtUtil.createJwt("refresh", username, role, 86400000L);
        //refresh 토큰 저장
        addRefreshEntity(username, refresh, 86400000L);
        //응답 설정

        response.addCookie(createCookie("refresh", refresh));

        try {
            // JSON 응답을 작성할 때 ObjectMapper 사용
            ObjectMapper objectMapper = new ObjectMapper();

            // 응답 객체에 넣을 데이터
            Map<String, String> tokenResponse = new HashMap<>();
            tokenResponse.put("access", access);
            // JSON 응답 설정
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // 응답 바디에 토큰을 작성
            objectMapper.writeValue(response.getWriter(), tokenResponse);

            response.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            throw new RuntimeException("Error while writing the response: " + e.getMessage());
        }

    }

    //실패시 실행메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

    private void addRefreshEntity(String username, String refresh, Long expiredMs) {

        Date date = new Date(System.currentTimeMillis() + expiredMs);

        RefreshEntity refreshEntity = new RefreshEntity();
        refreshEntity.setUsername(username);
        refreshEntity.setRefresh(refresh);
        refreshEntity.setExpiration(date.toString());

        // Redis에 저장 (키는 username, 값은 refreshEntity 객체, 만료 시간은 밀리초 단위로 설정)
        redisUtil.set(username, refreshEntity, expiredMs.intValue() / (1000 * 60));  // 밀리초를 분 단위로 변환
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(24 * 60 * 60);
//        cookie.setSecure(true);
        cookie.setPath("/");
        cookie.setHttpOnly(true); // 클라에서 접근을막아 XSS 방지
        return cookie;
    }
}