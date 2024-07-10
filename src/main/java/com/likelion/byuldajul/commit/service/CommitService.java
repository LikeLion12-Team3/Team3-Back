package com.likelion.byuldajul.commit.service;

import com.likelion.byuldajul.diary.Repository.DiaryHashtagRepository;
import com.likelion.byuldajul.commit.dto.CommitDetailsResponseDto;
import com.likelion.byuldajul.commit.dto.CommitResponseDto;
import com.likelion.byuldajul.commit.entity.Commit;
import com.likelion.byuldajul.commit.repository.CommitRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommitService {

    private final CommitRepository commitRepository;
    private final DiaryHashtagRepository diaryHashtagRepository;

    // 특정 연도와 월에 해당하는 커밋 개수를 조회하는 메서드
    @Transactional(readOnly = true)
    public List<CommitResponseDto> getCommitsByYearAndMonth(int year, int month, String email) {

        // 주어진 year과 month에 해당하는 커밋들 조회해서 List에 집어넣음
        List<Commit> commits = commitRepository.findByDateYearAndMonthAndUserEmail(year, month, email);

        // 커밋들을 일(day)별로 그룹화하고, 각 일에 해당하는 커밋 개수를 카운트하여 리스트로 반환
        return commits.stream()

                // 각 커밋의 createAt 필드에서 getDayOfMonth()를 통해서 일(day)를 뽑아냄, 각 그룹의 커밋의 개수
                // groupingBy는 이 추출한 값을 기준으로 커밋들을 그룹화
                .collect(Collectors.groupingBy(commit -> commit.getDate().getDayOfMonth(), Collectors.counting()))

                // 그룹화된 결과를 스트림으로 변환
                .entrySet().stream()

                // 각 엔트리(일과 커밋 개수)를 CommitResponseDto 객체로 매핑 (intValue()를 통해 Long을 int로 변환)
                // CommitResponseDto 객체는 생성자로 일(day)과 커밋 개수를 받아 저장
                .map(entry -> new CommitResponseDto(entry.getKey(), entry.getValue().intValue()))

                // 매핑된 CommitResponseDto 객체들을 리스트로 수집하여 반환
                .collect(Collectors.toList());
    }

    // 특정 날짜에 해당하는 커밋들의 상세 정보를 조회하는 메서드
    @Transactional(readOnly = true)
    public List<CommitDetailsResponseDto> getCommitsByDay(LocalDate date, String email) {

        // 특정 날짜에 해당하는 커밋들을 조회
        List<Commit> commits = commitRepository.findByDateAndUserEmail(date, email);

        return commits.stream()
                .map(commit -> {
                    // 다이어리 ID를 통해 다이어리 해시태그들을 조회
                    List<String> hashtags = diaryHashtagRepository.findByDiaryId(commit.getDiary().getId()).stream()
                            .map(diaryHashtag -> diaryHashtag.getHashtag().getName())
                            .toList();

                    // 커밋의 상세 정보를 CommitDetailsResponseDto 객체로 변환
                    return CommitDetailsResponseDto.builder()
                            .id(commit.getId())
                            .boardId(commit.getDiary().getId())
                            .date(commit.getDate())
                            .title(commit.getTitle())
                            .mainText(commit.getDiary().getMainText())
                            .impression(commit.getDiary().getImpression())
                            .hashtags(hashtags)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
