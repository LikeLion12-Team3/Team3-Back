package com.likelion.byuldajul.summary.Service;

import com.likelion.byuldajul.summary.Entity.Summary;
import com.likelion.byuldajul.summary.Repository.SummaryRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SummaryUpdateService {

    private final SummaryRepository summaryRepository;
    private final GPTService gptService;

    // 특정 날짜에 Diary가 추가되거나 내용 변경을 감지하면, GPTService에 해당 날짜의 내용을 요약하도록 다시 요청
    public void updateDiarySummary(String email, List<String> diaryContent, LocalDate date){

        // 일기 내용을 GPTService에 전달하여 요약 생성
        String summaryContent = gptService.generateSummary(diaryContent);


        // 생성된 요약을 Summary 객체로 저장
        Summary summary = Summary.builder()
                .date(date)
                .content(summaryContent)
                .email(email)
                .build();
        summaryRepository.save(summary);
    }

}