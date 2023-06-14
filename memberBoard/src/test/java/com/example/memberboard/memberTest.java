package com.example.memberboard;

import com.example.memberboard.dto.MemberDTO;
import com.example.memberboard.entity.MemberEntity;
import com.example.memberboard.repo.MemberRepository;
import com.example.memberboard.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.stream.IntStream;

@SpringBootTest
public class memberTest {
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    MemberService memberService;

    private MemberDTO newMember() {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("admin");
        memberDTO.setMemberPassword("1234");
        memberDTO.setMemberName("관리자");
        memberDTO.setMemberBirth("1995-05-09");
        memberDTO.setMemberMobile("010-9999-9999");
        return memberDTO;
    }

    private MemberDTO newMembers(int i) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setMemberEmail("member"+i);
        memberDTO.setMemberPassword("1234");
        memberDTO.setMemberName(i+"번째 테스트맨");
        memberDTO.setMemberBirth("1995-01-01");
        memberDTO.setMemberMobile("010-1111-1111");
        return memberDTO;
    }

    @Test
    @Transactional
    @DisplayName("테스트 데이터 생성")
    @Rollback(value = false)
    public void testDatum() {
        IntStream.rangeClosed(1, 1).forEach(i -> {
            memberRepository.save(MemberEntity.toSaveEntity(newMember()));
        });
    }
    @Test
    @Transactional
    @DisplayName("많은 테스트 데이터 생성")
    @Rollback(value = false)
    public void testData() {
        IntStream.rangeClosed(1, 10).forEach(i -> {
            memberRepository.save(MemberEntity.toSaveEntity(newMembers(i)));
        });
    }



}
