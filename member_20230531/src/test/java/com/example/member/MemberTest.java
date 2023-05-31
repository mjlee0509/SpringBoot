package com.example.member;

import com.example.member.repo.MemberRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MemberTest {
    @Autowired
    MemberRepository memberRepository;

    @Test
    @DisplayName("Repository Method Test")
    public void repositoryTest() {
        memberRepository.findByMemberEmail("testEmail");
        memberRepository.findByMemberEmailAndMemberPassword("testEmail", "1234");
    }
}
