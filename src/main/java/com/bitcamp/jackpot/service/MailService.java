package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.dto.MailDTO;
import com.bitcamp.jackpot.jwt.RedisUtil;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;


@Service
@Slf4j
@RequiredArgsConstructor
public class MailService {

    private final JavaMailSender javaMailSender;
    private final RedisUtil redisUtil;


    private String createCode() throws NoSuchAlgorithmException {
        int length = 6;

            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
    }
    public boolean checkVerificationCode(String email, String code) {
        try {

            if (redisUtil.isValueEqual(email,code)){
                redisUtil.delete(email);
                return true;
            }else return false;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    public void sendSimpleMailMessage(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        try {
            // 메일을 받을 수신자 설정
            simpleMailMessage.setTo(email);
            // 메일의 제목 설정
            simpleMailMessage.setSubject("ppyppy 인증번호");
            // 메일의 내용 설정
            String secretCode=createCode();
            simpleMailMessage.setText("인증번호 : "+secretCode);

            javaMailSender.send(simpleMailMessage);
            redisUtil.set(email,secretCode,3);
            
            log.info("메일 발송 성공!");
        } catch (Exception e) {
            log.info("메일 발송 실패!");
            throw new RuntimeException(e);
        }

    }
}