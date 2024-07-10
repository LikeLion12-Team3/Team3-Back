package com.likelion.byuldajul.user.filter;

import com.likelion.byuldajul.user.service.TokenService;
import com.likelion.byuldajul.user.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;


public class CustomLogoutHandler implements LogoutHandler {

    private final TokenService tokenService;
    private final JwtUtil jwtUtil;

    public CustomLogoutHandler(TokenService tokenService, JwtUtil jwtUtil) {
        this.tokenService = tokenService;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        // 요청에서 Access Token 추출
        String accessToken = jwtUtil.resolveAccessToken(request);

        if (accessToken != null) {
            // Accsee Token에서 이메일 추출
            String email = jwtUtil.getEmail(accessToken);

            // Redis에서 해당 이메일로 Refresh 토큰 가져오기
            String refreshToken = tokenService.getRefreshTokenByEmail(email);

            // 리프레시 토큰을 블랙리스트에 추가
            long expiryDuration = jwtUtil.getRefreshExp(refreshToken);

            // refresh 토큰을 블랙리스트에 추가
            tokenService.addToBlacklist(refreshToken, expiryDuration);
            // 이메일과, 이메일로  저장된 리프레시 토큰 삭제
            tokenService.deleteToken(email);
        }
    }
}

