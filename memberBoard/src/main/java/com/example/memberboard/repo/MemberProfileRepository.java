package com.example.memberboard.repo;

import com.example.memberboard.entity.MemberProfileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberProfileRepository extends JpaRepository<MemberProfileEntity, Long> {

}
