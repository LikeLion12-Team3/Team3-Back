package com.likelion.byuldajul.user.controller;

import com.likelion.byuldajul.user.dto.CreateUserRequestDto;
import com.likelion.byuldajul.user.dto.CreateUserResponseDto;
import com.likelion.byuldajul.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

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
        CreateUserResponseDto createUserResponseDto = userService.signUp(createUserRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createUserResponseDto); // 생성 성공 시 Status Code 변경
    }
}
