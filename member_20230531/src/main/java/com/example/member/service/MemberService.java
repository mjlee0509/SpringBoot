package com.example.member.service;

import com.example.member.dto.MemberDTO;
import com.example.member.entity.MemberEntity;
import com.example.member.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;


    public Long save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
        memberRepository.save(memberEntity);
        return memberRepository.save(memberEntity).getId();
    }

    public boolean login(MemberDTO memberDTO) {
        Optional<MemberEntity> findMember = memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword());
        if(findMember == null) {
            return false;
        } else {
            return true;
        }
    }

    public void loginAxios(MemberDTO memberDTO) {
        memberRepository.findByMemberEmailAndMemberPassword
                        (memberDTO.getMemberEmail(), memberDTO.getMemberPassword())
                .orElseThrow(() -> new NoSuchElementException("이메일 또는 비밀번호가 일치하지 않습니다"));
    }


    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for(MemberEntity memberEntity : memberEntityList) {
            memberDTOList.add(MemberDTO.toDTO(memberEntity));
        }
        return memberDTOList;
    }

//    public MemberDTO findById(Long id) {
//        Optional<MemberEntity> optionalMemberEntity = memberRepository.findById(id);
//        if (optionalMemberEntity.isPresent()) {
//            MemberEntity memberEntity = optionalMemberEntity.get(); // optional 껍데기 벗기기
//            return MemberDTO.toDTO(optionalMemberEntity.get());  // Entity를 DTO로 변환
//        } else {
//            return null;
//        }
//    }

    public MemberDTO findById(Long id) {
        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return MemberDTO.toDTO(memberEntity);
    }

    public void delete(Long id) {
        memberRepository.deleteById(id);
    }
    public MemberDTO findByMemberEmail(String loginEmail) {
        // 1. 회원정보를 조회 -> 회원정보가 없으면 예외 처리; 있으면 MemberEntity 리턴 후 2.로 이동
        MemberEntity memberEntity = memberRepository.findByMemberEmail(loginEmail).orElseThrow(() -> new NoSuchElementException());
        // 2. 있는 경우 DTO로 변환하여 Controller로 리턴
        return MemberDTO.toDTO(memberEntity);
    }

    public void update(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toUpdateEntity(memberDTO);
        memberRepository.save(memberEntity);
    }

    public boolean emailCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }
}
