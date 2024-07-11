package com.likelion.byuldajul.commit.controller;

import com.likelion.byuldajul.commit.dto.CommitDetailsResponseDto;
import com.likelion.byuldajul.commit.dto.CommitResponseDto;
import com.likelion.byuldajul.commit.service.CommitService;
import com.likelion.byuldajul.user.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/commits")
public class CommitController {

    private final CommitService commitService;

    @Operation(summary = "커밋 조회", description = "년, 월을 통해 보유한 커밋을 조회합니다.")
    @GetMapping("")
    public ResponseEntity<List<CommitResponseDto>> getCommits(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                              @RequestParam("year") int year, @RequestParam("month") int month) {
        List<CommitResponseDto> commits = commitService.getCommitsByYearAndMonth(year, month, userDetails.getUsername());
        return ResponseEntity.ok(commits);
    }

    @GetMapping("/by-day")
    @Operation(summary = "날짜별 커밋 상세 조회", description = "해당 날짜의 커밋 상세 정보를 조회합니다.")
    public ResponseEntity<List<CommitDetailsResponseDto>> getCommitsByDay(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                          @RequestParam("date") LocalDate date) {
        List<CommitDetailsResponseDto> commitDetails = commitService.getCommitsByDay(date, userDetails.getUsername());
        return ResponseEntity.ok(commitDetails);
    }
}
