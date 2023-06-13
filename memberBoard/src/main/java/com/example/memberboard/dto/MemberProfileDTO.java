package com.example.memberboard.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberProfileDTO {
    private Long id;
    private String originalFileName;
    private String storedFileName;


}
