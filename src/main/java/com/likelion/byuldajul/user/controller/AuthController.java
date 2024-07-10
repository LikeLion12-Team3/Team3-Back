package com.likelion.byuldajul.user.controller;

import com.likelion.byuldajul.user.dto.JwtDto;
import com.likelion.byuldajul.user.dto.ReissueTokenRequestDto;
import com.likelion.byuldajul.user.dto.ReissueTokenResponseDto;
import com.likelion.byuldajul.user.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    // 토큰 재발급 API
    @PostMapping("/reissue")
    public ResponseEntity<?> reissue(@RequestBody ReissueTokenRequestDto tokenRequestDto) {

        log.info("[ Auth Controller ] 토큰을 재발급합니다. ");
        ReissueTokenResponseDto newToken = authService.reissueToken(tokenRequestDto.getRefreshToken());
        return ResponseEntity.ok(newToken);
    }
}

