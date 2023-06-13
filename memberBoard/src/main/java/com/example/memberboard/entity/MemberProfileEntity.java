package com.example.memberboard.entity;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "member_profile_table_0612")
public class MemberProfileEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String originalFileName;

    @Column(length = 100, nullable = false)
    private String storedFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

    public static MemberProfileEntity toSaveMemberEntity(MemberEntity saveEntity, String originalFileName, String storedFileName) {
        MemberProfileEntity memberProfileEntity = new MemberProfileEntity();
        memberProfileEntity.setMemberEntity(saveEntity);
        memberProfileEntity.setOriginalFileName(originalFileName);
        memberProfileEntity.setStoredFileName(storedFileName);
        return memberProfileEntity;
    }





}
