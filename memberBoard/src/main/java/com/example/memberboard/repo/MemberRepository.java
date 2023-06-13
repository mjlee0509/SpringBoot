package com.example.memberboard.repo;

import com.example.memberboard.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {

    Optional<MemberEntity> findByMemberEmailAndMemberPassword(String memberEmail, String memberPassword);
    Optional<MemberEntity> findByMemberEmail(String memberEmail);


}
