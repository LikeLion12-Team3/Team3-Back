package com.likelion.byuldajul.board.Service;


import com.likelion.byuldajul.board.Dto.reponse.DiaryListResponseDto;
import com.likelion.byuldajul.board.Dto.reponse.DiaryResponseDto;
import com.likelion.byuldajul.board.Dto.request.CreateDiaryRequestDto;
import com.likelion.byuldajul.board.Entity.Diary;
import com.likelion.byuldajul.board.Entity.DiaryHashtag;
import com.likelion.byuldajul.board.Entity.Hashtag;
import com.likelion.byuldajul.board.Repository.DiaryHashtagRepository;
import com.likelion.byuldajul.board.Repository.DiaryRepository;
import com.likelion.byuldajul.board.Repository.HashtagRepository;
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
    public Long save(CreateDiaryRequestDto createDiaryRequestDto) {


        Diary diary = diaryRepository.save(createDiaryRequestDto.toEntity());

        for (String hashtagName : createDiaryRequestDto.getDiaryHashtags()) {
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

        return diary.getId();
    }

    public List<DiaryListResponseDto> getDiaryList(String hashtag) {
        List<Diary> diaryList = diaryRepository.findByHashTag(hashtag);

        List<DiaryListResponseDto> responseDtoList = diaryList.stream()
                .map(DiaryListResponseDto::from)
                .collect(Collectors.toList());
        return responseDtoList;

    }

}



