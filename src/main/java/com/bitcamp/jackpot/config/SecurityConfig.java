package com.bitcamp.jackpot.config;

import com.bitcamp.jackpot.jwt.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
//WebSecurityConfiguration < 이게 WebSecurityconfigererAdpater 의 상위 버전. 어댑터이건 사용중단
public class SecurityConfig {
    private final JWTUtil jwtUtil;
    private final AuthenticationConfiguration authenticationConfiguration;
    private final RedisUtil redisUtil;

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {

        return new BCryptPasswordEncoder();
    }

    //어덴티케이션매니저 빈등록
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http, LogoutService logoutService) throws Exception {

        http
                .cors((cors) -> cors
                        .configurationSource(new CorsConfigurationSource() {
                            @Override
                            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                                CorsConfiguration configuration = new CorsConfiguration();
                                //개발중 스웨거 활용을 위해 주석 처리함
//                              configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
                                configuration.setAllowedOrigins(Collections.singletonList("*"));  // 모든 출처 허용


                                configuration.setAllowedMethods(Collections.singletonList("*"));
                                configuration.setAllowCredentials(true);
                                configuration.setAllowedHeaders(Collections.singletonList("*"));
                                configuration.setMaxAge(3600L);
                                configuration.setExposedHeaders(Collections.singletonList("Authorization"));

                                return configuration;
                            }


                        }));

        //csrf disable
        http
                .csrf(AbstractHttpConfigurer::disable);

        //From 로그인 방식 disable
        http
                .formLogin(AbstractHttpConfigurer::disable);

        //http basic 인증 방식 disable
        http
                .httpBasic(AbstractHttpConfigurer::disable);

        //경로별 인가 설정 작업
        http
                .authorizeHttpRequests((auth) -> auth
                        //로그인 인증이 필요없는 요청
                        .requestMatchers(
                                "/member/signIn",
                                "/",
                                "/member/signUp",
                                "/board/findAll",
                                "/board/findAllAsk",
                                "/member/checkEmail",
                                "/member/checkNickName",
                                "/reissue",
                                "/shop/findList",
                                "/shop/findOne/**",
                                "/shop/category/**",

                                "/shop/search",
                                "/swagger-ui/**", "/v3/api-docs/**",
                                "/dog/**"

                        ).permitAll()
                        .requestMatchers("/admin/*").hasRole("ADMIN")


                        .anyRequest().authenticated());
        //JWT토큰필터
        http
                .addFilterBefore(new JWTFilter(jwtUtil, redisUtil), LoginFilter.class);
        http                  //커스텀한 로그인 필터를 세션 생성에 앞서 필터링하게끔 추가
                .addFilterAt(new LoginFilter(authenticationManager(authenticationConfiguration), jwtUtil, redisUtil), UsernamePasswordAuthenticationFilter.class);
        http
                .addFilterBefore(new CustomLogoutFilter(logoutService), LogoutFilter.class);
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));


        return http.build();
    }
}

