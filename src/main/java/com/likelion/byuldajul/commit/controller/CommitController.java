package com.likelion.byuldajul.commit.controller;

import com.likelion.byuldajul.commit.dto.CommitDetailsResponseDto;
import com.likelion.byuldajul.commit.dto.CommitRequestDto;
import com.likelion.byuldajul.commit.dto.CommitResponseDto;
import com.likelion.byuldajul.commit.service.CommitService;
import com.likelion.byuldajul.user.userDetails.CustomUserDetails;
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

    @GetMapping
    public ResponseEntity<List<CommitResponseDto>> getCommits(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                              @RequestBody CommitRequestDto requestDto) {
        List<CommitResponseDto> commits = commitService.getCommitsByYearAndMonth(requestDto);
        return ResponseEntity.ok(commits);
    }

    @GetMapping("/by-day")
    public ResponseEntity<List<CommitDetailsResponseDto>> getCommitsByDay(@AuthenticationPrincipal CustomUserDetails userDetails,
                                                                          @RequestParam("date") LocalDate date) {
        List<CommitDetailsResponseDto> commitDetails = commitService.getCommitsByDay(date);
        return ResponseEntity.ok(commitDetails);
    }
}
