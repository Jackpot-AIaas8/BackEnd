package com.bitcamp.jackpot.controller;

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

    @GetMapping("/checkVerificationCode")
    public ResponseEntity<String> checkVerificationCode(@RequestParam String email, @RequestParam String code) {
        boolean isVerified = mailService.checkVerificationCode(email, code);

        if (isVerified) {
            return ResponseEntity.ok("인증 되었습니다.");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("인증코드가 잘못 되었습니다.");
        }
    }

}
