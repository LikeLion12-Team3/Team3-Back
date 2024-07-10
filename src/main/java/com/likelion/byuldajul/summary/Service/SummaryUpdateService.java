package com.likelion.byuldajul.summary.Service;

import com.likelion.byuldajul.user.userDetails.CustomUserDetails;
import com.likelion.byuldajul.diary.Service.DiaryService;
import com.likelion.byuldajul.summary.Entity.Summary;
import com.likelion.byuldajul.summary.Repository.SummaryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SummaryUpdateService {

    private final SummaryRepository summaryRepository;
    private final GPTService gptService;
    private final DiaryService diaryService;

    // 특정 날짜에 Diary가 추가되거나 내용 변경을 감지하면, GPTService에 해당 날짜의 내용을 요약하도록 다시 요청
    public ResponseEntity<?> updateDiarySummary(@AuthenticationPrincipal CustomUserDetails userDetails, LocalDateTime date){
        // 특정 날짜의 일기 내용을 가져옴
//        List<String> diaryContent = diaryService.getDiaryForDate(userDetails.getUsername(), date);
//        if (diaryContent == null || diaryContent.isEmpty()) { // 해당 날짜에 일기가 존재하지 않으면, 404 Not Found 응답을 반환
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "주어진 날짜에 대한 일기를 찾을 수 없습니다."));
//        }
        // 일기 내용을 GPTService에 전달하여 요약 생성
        String summaryContent = gptService.generateSummary(null);
        if (summaryContent == null || summaryContent.isEmpty()) { // 요약 생성에 실패한 경우
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(Map.of("error", "주어진 일기 내용으로 요약을 생성할 수 없습니다"));
        }
        // 생성된 요약을 필요한 곳에 사용하거나 저장
        Summary summary = new Summary(summaryContent, date);
        summaryRepository.save(summary);

        return ResponseEntity.ok(null);
    }

}