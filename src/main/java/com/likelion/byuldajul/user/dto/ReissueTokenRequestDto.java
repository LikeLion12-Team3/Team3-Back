package com.likelion.byuldajul.user.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReissueTokenRequestDto {
    public String refreshToken;

    @Builder
    public ReissueTokenRequestDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
