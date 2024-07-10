package com.likelion.byuldajul.summary.Service;

import com.likelion.byuldajul.board.Service.DiaryService;
import com.likelion.byuldajul.summary.Entity.Summary;
import com.likelion.byuldajul.summary.Repository.SummaryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SummaryUpdateService {

    private final SummaryRepository summaryRepository;
    private final GPTService gptService;
    private final DiaryService diaryService;

    // 특정 날짜에 Diary가 추가되거나 내용 변경을 감지하면, GPTService에 해당 날짜의 내용을 요약하도록 다시 요청
    public ResponseEntity<?> updateDiarySummary(String email, List<String> diaryContent, LocalDate date){

        // 일기 내용을 GPTService에 전달하여 요약 생성
        String summaryContent = gptService.generateSummary(diaryContent);
        if (summaryContent == null || summaryContent.isEmpty()) { // 요약 생성에 실패한 경우
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of("error", "요약을 생성할 수 없습니다."));
        }

        // 생성된 요약을 Summary 객체로 저장
        Summary summary = new Summary(email, summaryContent, date);
        summaryRepository.save(summary);

        return ResponseEntity.ok(Map.of("message", "요약이 성공적으로 저장되었습니다."));
    }

}