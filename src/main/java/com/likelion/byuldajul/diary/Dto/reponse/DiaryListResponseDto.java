package com.likelion.byuldajul.diary.Dto.reponse;

import com.likelion.byuldajul.diary.Entity.Diary;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiaryListResponseDto {

    private Long Id;

    private LocalDateTime createdAt;

    private String title;

    private String mainText;

    public static DiaryListResponseDto from(Diary diary) {
        return DiaryListResponseDto.builder()
                .Id(diary.getId())
                .createdAt(diary.getCreatedAt())
                .title(diary.getTitle())
                .mainText(diary.getMainText().length() <= 10 ? diary.getMainText() : diary.getMainText().substring(0, 10) + "..." )
                .build();

    }

}
