package com.example.member;

import com.example.member.dto.MemberDTO;
import com.example.member.repo.MemberRepository;
import com.example.member.service.MemberService;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
public class MemberTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    @Test
    @DisplayName("Repository Method Test")
    public void repositoryTest() {
        memberRepository.findByMemberEmail("testEmail");
        memberRepository.findByMemberEmailAndMemberPassword("testEmail", "1234");
    }

    private MemberDTO newMember() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("test-email");
        memberDTO.setMemberPassword("1234");
        memberDTO.setMemberName("Test");
        memberDTO.setMemberBirth("2000-01-01");
        memberDTO.setMemberMobile("010-9999-9999");
        return memberDTO;
    }

    // 여러개의 테스트 데이터를 만들고 싶을 때
    private MemberDTO newMembers(int i) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("test-email"+i);
        memberDTO.setMemberPassword("1234"+i);
        memberDTO.setMemberName("Test"+i);
        memberDTO.setMemberBirth("2000-01-01");
        memberDTO.setMemberMobile("010-9999-9999");
        return memberDTO;
    }

    @Test
    public void testData() {
        for (int i=1; i<=20; i++) {
            memberService.save(newMembers(i));
        }
    }

    // 회원가입 테스트
    @Test
    @Transactional
    @Rollback
    @DisplayName("#1. 회원가입 테스트")
    public void memberSaveTest() {
        MemberDTO memberDTO = newMembers(999);
        Long savedId = memberService.save(memberDTO);
        MemberDTO dto = memberService.findById(savedId);
        assertThat(memberDTO.getMemberEmail().equals(dto.getMemberEmail()));
    }
}
