package com.likelion.byuldajul.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean //암호화 메서드
    public BCryptPasswordEncoder bCryptPasswordEncoder() { return new BCryptPasswordEncoder(); }

    //인증이 필요하지 않은 url
    private final String[] allowedUrls = {
            "/users/login", //로그인은 인증이 필요하지 않음
            "/users/signup", //회원가입은 인증이 필요하지 않음
            "/auth/reissue" //토큰 재발급은 인증이 필요하지 않음
    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // CORS 정책 설정
        http
                .cors(cors -> cors
                        .configurationSource(CorsConfig.apiConfigurationSource()));

        //csrf disable, 세션방식에서는 세션이 고정되기 때문에 필수적으로 csrf에 대한 공격을 방어해야하지만, JWT방식은 세션을 STATELESS 상태로 관리하기 때문에 csrf에 대한 공격을 크게 방어하지 않아도 됨.
        http
                .csrf((auth) -> auth.disable());

        //From 로그인 방식 disable , JWT방식을 사용할것이기 때문에
        http
                .formLogin((auth) -> auth.disable());

        //http basic 인증 방식 disable , JWT방식을 사용할것이기 때문에
        http
                .httpBasic((auth) -> auth.disable());

        // 경로별 인가
        http
                .authorizeHttpRequests(auth -> auth
                        //위에서 정의했던 allowedUrls 들은 인증이 필요하지 않음 -> permitAll
                        .requestMatchers(allowedUrls).permitAll()
                        .anyRequest().authenticated() // 그 외의 url 들은 인증이 필요함
                );

        //세션 설정, JWT방식에서는 항상 세션을 STATELESS 상태로 관리한다.
        http
                .sessionManagement((session) -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        return http.build();
    }
}

