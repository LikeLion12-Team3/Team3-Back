package com.likelion.byuldajul.diary.Controller;

import com.likelion.byuldajul.diary.Dto.reponse.IdeaResponseDto;
import com.likelion.byuldajul.diary.Dto.request.CreateIdeaRequestDto;
import com.likelion.byuldajul.diary.Dto.request.UpdateIdeaRequestDto;
import com.likelion.byuldajul.diary.Entity.Idea;
import com.likelion.byuldajul.diary.Service.IdeaService;
import com.likelion.byuldajul.user.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/idea")
@RequiredArgsConstructor
public class IdeaController {

    private final IdeaService ideaService;

    @Operation(summary = "아이디어 생성", description = "아이디어를 생성합니다.")
    @PostMapping("")
    public ResponseEntity<?> createIdea(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                        @RequestBody CreateIdeaRequestDto createIdeaRequestDto) {
        log.info("제목: {}", createIdeaRequestDto.getTitle());
        log.info("아이디어내용: {}", createIdeaRequestDto.getMainText());

        return ResponseEntity.ok(ideaService.saveIdea(customUserDetails.getUsername(),
                createIdeaRequestDto));
    }

    @Operation(summary = "아이디어 조회", description = "아이디어 단건 조회 (ID)")
    @GetMapping("/{id}")
    public IdeaResponseDto getIdea(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                   @PathVariable Long id) {
        return ideaService.getIdea(customUserDetails.getUsername(), id);
    }

    @Operation(summary = "아이디어 전체 조회", description = "아이디어 전체 조회")
    @GetMapping("")
    public ResponseEntity<?> getIdeaList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(ideaService.getIdeaList(customUserDetails.getUsername()));
    }

    @Operation(summary = "아이디어 업데이트", description = "아이디어 업데이트")
    @PatchMapping("/{id}")
    public void updateIdea(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                           @PathVariable Long id, @RequestBody UpdateIdeaRequestDto updateIdeaRequestDto) {
        ideaService.updateIdea(customUserDetails.getUsername(), id, updateIdeaRequestDto);
    }

    @Operation(summary = "아이디서 삭제", description = "아이디어 삭제")
    @DeleteMapping("/{id}")
    public void deleteIdea(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                           @PathVariable Long id) {
        ideaService.deleteIdea(customUserDetails.getUsername(), id);
    }
}
