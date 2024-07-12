package com.likelion.byuldajul.diary.Dto.reponse;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LatestDiaryIdResponseDto {
    private Long latestDiaryId;

    @Builder
    public LatestDiaryIdResponseDto(Long latestDiaryId) {
        this.latestDiaryId = latestDiaryId;
    }
}
