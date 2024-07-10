package com.likelion.byuldajul.board.Controller;

import com.likelion.byuldajul.board.Dto.reponse.DiaryListResponseDto;
import com.likelion.byuldajul.board.Dto.reponse.DiaryResponseDto;
import com.likelion.byuldajul.board.Dto.request.CreateDiaryRequestDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import com.likelion.byuldajul.board.Service.DiaryService;

import java.util.List;


@Slf4j
@RestController
@RequestMapping("/diary")
@RequiredArgsConstructor
@CrossOrigin(originPatterns = "*")
public class DiaryController {

    private final DiaryService diaryService;

    @PostMapping()
    public String createDiary(@RequestBody CreateDiaryRequestDto createDiaryRequestDto) {
        log.info("제목: {}", createDiaryRequestDto.getTitle());
        log.info("템플릿: {}", createDiaryRequestDto.getTemplate());
        log.info("본문: {}",createDiaryRequestDto.getMainText());
        log.info("느낀점: {}", createDiaryRequestDto.getImpression());


        diaryService.save(createDiaryRequestDto);



        return "일기 생성";
    }

    @GetMapping()
    public List<DiaryListResponseDto> getDiaryList(@RequestParam String hashtag) {
        return diaryService.getDiaryList(hashtag);
    }

//    @GetMapping("/{Id}")
//    public DiaryResponseDto getDiary(@PathVariable Long Id) {
//        return diaryService.getDiary(Id);
//    }

    @GetMapping("/{id}")
    public DiaryResponseDto getDiary(@PathVariable Long id) {
        return diaryService.getDiary(id);
    }


}
