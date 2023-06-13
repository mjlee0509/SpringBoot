package com.example.memberboard.entity;

import com.example.memberboard.dto.MemberDTO;
import lombok.Data;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "member_table_0612")
public class MemberEntity extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String memberEmail;

    @Column(length = 20, nullable = false)
    private String memberPassword;

    @Column(length = 15, nullable = false)
    private String memberName;

    @Column(length = 15)
    private String memberMobile;

    @Column(length = 10)
    private String memberBirth;

    @Column
    private int profileAttached;

    @OneToMany(mappedBy = "memberEntity", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<MemberProfileEntity> memberProfileEntityList = new ArrayList<>();


    public static MemberEntity toSaveEntity(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberBirth(memberDTO.getMemberBirth());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        memberEntity.setProfileAttached(0);
        return memberEntity;
    }

    public static MemberEntity toSaveEntityWithProfile(MemberDTO memberDTO) {
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        memberEntity.setMemberBirth(memberDTO.getMemberBirth());
        memberEntity.setMemberMobile(memberDTO.getMemberMobile());
        memberEntity.setProfileAttached(1);
        return memberEntity;
    }
}
