package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.domain.Auction;
import com.bitcamp.jackpot.domain.Member;
import com.bitcamp.jackpot.dto.MemberDTO;
import com.bitcamp.jackpot.dto.MemberEditDTO;
import com.bitcamp.jackpot.jwt.RedisUtil;
import com.bitcamp.jackpot.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private final RedisUtil redisUtil;

    @Override
    public void signUp(MemberDTO memberDTO) {
        Member member = modelMapper.map(memberDTO, Member.class);
        // 비밀번호 인코딩
        member.encodePassword(memberDTO.getPwd(), bCryptPasswordEncoder);
        //엔티티 저장
        try {
            memberRepository.save(member);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalArgumentException("중복된 이메일입니다.");
        }
    }

    @Override
    public void edit(int memberID, MemberDTO memberDTO) {
        try {
            // 해당 ID의 멤버 조회
            Member member = memberRepository.findById(memberID)
                    .orElseThrow(() -> new EntityNotFoundException("Member not found"));
            System.out.println("member 조회 완료");

            member.updateMemberInfo(
                    memberDTO.getName(),
                    memberDTO.getPhone(),
                    memberDTO.getPwd(),
                    memberDTO.getAddress()
            );

            // 업데이트된 기존 객체를 저장
            memberRepository.save(member);
            System.out.println(member + " 저장 완료");

        } catch (Exception e) {
            System.out.println(e + " 실패");
        }
    }





    @Override
    public void remove(String email) {
        Member member = memberRepository.findByEmail(email).orElseThrow();
        memberRepository.deleteByEmail(email);
        // cascade.remove로 멤버 삭제시 그 멤버의 게시글도 같이 삭제하려면 삭제하려는 엔티티가 이미 로드되어 있어야 함.
        // 그래서 그냥 딜리트만 하면 안되고 엔티티를 한번 찾아야 함.
    }


    @Override
    public MemberDTO findOne(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);

        Member member = optionalMember.orElseThrow(() -> new EntityNotFoundException("Member not found"));
        log.info("member : " + member);
        return modelMapper.map(member, MemberDTO.class);

    }

    @NonNull
    @Override
    public Page<MemberDTO> findAll(int page, int size) {
        // 입력 값 검증
        if (page < 0 || size <= 0) {
            throw new IllegalArgumentException("Page index must be non-negative and size must be greater than 0.");
        }

        Pageable pageable = PageRequest.of(page, size);

        Page<Member> members = memberRepository.findAll(pageable);

        return members.map(member -> modelMapper.map(member, MemberDTO.class));
    }

    @Override
    public boolean checkEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    @Override
    public boolean checkPwd(String email,String pwd) {

        return memberRepository.existsByEmailAndPwd(email,bCryptPasswordEncoder.encode(pwd));
    }

    @Override
    public boolean resetPwd(String email, String pwd) {
        try{
        // 1. 이메일로 회원 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Member not found with email: " + email));

        // 2. 명시적 메서드를 통해 비밀번호만 변경
        member.changePassword(pwd, bCryptPasswordEncoder);

        // 3. 변경된 엔티티 저장
        memberRepository.save(member);
        }catch (Exception e){
        log.error(e);
        return false;
        }

        return true;
    }







    @Override
    public boolean checkNickName(String nickName) {
        return memberRepository.existsByNickName(nickName);
    }


    @Override
    public String findId(String name, String phone) {
        Optional<Member> result = memberRepository.findByNameAndPhone(name, phone);

        return result.map(Member::getEmail)
                .orElseThrow(() -> new RuntimeException("Member not found with provided phone and name"));
    }



    public void adminRemove(int memberId) {
        log.info(memberId);
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당 상품을 찾을 수 없습니다."));
        memberRepository.deleteById(memberId);
    }

    @Override
    public void adminEdit(int memberId, MemberDTO memberDTO) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new RuntimeException("해당 상품을 찾을 수 없습니다."));
        modelMapper.map(memberDTO, member);
        memberRepository.save(member);
    }

    @Override
    public Page<MemberDTO> searchMembersByName(String name, Pageable pageable) {
        try {
            Page<Member> members = memberRepository.findByNameContaining(name, pageable);
            return members.map(member -> modelMapper.map(member, MemberDTO.class));
        } catch (Exception e) {
            throw new RuntimeException("Error occurred while searching members by name", e);
        }
    }



}