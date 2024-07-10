package com.likelion.byuldajul.summary.Controller;

import com.likelion.byuldajul.summary.Service.GPTService;
import com.likelion.byuldajul.board.Service.DiaryService;
import com.likelion.byuldajul.user.userDetails.CustomUserDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/boards/diary")
public class SummaryController {

    private final GPTService gptService;
    private final DiaryService diaryService;

    // 일기 요약을 가져오는 HTTP GET 요청을 처리
    @GetMapping("/summary")
    public ResponseEntity<?> getDiarySummary(@RequestParam(required = false) LocalDate date,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {
        // 받은 date에서 년, 월, 일 추출
        int year = date.getYear();
        int month = date.getMonthValue();
        int dayOfMonth = date.getDayOfMonth();

        // 사용자 인증 정보를 기반으로 인가 처리
        if (!userDetails.isEnabled()) { // 사용자가 유효하지 않으면, 401 Unauthorized 응답을 반환
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("유효하지 않은 사용자입니다.");
        }

        try {
//            String diaryContent = diaryService.getDiaryContentByYearMonthDay(year, month, dayOfMonth);
//            String summary = gptService.generateSummary(diaryContent);

            return ResponseEntity.ok(Map.of("message", "요약이 성공적으로 생성되었습니다.")); // 생성된 요약을 HTTP 200 OK 응답으로 반환
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("요청 처리 중 오류가 발생했습니다."); // 예외가 발생하면, 500 Internal Server Error 응답을 반환
        }
    }
}
