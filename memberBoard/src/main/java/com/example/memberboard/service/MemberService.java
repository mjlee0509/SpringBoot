package com.example.memberboard.service;

import com.example.memberboard.dto.MemberDTO;
import com.example.memberboard.dto.MemberProfileDTO;
import com.example.memberboard.entity.MemberEntity;
import com.example.memberboard.entity.MemberProfileEntity;
import com.example.memberboard.repo.MemberProfileRepository;
import com.example.memberboard.repo.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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

    public boolean login(MemberDTO memberDTO) {
        Optional<MemberEntity> memberEntity = memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword());
        if (memberEntity.isPresent()) {
            return true;
        } else {
            return false;
        }
    }

    public void loginAxios(MemberDTO memberDTO) {

        memberRepository.findByMemberEmailAndMemberPassword(memberDTO.getMemberEmail(), memberDTO.getMemberPassword())
                .orElseThrow(() -> new NoSuchElementException("이메일 또는 비밀번호가 일치하지 않습니다"));
    }

    @Transactional
    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity: memberEntityList) {
            memberDTOList.add(MemberDTO.toDTO(memberEntity));
        }
        return memberDTOList;
    }


    @Transactional
    public MemberDTO findById(Long id) {
        MemberEntity memberEntity = memberRepository.findById(id).orElseThrow(() -> new NoSuchElementException());
        return MemberDTO.toDTO(memberEntity);
    }
}

