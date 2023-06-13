package com.example.memberboard.service;

import com.example.memberboard.dto.MemberDTO;
import com.example.memberboard.dto.MemberProfileDTO;
import com.example.memberboard.entity.MemberEntity;
import com.example.memberboard.entity.MemberProfileEntity;
import com.example.memberboard.repo.MemberProfileRepository;
import com.example.memberboard.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberProfileRepository memberProfileRepository;


    public boolean emailCheck(String memberEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(memberEmail);
        if (optionalMemberEntity.isEmpty()) {
            return true;
        } else {
            return false;
        }
    }

    public Long save(MemberDTO memberDTO) throws IOException {
        if (memberDTO.getMemberProfile().isEmpty()) {
            MemberEntity memberEntity = MemberEntity.toSaveEntity(memberDTO);
            return memberRepository.save(memberEntity).getId();
        } else {
            MemberEntity memberEntity = MemberEntity.toSaveEntityWithProfile(memberDTO);
            MemberEntity saveEntity = memberRepository.save(memberEntity);

            String originalFileName = memberDTO.getMemberProfile().getOriginalFilename();
            String storedFileName = System.currentTimeMillis() + "-" + originalFileName;
            String savePath = "D:\\springboot_img\\" + storedFileName;

            memberDTO.getMemberProfile().transferTo(new File(savePath));

            MemberProfileEntity memberProfileEntity =
                    MemberProfileEntity.toSaveMemberEntity(saveEntity, originalFileName, storedFileName);

            memberProfileRepository.save(memberProfileEntity);
            return saveEntity.getId();

        }

    }

}

