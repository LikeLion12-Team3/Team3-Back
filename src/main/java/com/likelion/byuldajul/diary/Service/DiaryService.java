package com.likelion.byuldajul.diary.Service;


import com.likelion.byuldajul.diary.Dto.reponse.DiaryListResponseDto;
import com.likelion.byuldajul.diary.Dto.reponse.DiaryResponseDto;
import com.likelion.byuldajul.diary.Dto.request.CreateDiaryRequestDto;
import com.likelion.byuldajul.diary.Dto.request.UpdateDiaryRequestDto;
import com.likelion.byuldajul.diary.Entity.Diary;
import com.likelion.byuldajul.diary.Entity.DiaryHashtag;
import com.likelion.byuldajul.diary.Entity.Hashtag;
import com.likelion.byuldajul.diary.Repository.DiaryHashtagRepository;
import com.likelion.byuldajul.diary.Repository.DiaryRepository;
import com.likelion.byuldajul.diary.Repository.HashtagRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@Slf4j
@RequiredArgsConstructor
public class DiaryService {

    private final DiaryRepository diaryRepository;
    private final HashtagRepository hashtagRepository;
    private final DiaryHashtagRepository diaryHashtagRepository;

    @Transactional
    public DiaryResponseDto save(CreateDiaryRequestDto createDiaryRequestDto) {

        Diary diary = diaryRepository.save(createDiaryRequestDto.toEntity());
        List<String> hashtags = hashtagRepository.findHashtagsByDiaryId(diary.getId());
        saveHashtag(diary, createDiaryRequestDto.getDiaryHashtags());

        return DiaryResponseDto.of(diary, hashtags);
    }

    private void saveHashtag(Diary diary, List<String> hashtagList) {
        for (String hashtagName : hashtagList) {
            Optional<Hashtag> optionalHashtag = hashtagRepository.findByName(hashtagName);

            Hashtag hashtag;
            hashtag = optionalHashtag.orElseGet(() ->
                    hashtagRepository.save(Hashtag.builder()
                            .name(hashtagName)
                            .build()));

            log.info(hashtag.toString());

            DiaryHashtag diaryHashtag = DiaryHashtag.builder()
                    .diary(diary)
                    .hashtag(hashtag)
                    .build();

            log.info(diaryHashtag.toString());

            diaryHashtagRepository.save(diaryHashtag);
        }
    }

    public List<DiaryListResponseDto> getDiaryList(String hashtag) {
        List<Diary> diaryList = diaryRepository.findByHashTag(hashtag);

        return diaryList.stream()
                .map(DiaryListResponseDto::from)
                .collect(Collectors.toList());

    }

    public DiaryResponseDto getDiary(Long id) {
        Diary diary = diaryRepository.findDiaryById(id);
        List<String> hashtags = hashtagRepository.findHashtagsByDiaryId(id);

        return DiaryResponseDto.builder()
                .id(diary.getId())
                .createdAt(diary.getCreatedAt())
                .modifiedAt(diary.getModifiedAt())
                .title(diary.getTitle())
                .template(diary.getTemplate())
                .mainText(diary.getMainText())
                .impression(diary.getImpression())
                .remark(diary.getRemark())
                .plan(diary.getPlan())
                .hashtagNames(hashtags)
                .build();
    }

    @Transactional
    public void updateDiary(Long id, UpdateDiaryRequestDto updateDiaryRequestDto) {
        Diary diary = diaryRepository.findDiaryById(id);

        diary.update(updateDiaryRequestDto.getTitle(),
                updateDiaryRequestDto.getTemplate(),
                updateDiaryRequestDto.getMainText(),
                updateDiaryRequestDto.getImpression(),
                updateDiaryRequestDto.getRemark(),
                 updateDiaryRequestDto.getPlan());

        diaryHashtagRepository.deleteAllByDiary_Id(id);

        saveHashtag(diary, updateDiaryRequestDto.getDiaryHashtags());
    }

    @Transactional
    public void deleteDiary(Long Id)  {

        diaryHashtagRepository.deleteAllByDiary_Id(Id);

        diaryRepository.deleteById(Id);
    }


    private void updateDiarySummary(String email) {
        List<Diary> diaries = diaryRepository.findAllByUser_Email(email);
        List<String> contents = diaries.stream()
                .map(Diary::getMainText)
                .toList();

        //TODO : GPT 요약 보내기
    }
}



