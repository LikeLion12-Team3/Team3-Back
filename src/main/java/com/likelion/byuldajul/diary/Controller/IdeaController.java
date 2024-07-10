package com.likelion.byuldajul.diary.Controller;

import com.likelion.byuldajul.diary.Dto.reponse.IdeaResponseDto;
import com.likelion.byuldajul.diary.Dto.request.CreateIdeaRequestDto;
import com.likelion.byuldajul.diary.Dto.request.UpdateIdeaRequestDto;
import com.likelion.byuldajul.diary.Entity.Idea;
import com.likelion.byuldajul.diary.Service.IdeaService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/idea")
@RequiredArgsConstructor
public class IdeaController {

    private final IdeaService ideaService;

    @PostMapping()
    public String createIdea(@RequestBody CreateIdeaRequestDto createIdeaRequestDto) {
        log.info("제목: {}", createIdeaRequestDto.getTitle());
        log.info("아이디어내용: {}", createIdeaRequestDto.getMainText());

        ideaService.saveIdea(createIdeaRequestDto);
        return "아이디어 생성";
    }

    @GetMapping("/{id}")
    public IdeaResponseDto getIdea(@PathVariable Long id) {
        return ideaService.getIdea(id);
    }

    @GetMapping()
    public List<Idea> getIdeaList() {
        return ideaService.getIdeaList();
    }

    @PatchMapping("/{id}")
    public void updateIdea(@PathVariable Long id, @RequestBody UpdateIdeaRequestDto updateIdeaRequestDto) {
        ideaService.updateIdea(id, updateIdeaRequestDto);
    }

    @DeleteMapping("/{id}")
    public void deleteIdea(@PathVariable Long id) {
        ideaService.deleteIdea(id);
    }
}
