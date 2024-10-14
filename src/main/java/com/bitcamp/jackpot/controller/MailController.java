package com.bitcamp.jackpot.controller;

import com.bitcamp.jackpot.dto.MailDTO;
import com.bitcamp.jackpot.service.MailService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
public class MailController {

    private final MailService mailService;


    @GetMapping("/sendEmail")
    public void sendSimpleMailMessage(@RequestParam String email) {
        mailService.sendSimpleMailMessage(email);

    }
}
