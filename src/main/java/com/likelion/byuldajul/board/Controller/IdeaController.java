package com.likelion.byuldajul.board.Controller;

import com.likelion.byuldajul.board.Dto.reponse.IdeaResponseDto;
import com.likelion.byuldajul.board.Dto.request.CreateIdeaRequestDto;
import com.likelion.byuldajul.board.Dto.request.UpdateIdeaRequestDto;
import com.likelion.byuldajul.board.Entity.Idea;
import com.likelion.byuldajul.board.Service.IdeaService;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DialectOverride;
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
