package com.bitcamp.jackpot.service;


import com.bitcamp.jackpot.dto.MemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Map;


public interface MemberService {

    public void signUp(MemberDTO memberDTO);

    public void edit(MemberDTO memberDTO);

    public void remove(String email);

    public MemberDTO findOne(String email);

    public Page<MemberDTO> findAll(int page, int size);

    public Map<String, Boolean> checkEmail(String email);

    public Map<String, Boolean> checkPwd(String email,String pwd);
    public Map<String, Boolean> checkNickName(String nickName);

    public String findId(String name, String phone);

    public boolean resetPwd(String email,String pwd);

    public void adminRemove(int memberId);

    public void adminEdit(int memberId, MemberDTO memberDTO);

    public Page<MemberDTO> searchMembersByName(String name, Pageable pageable);


}