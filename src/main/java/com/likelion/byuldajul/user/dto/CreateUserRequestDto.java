package com.likelion.byuldajul.user.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import com.likelion.byuldajul.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@NoArgsConstructor
public class CreateUserRequestDto {


    @NotBlank(message = "[ERROR] 이메일 입력은 필수입니다.")
    @Pattern(regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+.[A-Za-z]{2,6}$", message = "[ERROR] 이메일 형식에 맞지 않습니다.")
    private String email;

    @Size(min = 8, max = 64, message = "[ERROR] 비밀번호는 8자 이상, 64자 이하여야 합니다.")
    @Pattern(regexp = "^(?=.*[a-z])(?=.*\\d).{8,64}$", message = "[ERROR] 알파벳과 숫자가 모두 들어가는 8자리 이상의 비밀번호를 입력해주세요.")
    @NotBlank(message = "[ERROR] 비밀번호는 필수 입력값입니다.")
    private String password;

    @Size(min = 1, max = 12, message = "[ERROR] 닉네임은 1자 이상, 12글자 이하여야 합니다.")
    @NotBlank(message = "[ERROR] 닉네임은 필수 입력값입니다.")
    private String nickname;

    @Builder
    public CreateUserRequestDto(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    //User Dto -> User Entity로 변환하는 메서드
    public User toEntity(PasswordEncoder passwordEncoder) {
        //Password Encoder 통해 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(password);
        return User.builder()
                .email(email)
                .password(encodedPassword)
                .nickname(nickname)
                .build();
    }

}
