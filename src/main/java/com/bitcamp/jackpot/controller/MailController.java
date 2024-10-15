package com.bitcamp.jackpot.controller;

import com.bitcamp.jackpot.dto.VerificationCodeDTO;
import com.bitcamp.jackpot.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;


    @GetMapping("/sendEmail")
    public void sendSimpleMailMessage(@RequestParam String email) {
        mailService.sendSimpleMailMessage(email);

    }
    @PostMapping("/checkVerificationCode")
    public ResponseEntity<String> checkVerificationCode(@RequestBody VerificationCodeDTO verificationCodeDTO) {
        boolean isVerified = mailService.checkVerificationCode(verificationCodeDTO);
        // 인증 성공
        if (isVerified) {
            return ResponseEntity.ok("인증 성공");
        }
        // 인증 실패
        else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("인증 정보가 일치하지 않습니다.");
        }
    }


}
