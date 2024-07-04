package com.likelion.byuldajul.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LoginRequestDto {

    @NotBlank(message = "[ERROR] 이메일 입력은 필수입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "[ERROR] 이메일 형식에 맞지 않습니다.")
    private String email;

    @Size(min = 8, max = 64, message = "[ERROR] 비밀번호는 8자 이상, 64자 이하여야 합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d).{8,64}$", message = "[ERROR] 알파벳과 숫자가 모두 들어가는 8자리 이상의 비밀번호를 입력해주세요.")
    @NotBlank(message = "[ERROR] 비밀번호는 필수 입력값입니다.")
    private String password;

    @Builder
    public LoginRequestDto(String email, String password) {
        this.email = email;
        this.password = password;
    }
}
