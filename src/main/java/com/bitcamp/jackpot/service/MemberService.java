package com.bitcamp.jackpot.service;


import com.bitcamp.jackpot.domain.Member;
import com.bitcamp.jackpot.dto.CustomUserDetails;
import com.bitcamp.jackpot.dto.MemberDTO;
import com.bitcamp.jackpot.dto.MemberEditDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;
import java.util.Map;


public interface MemberService {

    public void signUp(MemberDTO memberDTO);

    public void edit(String email, MemberEditDTO memberEditDTO);

    public void remove(String email);

    public MemberDTO findOne(String email);

    public Page<MemberDTO> findAll(int page, int size);

    public boolean checkEmail(String email);

    public boolean checkNickName(String password);

    public String findId(String name, String phone);


    public void adminRemove(int memberId);

    public void adminEdit(int memberId, MemberDTO memberDTO);

    public Page<MemberDTO> searchMembersByName(String name, Pageable pageable);


}