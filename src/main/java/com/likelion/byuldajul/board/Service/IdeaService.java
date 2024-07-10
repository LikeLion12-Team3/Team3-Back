package com.likelion.byuldajul.board.Service;

import com.likelion.byuldajul.board.Dto.reponse.IdeaResponseDto;
import com.likelion.byuldajul.board.Dto.request.CreateIdeaRequestDto;
import com.likelion.byuldajul.board.Dto.request.UpdateIdeaRequestDto;
import com.likelion.byuldajul.board.Entity.Idea;
import com.likelion.byuldajul.board.Repository.IdeaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class IdeaService {

    private final IdeaRepository ideaRepository;

    @Transactional
    public void saveIdea(CreateIdeaRequestDto createIdeaRequestDto) {

        Idea idea = ideaRepository.save(createIdeaRequestDto.toEntity());

    }

    public IdeaResponseDto getIdea(Long id) {
        Idea idea = ideaRepository.findById(id).orElseThrow();
        IdeaResponseDto ideaResponseDto = IdeaResponseDto.from(idea);
        return ideaResponseDto;

    }

    public List<Idea> getIdeaList() {
        return ideaRepository.findAll();
    }

    @Transactional
    public void updateIdea(Long id, UpdateIdeaRequestDto updateIdeaRequestDto) {

        Idea idea = ideaRepository.findIdeaById(id);

        idea.update(updateIdeaRequestDto.getTitle(), updateIdeaRequestDto.getMainText());
        ideaRepository.save(idea);
    }

    @Transactional
    public void deleteIdea(Long id) {

        ideaRepository.deleteIdeaById(id);
    }

}