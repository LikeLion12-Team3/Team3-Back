package com.likelion.byuldajul.diary.Controller;

import com.likelion.byuldajul.diary.Dto.reponse.DiaryListResponseDto;
import com.likelion.byuldajul.diary.Dto.reponse.DiaryResponseDto;
import com.likelion.byuldajul.diary.Dto.request.CreateDiaryRequestDto;
import com.likelion.byuldajul.diary.Dto.request.UpdateDiaryRequestDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<?> createDiary(@RequestBody CreateDiaryRequestDto createDiaryRequestDto) {
        log.info("제목: {}", createDiaryRequestDto.getTitle());
        log.info("템플릿: {}", createDiaryRequestDto.getTemplate());
        log.info("본문: {}",createDiaryRequestDto.getMainText());
        log.info("느낀점: {}", createDiaryRequestDto.getImpression());
        DiaryResponseDto responseDto = diaryService.save(createDiaryRequestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);
    }

    @GetMapping()
    public List<DiaryListResponseDto> getDiaryList(@RequestParam String hashtag) {
        return diaryService.getDiaryList(hashtag);
    }

    @GetMapping("/{id}")
    public DiaryResponseDto getDiary(@PathVariable Long id) {
        return diaryService.getDiary(id);
    }

    @PatchMapping("/{id}")
    public void updateDiary(@PathVariable Long id, @RequestBody UpdateDiaryRequestDto updateDiaryRequestDto) {
        diaryService.updateDiary(id, updateDiaryRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteDiary(@PathVariable Long id) {
        diaryService.deleteDiary(id);
    }



}
