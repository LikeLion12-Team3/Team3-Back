package com.likelion.byuldajul.user.dto;

import com.likelion.byuldajul.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CreateUserResponseDto {

    private Long id;

    private String email;

    private String nickname;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    @Builder
    public CreateUserResponseDto(Long id, String email, String nickname, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.email = email;
        this.nickname = nickname;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    // Entity -> DTO 변환
    public static CreateUserResponseDto from(User user) {
        return CreateUserResponseDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .createdAt(user.getCreatedAt())
                .modifiedAt(user.getModifiedAt())
                .build();
    }
}
