package com.likelion.byuldajul.summary.Controller;

import com.likelion.byuldajul.summary.Service.GPTService;
import com.likelion.byuldajul.board.Service.DiaryService;
import com.likelion.byuldajul.user.userDetails.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/diary")
public class SummaryController {

    private final GPTService gptService;
    private final DiaryService diaryService;

    // 일기 요약을 가져오는 HTTP GET 요청을 처리
    @GetMapping("/summary")
    public ResponseEntity<?> getDiarySummary(@RequestParam(required = false) LocalDateTime date,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        // 사용자 인증 정보를 기반으로 인가 처리
        if (userDetails == null || !userDetails.isEnabled()) { // 사용자가 유효하지 않으면, 401 Unauthorized 응답을 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 사용자입니다.");
        }

        try {
            // diaryService를 사용하여 요청된 날짜에 해당하는 일기 내용을 조회
//            String diaryContent = diaryService.getDiaryContentByDate(date);
            // GPTService를 사용하여 요약을 생성
//            String summary = gptService.generateSummary(diaryContent, date);

            return ResponseEntity.ok(null); // 생성된 요약을 HTTP 200 OK 응답으로 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("요청 처리 중 오류가 발생했습니다"); // 예외가 발생하면, 500 Internal Server Error 응답을 반환
        }
    }

    private boolean isValidToken(String token) {
        // 토큰 검증 로직 구현
        return true; // 예시로 항상 true를 반환
    }
}
