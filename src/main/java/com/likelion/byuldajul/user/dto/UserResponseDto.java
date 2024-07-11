package com.likelion.byuldajul.user.dto;

import com.likelion.byuldajul.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {

    private String email;

    private String nickname;

    @Builder
    public UserResponseDto(String email, String nickname) {
        this.email = email;
        this.nickname = nickname;
    }

    // Entity -> DTO 변환
    public static UserResponseDto from(User user) {
        return UserResponseDto.builder()
                .email(user.getEmail())
                .nickname(user.getNickname())
                .build();
    }
}
