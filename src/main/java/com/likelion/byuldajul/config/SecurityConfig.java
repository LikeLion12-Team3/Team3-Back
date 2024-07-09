package com.likelion.byuldajul.config;

import com.likelion.byuldajul.exception.CustomAccessDeniedHandler;
import com.likelion.byuldajul.exception.CustomAuthenticationEntryPoint;
import com.likelion.byuldajul.user.filter.CustomLoginFilter;
import com.likelion.byuldajul.user.filter.CustomLogoutHandler;
import com.likelion.byuldajul.user.filter.JwtFilter;
import com.likelion.byuldajul.user.repository.UserRepository;
import com.likelion.byuldajul.user.service.TokenService;
import com.likelion.byuldajul.user.utils.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final AuthenticationConfiguration authenticationConfiguration;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TokenService tokenService;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    @Bean //암호화 메서드
    public BCryptPasswordEncoder bCryptPasswordEncoder() { return new BCryptPasswordEncoder(); }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

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

        // Custom AuthenticationEntryPoint와 AccessDeniedHandler 등록
        http
                .exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint(customAuthenticationEntryPoint)
                        .accessDeniedHandler(customAccessDeniedHandler)
                );

        // Login Filter
        CustomLoginFilter loginFilter = new CustomLoginFilter(
                authenticationManager(authenticationConfiguration), jwtUtil);
        // Login Filter URL 지정
        loginFilter.setFilterProcessesUrl("/users/login");

        // filter chain 에 login filter 등록
        http
                .addFilterAt(loginFilter, UsernamePasswordAuthenticationFilter.class);
        // login filter 전에 Auth Filter 등록
        http
                .addFilterBefore(new JwtFilter(jwtUtil, userRepository), CustomLoginFilter.class);


        // Logout Filter 추가
        http
                .logout(logout -> logout
                        .logoutUrl("/users/logout")
                        .addLogoutHandler(new CustomLogoutHandler(tokenService, jwtUtil))
                        //.logoutSuccessUrl("/users/login") // 로그아웃 성공 시 리다이렉트할 URL 설정
                        .logoutSuccessHandler((request, response, authentication) -> {
                            response.setStatus(HttpStatus.OK.value());
                            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
                            response.setCharacterEncoding("UTF-8");
                            response.getWriter().write("{\"message\":\"로그아웃 성공\"}");
                        })
                );

        return http.build();
    }
}

