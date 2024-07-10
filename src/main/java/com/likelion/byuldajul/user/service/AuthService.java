package com.likelion.byuldajul.user.service;

import com.likelion.byuldajul.user.dto.JwtDto;
import com.likelion.byuldajul.user.dto.ReissueTokenResponseDto;
import com.likelion.byuldajul.user.entity.Token;
import com.likelion.byuldajul.user.repository.TokenRepository;
import com.likelion.byuldajul.user.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final JwtUtil jwtUtil;
    private final TokenRepository tokenRepository;
    private final TokenService tokenService;

    public ReissueTokenResponseDto reissueToken(String refreshToken) {

        log.info("[ Auth Service ] 토큰 재발급을 시작합니다.");

        try {
            // Refresh Token에서 사용자 이메일 추출
            String email = jwtUtil.getEmail(refreshToken);
            log.info("[ Auth Service ] Email ---> {}", email);

            // Refresh Token 유효성 검사
            jwtUtil.validateToken(refreshToken);

            // Refresh Token 블랙리스트 확인
            if (tokenService.isTokenBlacklisted(refreshToken)) {
                log.info("[ Auth Service ] 유효하지 않은 리프레시 토큰입니다.");
                throw new SecurityException("유효하지 않은 리프레시 토큰입니다.");
            }

            // 이메일로 저장된 Refresh Token 가져오기
            String storedRefreshToken = tokenService.getRefreshTokenByEmail(email);
            if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
                log.info("[ Auth Service ] 저장된 리프레시 토큰과 일치하지 않습니다.");
                throw new SecurityException("유효하지 않은 리프레시 토큰입니다.");
            }

            log.info("[ Auth Service ] Refresh Token 이 유효합니다. 토큰을 재발급합니다.");
            // 새로운 액세스 토큰 발급
            JwtDto jwtDto = jwtUtil.reissueToken(refreshToken);

            // TokenResponseDto 반환
            return new ReissueTokenResponseDto(jwtDto.getAccessToken());

        } catch (SecurityException e) {
            log.error("[ Auth Service ] 보안 예외 발생: {}", e.getMessage());
            throw new SecurityException("유효하지 않은 리프레시 토큰입니다.", e);
        }
    }
}
