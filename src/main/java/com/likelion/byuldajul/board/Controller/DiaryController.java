package com.likelion.byuldajul.board.Controller;

import com.likelion.byuldajul.board.Dto.request.CreateDiaryRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.likelion.byuldajul.board.Service.DiaryService;



@Slf4j
@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping()
    public String createDiary(@RequestBody CreateDiaryRequestDto createDiaryRequestDto) {
        log.info("제목: {}", createDiaryRequestDto.getTitle());
        log.info("템플릿: {}", createDiaryRequestDto.getTemplate());
        log.info("본문: {}",createDiaryRequestDto.getMainText());
        log.info("느낀점: {}", createDiaryRequestDto.getImpression());


        diaryService.save(createDiaryRequestDto);

        // hashtag add

        return "일기 생성";
    }


}
