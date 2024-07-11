package com.likelion.byuldajul.diary.Service;


import com.likelion.byuldajul.commit.entity.Commit;
import com.likelion.byuldajul.commit.repository.CommitRepository;
import com.likelion.byuldajul.diary.Dto.reponse.DiaryListResponseDto;
import com.likelion.byuldajul.diary.Dto.reponse.DiaryResponseDto;
import com.likelion.byuldajul.diary.Dto.request.CreateDiaryRequestDto;
import com.likelion.byuldajul.diary.Dto.request.UpdateDiaryRequestDto;
import com.likelion.byuldajul.diary.Entity.Diary;
import com.likelion.byuldajul.diary.Entity.Hashtag;
import com.likelion.byuldajul.diary.Repository.DiaryRepository;
import com.likelion.byuldajul.diary.Repository.HashtagRepository;
import com.likelion.byuldajul.summary.Service.SummaryUpdateService;
import com.likelion.byuldajul.user.entity.User;
import com.likelion.byuldajul.user.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final HashtagRepository hashtagRepository;
    private final SummaryUpdateService summaryUpdateService;
    private final UserRepository userRepository;
    private final CommitRepository commitRepository;

    @Transactional
    public DiaryResponseDto save(String email, CreateDiaryRequestDto createDiaryRequestDto) {
        User user = userRepository.findByEmail(email).orElseThrow();

        Diary diary = diaryRepository.save(createDiaryRequestDto.toEntity(user));

        List<Hashtag> hashtags = createDiaryRequestDto.getDiaryHashtags().stream()
                .map(hashtag -> Hashtag.builder().name(hashtag).diary(diary).build())
                .toList();

        diary.setHashTags(hashtags);

        LocalDate localDate = LocalDate.now();
        updateDiarySummary(email, localDate);
        commitRepository.save(Commit.builder().diary(diary).title(diary.getTitle()).date(localDate).user(user).build());

        return DiaryResponseDto.of(diary);
    }


    public List<DiaryListResponseDto> getDiaryListByQuery(String email, String query) {
        List<Diary> diaryList = diaryRepository.findByQuery(email, query);

        return diaryList.stream()
                .map(DiaryListResponseDto::from)
                .collect(Collectors.toList());

    }

    public DiaryResponseDto getDiary(String email, Long id) {
        Diary diary = diaryRepository.findById(id).orElseThrow();
        if (!diary.getUser().getEmail().equals(email)) {
            throw new SecurityException("권한이 없습니다.");
        }

        return DiaryResponseDto.of(diary);
    }

    @Transactional
    public void updateDiary(String email, Long id, UpdateDiaryRequestDto updateDiaryRequestDto) {
        Diary diary = diaryRepository.findById(id).orElseThrow();

        if (!diary.getUser().getEmail().equals(email)) {
            throw new SecurityException("권한이 없습니다.");
        }

        diary.update(updateDiaryRequestDto.getTitle(),
                updateDiaryRequestDto.getTemplate(),
                updateDiaryRequestDto.getMainText(),
                updateDiaryRequestDto.getImpression(),
                updateDiaryRequestDto.getRemark(),
                updateDiaryRequestDto.getPlan());

        hashtagRepository.deleteAllByDiary_Id(diary.getId());
        List<Hashtag> hashtags = updateDiaryRequestDto.getDiaryHashtags().stream()
                .map(hashtag -> Hashtag.builder().name(hashtag).diary(diary).build())
                .toList();
        hashtagRepository.saveAll(hashtags);

        LocalDate localDate = LocalDate.now();
        updateDiarySummary(email, localDate);
    }

    @Transactional
    public void deleteDiary(String email, Long id)  {

        Diary diary = diaryRepository.findById(id).orElseThrow();

        if (!diary.getUser().getEmail().equals(email)) {
            throw new SecurityException("권한이 없습니다.");
        }
        hashtagRepository.deleteAllByDiary_Id(diary.getId());
        diaryRepository.deleteById(id);

        LocalDate localDate = LocalDate.now();
        updateDiarySummary(email, localDate);
        commitRepository.deleteByDiary_Id(id);

    }

    public Map<String, Long> getHashTagList(String email) {
        List<Diary> diaries = diaryRepository.findAllByUserEmailWithHasTag(email);

        return diaries.stream()
                .flatMap(diary -> diary.getHashTags().stream())
                .collect(Collectors.groupingBy(
                                Hashtag::getName,
                                Collectors.counting()
                        )
                );
    }


    @Async
    void updateDiarySummary(String email, LocalDate localDate) {
        List<Diary> diaries = diaryRepository.findAllByUser_Email(email);
        List<String> contents = diaries.stream()
                .map(Diary::getMainText)
                .toList();

        summaryUpdateService.updateDiarySummary(email, contents, localDate);
    }

    @Transactional(readOnly = true)
    public Long getLatestDiaryId(String email) {
        Pageable pageable = PageRequest.of(0, 1); // 첫 번째 페이지의 첫 번째 결과만 가져옴
        List<Long> ids = diaryRepository.findTopByUserEmailOrderByCreatedAtDesc(email, pageable);
        return ids.isEmpty() ? null : ids.get(0); // 결과가 없으면 null을 반환, 있으면 첫 번째 결과를 반환
    }

}



