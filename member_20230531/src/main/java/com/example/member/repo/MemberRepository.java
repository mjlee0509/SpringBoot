package com.example.member.repo;

import com.example.member.dto.MemberDTO;
import com.example.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    MemberEntity findByMemberEmailAndMemberPassword(String memberEmail, String memberPassword);

}
