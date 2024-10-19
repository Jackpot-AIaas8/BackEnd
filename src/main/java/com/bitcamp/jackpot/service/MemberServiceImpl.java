package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.config.error.ErrorCode;
import com.bitcamp.jackpot.config.error.exception.DatabaseException;
import com.bitcamp.jackpot.config.error.exception.DuplicateResourceException;
import com.bitcamp.jackpot.config.error.exception.InvalidPasswordException;
import com.bitcamp.jackpot.config.error.exception.MemberNotFoundException;
import com.bitcamp.jackpot.domain.Member;
import com.bitcamp.jackpot.dto.MemberDTO;
import com.bitcamp.jackpot.repository.MemberRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashMap;
import java.util.Map;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Override
    public void signUp(MemberDTO memberDTO) {
        log.info("dto 확인  - 확인: {}", memberDTO);

        Member member = modelMapper.map(memberDTO, Member.class);
        // 비밀번호 인코딩
        member.encodePassword(memberDTO.getPwd(), bCryptPasswordEncoder);
        log.info("저장할 멤버 - 확인: {}", member);
        //엔티티 저장
        try {
            memberRepository.save(member);
        } catch (Exception e) {
            throw new DatabaseException();
        }
    }

    @Override
    public void edit(int memberID, MemberDTO memberDTO) {
        try {
            // 해당 ID의 멤버 조회
            Member member = memberRepository.findById(memberID)
                    .orElseThrow(MemberNotFoundException::new);

            member.updateMemberInfo(
                    memberDTO.getName(),
                    memberDTO.getPhone(),
                    memberDTO.getPwd(),
                    memberDTO.getAddress()
            );
            memberRepository.save(member);
        } catch (Exception e) {
            throw new DatabaseException();
        }
    }


    @Override
    public void remove(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
        memberRepository.delete(member);
        log.info(" 성공적 탈퇴 {} ", email);
    }



    @Override
    public MemberDTO findOne(String email) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        log.info("Member 를 찾을 수 없습니다.: {}", member);
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
    public Map<String, Boolean> checkNickName(String nickName) {
        Map<String, Boolean> response = new HashMap<>();
        if (memberRepository.existsByEmail(nickName)) {
            response.put("isDuplicate", true);
            throw new DuplicateResourceException(true);
        } else {
            response.put("isDuplicate", false);
        }
        return response;
    }

    @Override
    public Map<String, Boolean> checkEmail(String email) {
        Map<String, Boolean> response = new HashMap<>();
        if (memberRepository.existsByEmail(email)) {
            response.put("isDuplicate", true);
            throw new DuplicateResourceException(true);
        } else {
            response.put("isDuplicate", false);
        }
        return response;
    }

    @Override
    public Map<String, Boolean> checkPwd(String email, String pwd) {
        // 이메일로 사용자 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        boolean isMatchedPwd = bCryptPasswordEncoder.matches(pwd, member.getPwd());
        if (!isMatchedPwd) {
            throw new InvalidPasswordException("알맞지 않은 비밀번호",ErrorCode.INVALID_INPUT_VALUE); // 더 적절한 예외를 사용
        }
        Map<String, Boolean> response = new HashMap<>();
        response.put("isMatchedPwd", true);
        return response;
    }


    @Override
    public boolean resetPwd(String email, String pwd) {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);
        try{
        member.changePassword(pwd, bCryptPasswordEncoder);
        memberRepository.save(member);
        }catch (Exception e){
        log.error(e);
        throw new DatabaseException(ErrorCode.INVALID_INPUT_VALUE);
        }
        return true;
    }










    @Override
    public String findId(String name, String phone) {
        Optional<Member> result = memberRepository.findByNameAndPhone(name, phone);
        result.orElseThrow(MemberNotFoundException::new);
        return result.map(Member::getEmail)
                .orElseThrow(() -> new DatabaseException(ErrorCode.DATABASE_ERROR));
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
            log.error("Error occurred while searching members by name: {}", name, e);
            throw new MemberNotFoundException(null,ErrorCode.MEMBER_NOT_FOUND);
        }
    }



}