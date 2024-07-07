package com.likelion.byuldajul.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.bind.annotation.BindParam;

@Getter
@NoArgsConstructor
public class UpdateNicknameRequestDto {

    @Size(min = 1, max = 12, message = "[ERROR] 닉네임은 1자 이상, 12글자 이하여야 합니다.")
    @NotBlank(message = "[ERROR] 닉네임은 필수 입력값입니다.")
    private String nickname;

    @Builder
    public UpdateNicknameRequestDto(String nickname) {
        this.nickname = nickname;
    }
}
