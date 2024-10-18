package com.bitcamp.jackpot.service;


import com.bitcamp.jackpot.dto.MemberDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface MemberService {

    public void signUp(MemberDTO memberDTO);

    public void edit(int memberID, MemberDTO memberDTO);

    public void remove(String email);

    public MemberDTO findOne(String email);

    public Page<MemberDTO> findAll(int page, int size);

    public boolean checkEmail(String email);
    public boolean checkPwd(String email,String pwd);
    public boolean checkNickName(String password);

    public String findId(String name, String phone);

    public boolean resetPwd(String email,String pwd);

    public void adminRemove(int memberId);

    public void adminEdit(int memberId, MemberDTO memberDTO);

    public Page<MemberDTO> searchMembersByName(String name, Pageable pageable);


}