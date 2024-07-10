package com.likelion.byuldajul.summary.Controller;

import com.likelion.byuldajul.summary.Service.GPTService;
import com.likelion.byuldajul.diary.Service.DiaryService;
import com.likelion.byuldajul.summary.Service.SummaryService;
import com.likelion.byuldajul.user.userDetails.CustomUserDetails;

import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/summary")
public class SummaryController {

    private final SummaryService summaryService;

    // 일기 요약을 가져오는 HTTP GET 요청을 처리
    @Operation(summary = "일기 요약 조회", description = "일기 요약을 조회합니다.")
    @GetMapping("")
    public ResponseEntity<?> getDiarySummary(@RequestParam("year") int year,
                                             @RequestParam("month") int month,
                                             @RequestParam("day") int day,
                                             @AuthenticationPrincipal CustomUserDetails userDetails) {

        LocalDate date = LocalDate.of(year, month, day);
        return ResponseEntity.ok(summaryService.getSummary(userDetails.getUsername(), date));

    }
}
