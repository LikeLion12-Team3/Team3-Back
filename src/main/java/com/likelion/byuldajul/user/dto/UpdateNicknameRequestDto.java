package com.likelion.byuldajul.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter //필드가 nickname 하나이므로 Builder 대신 Setter를 사용하였습니다.
public class UpdateNicknameRequestDto {

    @Size(min = 1, max = 12, message = "[ERROR] 닉네임은 1자 이상, 12글자 이하여야 합니다.")
    @NotBlank(message = "[ERROR] 닉네임은 필수 입력값입니다.")
    private String nickname;

}
