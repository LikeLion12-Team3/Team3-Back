package com.likelion.byuldajul.diary.Service;

import com.likelion.byuldajul.diary.Dto.reponse.IdeaResponseDto;
import com.likelion.byuldajul.diary.Dto.request.CreateIdeaRequestDto;
import com.likelion.byuldajul.diary.Dto.request.UpdateIdeaRequestDto;
import com.likelion.byuldajul.diary.Entity.Idea;
import com.likelion.byuldajul.diary.Repository.IdeaRepository;
import com.likelion.byuldajul.user.entity.User;
import com.likelion.byuldajul.user.repository.UserRepository;
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
    private final UserRepository userRepository;


    @Transactional
    public IdeaResponseDto saveIdea(String email, CreateIdeaRequestDto createIdeaRequestDto) {
        User user = userRepository.findByEmail(email).orElseThrow();
        Idea idea = ideaRepository.save(createIdeaRequestDto.toEntity(user));
        return IdeaResponseDto.from(idea);

    }

    public IdeaResponseDto getIdea(String email, Long id) {
        Idea idea = ideaRepository.findById(id).orElseThrow();

        if (!idea.getUser().getEmail().equals(email)) {
            throw new SecurityException("권한이 없습니다.");
        }

        IdeaResponseDto ideaResponseDto = IdeaResponseDto.from(idea);
        return ideaResponseDto;

    }

    public List<IdeaResponseDto> getIdeaList(String email) {
        return ideaRepository.findAllByUser_Email(email).stream()
                .map(IdeaResponseDto::from)
                .toList();
    }

    @Transactional
    public void updateIdea(String email, Long id, UpdateIdeaRequestDto updateIdeaRequestDto) {

        Idea idea = ideaRepository.findIdeaById(id);

        if (!idea.getUser().getEmail().equals(email)) {
            throw new SecurityException("권한이 없습니다.");
        }

        idea.update(updateIdeaRequestDto.getTitle(), updateIdeaRequestDto.getMainText());
        ideaRepository.save(idea);
    }

    @Transactional
    public void deleteIdea(String email, Long id) {
        Idea idea = ideaRepository.findById(id).orElseThrow();

        if (!idea.getUser().getEmail().equals(email)) {
            throw new SecurityException("권한이 없습니다.");
        }

        ideaRepository.deleteIdeaById(id);
    }


}