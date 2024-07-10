package com.likelion.byuldajul.diary.Dto.reponse;

import com.likelion.byuldajul.diary.Entity.Diary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DiaryResponseDto {

    private Long id;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    private String title;

    private String template;

    private String mainText;

    private String impression;

    private String remark;

    private String plan;

    private List<String> hashtagNames;

    public static DiaryResponseDto of(Diary diary, List<String> hashtagNames) {
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
                .hashtagNames(hashtagNames)
                .build();
    }

}
