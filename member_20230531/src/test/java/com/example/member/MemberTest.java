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

import java.util.NoSuchElementException;

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

    @Test
    @Transactional
    @Rollback
    @DisplayName("#2. 로그인 테스트")
    public void loginTest() {
        // #2-1. 테스트용 데이터 생성
        MemberDTO memberDTO = newMembers(999);
        // #2-2. 이메일 또는 비밀번호를 틀렸을 때 Exception 상황 설정
        MemberDTO loginDTO = new MemberDTO();
        loginDTO.setMemberEmail("wrong email");
        loginDTO.setMemberPassword("1234");
        // #2-3. 해당 Exception이 발생하면 테스트 성공
        assertThatThrownBy(() -> memberService.loginAxios(loginDTO))
                .isInstanceOf(NoSuchElementException.class);
    }

    @Test
    @DisplayName("#3. 삭제 테스트")
    public void deleteTest() {
        // #3-1. 테스트 데이터 생성
        MemberDTO memberDTO = newMembers(999);
        Long savedId = memberService.save(memberDTO);
        // #3-2. 삭제 실시
        memberService.delete(savedId);
        // #3-3. 삭제 후 이 회원을 검색했을 때, NoSuchElementException이 터지면 테스트 성공
        assertThatThrownBy(() -> memberService.findById(savedId))
                .isInstanceOf(NoSuchElementException.class);
    }
}
