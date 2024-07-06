package com.likelion.byuldajul.user.dto;

import com.likelion.byuldajul.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private String eamil;

    private String nickname;

    @Builder
    public UserResponseDto(String eamil, String nickname) {
        this.eamil = eamil;
        this.nickname = nickname;
    }

    // Entity -> DTO 변환
    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .eamil(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}
