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
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequestDto createUserRequestDto, BindingResult bindingResult) {
        // 유효성 검사 결과 확인
        if (bindingResult.hasErrors()) {
            // 유효성 검사 실패 시
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError error : bindingResult.getFieldErrors()) {
                errorMap.put(error.getField(), error.getDefaultMessage());
            }
            // 실패 메시지와 함께 BadRequest 응답 반환
            return ResponseEntity.badRequest().body(errorMap);
        }

        // 유효성 검사 통과 시 회원 가입 서비스 호출
        try {
            CreateUserResponseDto createUserResponseDto = userService.signUp(createUserRequestDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponseDto);
        } catch (IllegalArgumentException e) { //서비스 단에서 IllegalArgumentException 예외 catch
            if (e.getMessage().contains("해당 이메일이 이미 존재합니다.")) { //예외 메세지에 해당 내용이 존재하면 아래 에러 응답
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        Map.of("error", "Conflict",
                                "message", e.getMessage())
                );
            } else { //존재하지 않으면 아래 에러 응답
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Map.of("error", "Bad Request",
                                "message", "올바르지 않은 요청입니다.")
                );
            }
        }
    }

    //회원정보 조회
    @GetMapping
    public ResponseEntity<?> getUsers(@AuthenticationPrincipal CustomUserDetails userDetails) {
        //인증되지 않은 경우
        if(userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body( //UNAUTHORIZED 상태 코드와 함게 아래 key value 형태로 body값 반환
                    Map.of("error", "Unauthorized",
                            "message", "인증 자격 증명이 제공되지 않았거나 유효하지 않습니다.")
            );
        }

        try {
            //userDetails에서 getUsername 메서드로 email을 꺼내서 서비스단에서 getUserByEmail 메서드로 유저 정보를 가져옴.
            UserResponseDto userResponseDto = userService.getUserByEmail(userDetails.getUsername());
            return ResponseEntity.ok(userResponseDto);
        } catch (IllegalArgumentException e) {
            //서비스 단에서 IllegalArgumentException 예외 catch
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    Map.of("error", "Not Found",
                            "message", e.getMessage())
            );
        }
    }

    //닉네임 변경
    @PatchMapping(value = "/nickname")
    public ResponseEntity<?> updateNickname(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @Valid @RequestBody UpdateNicknameRequestDto updateNicknameRequestDto) {
        if (userDetails == null) {
            // 인증되지 않은 경우
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("error", "Unauthorized",
                            "message", "인증 자격 증명이 제공되지 않았거나 유효하지 않습니다.")
            );
        }

        try {
            userService.updateNickname(userDetails.getUsername(), updateNicknameRequestDto.getNickname());
            return ResponseEntity.ok(Map.of("message", "닉네임이 성공적으로 변경되었습니다."));
        } catch (IllegalArgumentException e) { //서비스 단에서 IllegalArgumentException 예외 catch
            if (e.getMessage().contains("사용자가 존재하지 않습니다")) {  //예외 메세지에 해당 내용이 존재하면 아래 에러 응답
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of("error", "Not Found",
                                "message", e.getMessage())
                );
            } else { //존재하지 않으면 아래 에러 응답
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Map.of("error", "Bad Request",
                                "message", "올바르지 않은 요청입니다.")
                );
            }
        }
    }

    //비밀번호 변경
    @PutMapping(value = "/pw")
    public ResponseEntity<?> updatePassword(@AuthenticationPrincipal CustomUserDetails userDetails,
                                            @Valid @RequestBody UpdatePasswordRequestDto updatePasswordRequestDto) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("error", "Unauthorized",
                            "message", "인증 자격 증명이 제공되지 않았거나 유효하지 않습니다.")
            );
        }

        try {
            userService.updatePassword(userDetails.getUsername(), updatePasswordRequestDto.getNewPassword());
            return ResponseEntity.ok(Map.of("message", "비밀번호가 성공적으로 재설정되었습니다."));
        } catch (IllegalArgumentException e) { //서비스 단에서 IllegalArgumentException 예외 catch
            if (e.getMessage().contains("요청한 사용자를 찾을 수 없습니다")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of("error", "Not Found",
                                "message", e.getMessage())
                );
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Map.of("error", "Bad Request",
                                "message", "올바르지 않은 요청입니다.")
                );
            }
        }
    }


    //회원 탈퇴
    @DeleteMapping
    public ResponseEntity<?> deleteUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        if (userDetails == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    Map.of("error", "Unauthorized",
                            "message", "인증 자격 증명이 제공되지 않았거나 유효하지 않습니다.")
            );
        }

        try {
            userService.deleteUser(userDetails.getUsername());
            return ResponseEntity.ok(Map.of("message", "사용자가 성공적으로 삭제되었습니다"));
        } catch (IllegalArgumentException e) { //서비스 단에서 IllegalArgumentException 예외 catch
            if (e.getMessage().contains("요청한 사용자를 찾을 수 없습니다")) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        Map.of("error", "Not Found",
                                "message", e.getMessage())
                );
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        Map.of("error", "Bad Request",
                                "message", "올바르지 않은 요청입니다.")
                );
            }
        }
    }

}


