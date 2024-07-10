package com.likelion.byuldajul.diary.Controller;

import com.likelion.byuldajul.diary.Dto.reponse.DiaryListResponseDto;
import com.likelion.byuldajul.diary.Dto.reponse.DiaryResponseDto;
import com.likelion.byuldajul.diary.Dto.request.CreateDiaryRequestDto;
import com.likelion.byuldajul.diary.Dto.request.UpdateDiaryRequestDto;
import com.likelion.byuldajul.user.userDetails.CustomUserDetails;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.connection.ReactiveSetCommands;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import com.likelion.byuldajul.diary.Service.DiaryService;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @Operation(summary = "새 일기 생성", description = "새 일기를 생성합니다.")
    @PostMapping("")
    public ResponseEntity<?> createDiary(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                         @RequestBody CreateDiaryRequestDto createDiaryRequestDto) {
        log.info("제목: {}", createDiaryRequestDto.getTitle());
        log.info("템플릿: {}", createDiaryRequestDto.getTemplate());
        log.info("본문: {}",createDiaryRequestDto.getMainText());
        log.info("느낀점: {}", createDiaryRequestDto.getImpression());
        DiaryResponseDto responseDto = diaryService.save(customUserDetails.getUsername(), createDiaryRequestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @Operation(summary = "일기 조회 (해시태그)", description = "해시태그로 일기를 조회합니다. 내용은 첫 10글자만 보입니다. " +
            "'해시태그 검색 결과' 페이지에서 이용하시면 됩니다. \n query에는 사용자가 검색을 시도한 내용을 전달하시면 됩니다.")
    @GetMapping("")
    public ResponseEntity<List<DiaryListResponseDto>> getDiaryList(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                                                   @RequestParam String query) {
        return ResponseEntity.ok(diaryService.getDiaryListByQuery(customUserDetails.getUsername(), query));
    }

    @Operation(summary = "해시태그 조회", description = "태그된 해시태그와 각 해시태그별 태그된 수를 조회합니다.")
    @GetMapping("/hashtag")
    public ResponseEntity<?> getHashTagList(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return ResponseEntity.ok(diaryService.getHashTagList(customUserDetails.getUsername()));
    }

    @Operation(summary = "일기 조회 (ID)", description = "일기 단건 조회 (ID)")
    @GetMapping("/{id}")
    public ResponseEntity<?> getDiary(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                      @PathVariable Long id) {
        return ResponseEntity.ok(diaryService.getDiary(customUserDetails.getUsername(), id));
    }

    @Operation(summary = "일기 업데이트", description = "일기 업데이트")
    @PatchMapping("/{id}")
    public ResponseEntity<?> updateDiary(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                         @PathVariable Long id, @RequestBody UpdateDiaryRequestDto updateDiaryRequestDto) {
        diaryService.updateDiary(customUserDetails.getUsername(), id, updateDiaryRequestDto);
        return ResponseEntity.ok("수정 완료");
    }

    @Operation(summary = "일기 삭제", description = "일기 삭제")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDiary(@AuthenticationPrincipal CustomUserDetails customUserDetails,
                                         @PathVariable Long id) {
        diaryService.deleteDiary(customUserDetails.getUsername(), id);
        return ResponseEntity.ok("삭제 완료");
    }



}
