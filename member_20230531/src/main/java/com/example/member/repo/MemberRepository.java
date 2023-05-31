package com.example.member.repo;

import com.example.member.dto.MemberDTO;
import com.example.member.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> {
    // 필요한 메서드는 Repository에서 자체제작해서 쓸 수도 있다!
    // select에 해당되는 메서드들은 반드시 리턴타입이 Entity여야 함
    // select에 해당되는 메서드들은 반드시 findBy~~ 여야 한다

    // 내가 한거
    MemberEntity findByMemberEmailAndMemberPassword(String memberEmail, String memberPassword);

    // 교수님이 하신거
    // 내가 Optional을 놓쳤구나...
    Optional<MemberEntity> findByMemberEmail(String MemberEmail);

}
