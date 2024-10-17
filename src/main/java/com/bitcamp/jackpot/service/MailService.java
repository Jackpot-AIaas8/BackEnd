package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.dto.VerificationCodeDTO;
import com.bitcamp.jackpot.util.RedisUtil;
import com.bitcamp.jackpot.repository.MemberRepository;
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
    private final MemberRepository memberRepository;


    private String createCode() throws NoSuchAlgorithmException {
        int length = 6;

            Random random = SecureRandom.getInstanceStrong();
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < length; i++) {
                builder.append(random.nextInt(10));
            }
            return builder.toString();
    }
    public boolean checkVerificationCode(VerificationCodeDTO verificationCodeDTO) {

        String name = verificationCodeDTO.getName();
        String code = verificationCodeDTO.getCode();
        String email = verificationCodeDTO.getEmail();

        try {
            // 사용자 존재 여부를 먼저 확인하고, 없으면 바로 false 반환
            if (!memberRepository.existsByNameAndEmail(name, email)) {
                return false;
            }

            // Redis에서 인증 코드 비교, 일치하지 않으면 false 반환
            if (!redisUtil.isValueEqual(email, code)) {
                return false;
            }

            // 인증 완료 시 Redis에서 해당 키 삭제 후 true 반환
            redisUtil.delete(email);
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }





    public void sendSimpleMailMessage(String email) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        try {
            // 메일을 받을 수신자 설정         이 과정에서 db 에 들어가서 무결성을 확인할 필요가 있음
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
