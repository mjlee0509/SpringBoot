package com.example.memberboard.dto;

import com.example.memberboard.entity.MemberEntity;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;

@Data
public class MemberDTO {
    private Long id;
    private String memberEmail;
    private String memberPassword;
    private String memberName;
    private String memberMobile;
    private String memberBirth;
    private String createdAt;
    private int profileAttached;
    private MultipartFile memberProfile;
    private String originalFileName;
    private String storedFileName;

    public static MemberDTO toDTO(MemberEntity memberEntity) {
        MemberDTO memberDTO = new MemberDTO();
        memberDTO.setId(memberEntity.getId());
        memberDTO.setMemberEmail(memberEntity.getMemberEmail());
        memberDTO.setMemberPassword(memberEntity.getMemberPassword());
        memberDTO.setMemberName(memberEntity.getMemberName());
        memberDTO.setMemberBirth(memberEntity.getMemberBirth());
        memberDTO.setMemberMobile(memberEntity.getMemberMobile());
        return memberDTO;
    }




}
