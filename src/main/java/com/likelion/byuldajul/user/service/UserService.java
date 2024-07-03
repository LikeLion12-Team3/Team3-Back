package com.likelion.byuldajul.user.service;

import com.likelion.byuldajul.user.dto.CreateUserRequestDto;
import com.likelion.byuldajul.user.dto.CreateUserResponseDto;
import com.likelion.byuldajul.user.entity.User;
import com.likelion.byuldajul.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public CreateUserResponseDto signUp(CreateUserRequestDto createUserRequestDto) {

        // 이메일 중복 확인
        if (userRepository.existsByEmail(createUserRequestDto.getEmail())) {
            throw new IllegalArgumentException("해당 이메일이 이미 존재합니다.");
        }

        //파라미터로 받은 DTO를 Entity로 변환
        User user = createUserRequestDto.toEntity(passwordEncoder);

        //변환한 Entity를 DB에 저장
        userRepository.save(user);

        log.info("[ User Service ] 사용자가 생성되었습니다.");
        log.info("[ User Service ] 이메일 ---> {}", user.getEmail());
        log.info("[ User Service ] 이름 ---> {}", user.getNickname());
        log.info("[ User Service ] 비밀번호 ---> {}", user.getPassword());

        //DB에 저장한 Entity를 DTO로 변환 후 반환
        return CreateUserResponseDto.from(user);
    }
}
