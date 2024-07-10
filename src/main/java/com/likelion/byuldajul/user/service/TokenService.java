package com.likelion.byuldajul.user.service;

import com.likelion.byuldajul.user.entity.Token;
import com.likelion.byuldajul.user.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class TokenService {

    private final RedisTemplate<String, Object> redisTemplate; // RedisTemplate 사용
    private final TokenRepository tokenRepository; // RedisTokenRepository 사용
    // 블랙리스트 키 접두사 (유지보수와 가독성을 위해서 사용)
    private static final String BLACKLIST_PREFIX = "blacklist:"; // static으로 클래스의 모든 인스턴스가 BLACKLIST_PREFIX를 사용할 때 동일한 값을 참조하도록 함.

    // 주어진 토큰을 블랙리스트에 추가하고, 주어진 기간 동안 유지
    public void addToBlacklist(String token, long durationMs) { // durationMs는 TTL을 설정하는데 사용됨. 유효기간이 지나면 Redis에서 자동으로 삭제함.
        String key = BLACKLIST_PREFIX + token; // 블랙리스트 키 생성
        redisTemplate.opsForValue().set(key, true, durationMs, TimeUnit.MILLISECONDS); // Redis에 블랙리스트 키와 값을 저장하고, 만료 시간 설정(TTL)
    }

    // 주어진 토큰이 블랙리스트에 있는지 확인
    public boolean isTokenBlacklisted(String token) {
        String key = BLACKLIST_PREFIX + token; // 블랙리스트 키 생성
        return Boolean.TRUE.equals(redisTemplate.hasKey(key)); // 블랙리스트에 토큰이 있는지 확인(Boolean.TRUE.equals 를 사용하여 null포인터 예외 방지)
    }

    // 주어진 이메일과 리프레시 토큰을 저장
    public void saveToken(String email, String refreshToken) {
        Token token = Token.builder()
                .email(email)
                .refreshToken(refreshToken)
                .build();

        // Token 객체를 Redis에 저장
        log.info("토큰을 저장합니다: {}", token);
        tokenRepository.save(token);
    }

    public String getRefreshTokenByEmail(String email) {
        return (String) redisTemplate.opsForValue().get(email + ":refresh"); // Redis에서 Refresh 토큰 가져오기
    }

    public void deleteToken(String email) {
        log.info("이메일에 대한 토큰을 삭제합니다: {}", email);
        redisTemplate.delete(email + ":refresh"); // Redis에서 Refresh 토큰 삭제
    }
}


