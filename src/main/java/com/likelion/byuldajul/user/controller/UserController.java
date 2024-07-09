package com.likelion.byuldajul.user.controller;

import com.likelion.byuldajul.user.dto.*;
import com.likelion.byuldajul.user.service.UserService;
import com.likelion.byuldajul.user.userDetails.CustomUserDetails;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    //회원가입
    @PostMapping(value = "/signup")
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequestDto createUserRequestDto) {
        CreateUserResponseDto createUserResponseDto = userService.signUp(createUserRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponseDto);
    }

    //회원정보 조회
    @GetMapping
    public ResponseEntity<?> getUsers(@AuthenticationPrincipal CustomUserDetails userDetails) {
        UserResponseDto userResponseDto = userService.getUserByEmail(userDetails.getUsername());
        return ResponseEntity.ok(userResponseDto);
    }

    //닉네임 변경
    @PatchMapping(value = "/nickname")
    public ResponseEntity<?> updateNickname(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @Valid @RequestBody UpdateNicknameRequestDto updateNicknameRequestDto) {
        userService.updateNickname(userDetails.getUsername(), updateNicknameRequestDto.getNickname());
        return ResponseEntity.ok(Map.of("message", "닉네임이 성공적으로 변경되었습니다."));
    }

    //비밀번호 변경
    @PutMapping(value = "/pw")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @Valid @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto) {
        userService.updatePassword(userDetails.getUsername(), updatePasswordRequestDto.getNewPassword());
        return ResponseEntity.ok(Map.of("message", "비밀번호가 성공적으로 재설정되었습니다."));
    }

    //회원 탈퇴
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        userService.deleteUser(userDetails.getUsername());
        return ResponseEntity.ok(Map.of("message", "사용자가 성공적으로 삭제되었습니다"));
    }
}


