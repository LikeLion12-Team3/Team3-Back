package com.likelion.byuldajul.user.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.likelion.byuldajul.user.dto.JwtDto;
import com.likelion.byuldajul.user.dto.LoginRequestDto;
import com.likelion.byuldajul.user.userDetails.CustomUserDetails;
import com.likelion.byuldajul.user.utils.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

@Slf4j
@RequiredArgsConstructor
public class CustomLoginFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    //로그인 시도 메서드(사용자 인증 시도)
    @Override
    public Authentication attemptAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response) throws AuthenticationException {

        log.info("[ Login Filter ]  로그인 시도 : Custom Login Filter 작동 ");
        ObjectMapper objectMapper = new ObjectMapper();
        LoginRequestDto requestBody;
        try {
            requestBody = objectMapper.readValue(request.getInputStream(), LoginRequestDto.class); //HTTP 요청의 본문을 LoginRequest 객체로 변환
        } catch (IOException e) {
            throw new AuthenticationServiceException("[ Login Filter ] Request Body 파싱 과정에서 오류가 발생했습니다.");
        }

        //Request Body 에서 추출
        String email = requestBody.getEmail(); //Email 추출
        String password = requestBody.getPassword(); //password 추출
        log.info("[ Login Filter ]  Email ---> {} ", email);
        log.info("[ Login Filter ]  Password ---> {} ", password);

        //UserNamePasswordToken 을 생성하여 인증을 시도
        UsernamePasswordAuthenticationToken authToken
                = new UsernamePasswordAuthenticationToken(email, password);

        log.info("[ Login Filter ] 인증용 객체 UsernamePasswordAuthenticationToken 이 생성되었습니다. ");
        log.info("[ Login Filter ] 인증을 시도합니다.");

        //UsernamePasswordAuthenticationToken을 매개변수로 받아서 인증을 시도하는 메소드
        return authenticationManager.authenticate(authToken);
    }

    //로그인(인증) 성공시 처리
    @Override
    protected void successfulAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain chain,
            @NonNull Authentication authentication) throws IOException {


        log.info("[ Login Filter ] 로그인에 성공 하였습니다.");

        //사용자 정보 가져오기
        CustomUserDetails customUserDetails = (CustomUserDetails)authentication.getPrincipal(); //Authentication 객체에서 사용자의 정보 추출 후 CustomUserDetails로 객체로 캐스팅
                                                                                    //해당 Authentication 객체는 위 attemptAuthentication 메서드에서 반환한 객체

        //Client 에게 줄 Response 를 Build
        JwtDto jwtDto = JwtDto.builder()
                .accessToken(jwtUtil.createJwtAccessToken(customUserDetails)) //access token 생성
                .refreshToken(jwtUtil.createJwtRefreshToken(customUserDetails)) //refresh token 생성
                .build();

        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.OK.value()); //Response 의 Status 를 200으로 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE); //응답의 타입을 application/json 으로 설정
        response.setCharacterEncoding("UTF-8"); //응답의 캐릭터 인코딩을 UTF-8로 설정

        //Body 에 토큰이 담긴 Response 쓰기
        response.getWriter().write(objectMapper.writeValueAsString(jwtDto)); //JwtDto 객체를 JSON 문자열로 변환 후 Response
    }

    //로그인 실패시
    @Override
    protected void unsuccessfulAuthentication(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull AuthenticationException failed) throws IOException {

        log.info("[ Login Filter ] 로그인에 실패하였습니다.");

        String errorMessage;
        if (failed instanceof BadCredentialsException) {
            errorMessage = "잘못된 정보입니다.";
        } else if (failed instanceof LockedException) {
            errorMessage = "계정이 잠금 상태입니다.";
        } else if (failed instanceof DisabledException) {
            errorMessage = "계정이 비활성화 되었습니다.";
        } else if (failed instanceof UsernameNotFoundException) {
            errorMessage = "계정을 찾을 수 없습니다.";
        } else if (failed instanceof AuthenticationServiceException) {
            errorMessage = "Request Body 파싱 중 오류가 발생했습니다.";
        } else {
            errorMessage = "인증에 실패했습니다.";
        }

        ObjectMapper objectMapper = new ObjectMapper();
        response.setStatus(HttpStatus.UNAUTHORIZED.value()); //Status 설정
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(errorMessage)); //error message 와 함께 Response 작성
    }
}
