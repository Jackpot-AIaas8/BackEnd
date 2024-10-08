package com.bitcamp.jackpot.service;

import com.bitcamp.jackpot.domain.Member;
import com.bitcamp.jackpot.domain.Shop;
import com.bitcamp.jackpot.dto.CustomUserDetails;
import com.bitcamp.jackpot.dto.MemberDTO;
import com.bitcamp.jackpot.dto.MemberEditDTO;
import com.bitcamp.jackpot.dto.ShopDTO;
import com.bitcamp.jackpot.repository.MemberRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.util.annotation.NonNullApi;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = false)
@RequiredArgsConstructor
@Log4j2
public class MemberServiceImpl implements MemberService {

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;

    @Transactional
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
    public void edit(String email, MemberEditDTO memberEditDTO) {
        // 해당 ID의 멤버 조회
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Member not found"));

        Member editMember = Member.builder()
                .memberId(member.getMemberId())
                .name(memberEditDTO.getName())
                .email(member.getEmail())
                .phone(memberEditDTO.getPhone())
                .pwd(memberEditDTO.getPwd())
                .nickName(memberEditDTO.getNickname())
                .address(memberEditDTO.getAddress())
                .isAdmin(member.getIsAdmin())
                //. 필요한 프로퍼티 채우기, 바뀌는애는 memberEditDTO에서 가져오고 변화가없는 필드는 member에서 가져온다.
                .build();
        memberRepository.save(editMember);
    }

    @Override
    public void remove(String email) {
        memberRepository.deleteByEmail(email);
    }

    @Override
    public MemberDTO findOne(String email) {
        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        Member member = optionalMember.orElseThrow(() -> new EntityNotFoundException("Member not found"));
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
    public boolean checkNickName(String nickName) {
        return memberRepository.existsByNickName(nickName);
    }


    @Override
    public String findId(String phone, String name) {
        Optional<Member> result = memberRepository.findByNameAndPhone(phone, name);

        return result.map(Member::getEmail)
                .orElseThrow(() -> new RuntimeException("Member not found with provided phone and name"));
    }

    @Override
    public String findPwd(String phone, String name, String email) {
        Optional<Member> result = memberRepository.findByPhoneAndNameAndEmail(phone, name, email);

        return result.map(Member::getPwd)
                .orElseThrow(() -> new RuntimeException("Member not found with provided phone and name and email"));
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

}
