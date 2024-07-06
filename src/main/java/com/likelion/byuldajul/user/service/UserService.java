package com.likelion.byuldajul.user.service;

import com.likelion.byuldajul.user.dto.CreateUserRequestDto;
import com.likelion.byuldajul.user.dto.CreateUserResponseDto;
import com.likelion.byuldajul.user.dto.UserResponseDto;
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

    //유저 생성(회원가입)
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

    //유저 정보 조회
    @Transactional(readOnly = true)
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("사용자가 존재히지 않습니다."));

        log.info("[ User Service ] 사용자정보를 가져왔습니다.");
        log.info("[ User Service ] 이메일 ---> {}", user.getEmail());
        log.info("[ User Service ] 이름 ---> {}", user.getNickname());

        //user 엔티티를 DTO로 변환 후 반
        return UserResponseDto.from(user);
    }

    //유저 닉네임 변경
    @Transactional
    public void updateNickname(String email, String nickname) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("사용자가 존재히지 않습니다."));
        user.setNickname(nickname);

        log.info("[ User Service ] 이름이 변경되었습니다 ---> {}", user.getNickname());

        userRepository.save(user);
    }

    //유저 비밀번호 변경
    @Transactional
    public void updatePassword(String email, String newPassword) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("요청한 사용자를 찾을 수 없습니다"));
        user.setPassword(passwordEncoder.encode(newPassword));

        log.info("[ User Service ] 비밀번호가 변경되었습니다 ---> {}", newPassword);

        userRepository.save(user);
    }

    //유저 탈퇴
    @Transactional
    public void deleteUser(String email) {
        User user = userRepository.findByEmail(email).orElseThrow(() -> new IllegalArgumentException("요청한 사용자를 찾을 수 없습니다"));

        log.info("[ User Service ] 사용자 탈퇴가 완료되었습니다 ---> {}", user.getEmail());

        //refresh 토큰 삭제 로직은 로그아웃 기능 구현할 때 같이 할 예정
        userRepository.delete(user);
    }

}
