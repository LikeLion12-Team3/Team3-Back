package com.likelion.byuldajul.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter //필드가 newPassword 하나이므로 Builder 대신 Setter를 사용하였습니다.
public class UpdatePasswordRequestDto {

    @Size(min = 8, max = 64, message = "[ERROR] 비밀번호는 8자 이상, 64자 이하여야 합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d).{8,64}$", message = "[ERROR] 알파벳과 숫자가 모두 들어가는 8자리 이상의 비밀번호를 입력해주세요.")
    @NotBlank(message = "[ERROR] 비밀번호는 필수 입력값입니다.")
    private String newPassword;

}
