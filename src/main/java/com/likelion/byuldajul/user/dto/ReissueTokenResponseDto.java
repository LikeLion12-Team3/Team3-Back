package com.likelion.byuldajul.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReissueTokenResponseDto {
    private String accessToken;

    @Builder
    public ReissueTokenResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }
}
